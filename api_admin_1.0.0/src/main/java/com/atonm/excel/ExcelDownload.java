package com.atonm.excel;

import com.atonm.core.context.AppContextManager;
import com.atonm.kblease.api.config.generate.CodeConfig;
import com.atonm.kblease.api.utils.FtpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.SftpException;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelDownload extends AbstractView {
    private final CodeConfig codeConfig;
    private String downloadFilName;
    private List<String> headerList;
    private static final String CONTENT_TYPE = "application/vnd.ms-excel"; // Content Type 설정

    private Logger logger = LoggerFactory.getLogger(ExcelDownload.class);

    public ExcelDownload(CodeConfig codeConfig) {
        this.codeConfig = codeConfig;
    }

    public ExcelDownload() {
        this.codeConfig =  AppContextManager.getBean(CodeConfig.class);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userAgent = request.getHeader("User-Agent");
        try {
            Map<String, Object> datas = (Map<String, Object>) model.get("resultList");
            this.downloadFilName = (String) datas.get("fileName");
            this.headerList = new ArrayList<>();
            this.headerList = (ArrayList) datas.get("headers");

            // 파일 이름 설정
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            String yyyymmdd = sdf.format(c1.getTime());
            String fileName = downloadFilName + "_" + yyyymmdd + ".xlsx";
            fileName = URLEncoder.encode(fileName,"UTF-8"); // UTF-8로 인코딩

            // TODO : file clinet download 시 아래 주석 해제
            // 다운로드 설정
            if(userAgent.indexOf("MSIE") > -1){
                fileName = URLEncoder.encode(fileName, "utf-8");
            }else{
                fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
            }

            response.setContentType(CONTENT_TYPE);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            response.setHeader("Content-Transfer-Encoding", "binary");

            // SXSSFWorkbook 생성
            XSSFWorkbook workbook = new XSSFWorkbook();
            //workbook.setCompressTempFiles(true);

            // SXSSFSheet 생성
            XSSFSheet sheet = workbook.createSheet();
            //sheet.setRandomAccessWindowSize(100); // 메모리 행 100개로 제한, 초과 시 Disk로 flush

            // Cell 스타일 값
            sheet.setDefaultColumnWidth(25);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            //font.setFontName("Arial");
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short)9);
            //font.setColor(HSSFColor.WHITE.index);
            style.setFont(font);

            // header 생성(컬럼 title
            XSSFRow header = sheet.createRow(0);
            createColumnHeader(header);
            setHeaderStyle(header, style);

            // 엑셀에 출력할 List
            List<Object> resultList = (List<Object>) datas.get("list");

            if(resultList != null) {
                CellStyle commonstyle = workbook.createCellStyle();
                int rowCnt = 0;
                for(Object o: resultList) {
                    XSSFRow aRow = sheet.createRow(++rowCnt);
                    setEachRow(aRow, o, commonstyle, font);
                    //logger.info(String.valueOf(rowCnt));
                }
            }

            // Auto size the column widths
            for(int columnIndex=0; columnIndex<headerList.size(); columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }

            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            if (out != null) out.close();

            /*String _serverType = codeConfig.getServerType();
            HashMap<String, String> _ftpConf = getFtpConfig(_serverType);
            String _path = _ftpConf.get("excel-local-path");
            Path fileTempStorageLocation = Paths.get(_path).toAbsolutePath().normalize();
            createDirectories(fileTempStorageLocation);

            File file = new File(_path + fileName);
            FileOutputStream fos;

            fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();*/

            //ftpFileUpload((String) datas.get("lastPath"), _path + fileName);
        } catch (Exception e) {
            throw e;
        }
    }

    private void createColumnHeader(XSSFRow header) {
        int i = 0;
        for(String headerName: this.headerList) {
            header.createCell(i).setCellValue(headerName);
            i++;
        }
    }

    private void setHeaderStyle(XSSFRow header, CellStyle style) {
        for(int i=0; i<headerList.size(); i++) {
            style.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setBorderBottom(CellStyle.BORDER_THIN);
            header.getCell(i).setCellStyle(style);
        }
    }

    private void setEachRow(XSSFRow aRow, Object o, CellStyle commonstyle, Font font) {
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, String> dataMap = mapper.convertValue(o, LinkedHashMap.class);

        try{
            int i = 0;
            for(String key: dataMap.keySet()) {
                aRow.createCell(i).setCellValue(dataMap.get(key));

                commonstyle.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
                commonstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
                //테두리 선 (우,좌,위,아래)
                commonstyle.setBorderRight(CellStyle.BORDER_THIN);
                commonstyle.setBorderLeft(CellStyle.BORDER_THIN);
                commonstyle.setBorderTop(CellStyle.BORDER_THIN);
                commonstyle.setBorderBottom(CellStyle.BORDER_THIN);
                font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
                commonstyle.setFont(font);
                aRow.getCell(i).setCellStyle(commonstyle);

                i++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isGetter(Method method){
        if(!method.getName().startsWith("get"))      return false;
        if(method.getParameterTypes().length != 0)   return false;
        if(void.class.equals(method.getReturnType())) return false;
        return true;
    }


    private void createDirectories(Path storageLocation) {
        // ::todo Exception 처리 - 디렉토리 쓰기 권한이 없을 경우 예외 처리 추가 필요!
        try {
            Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
            //add owners permission
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            //add group permissions
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            //add others permissions
            perms.add(PosixFilePermission.OTHERS_READ);
            perms.add(PosixFilePermission.OTHERS_WRITE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);

            //Set<PosixFilePermission> permis = PosixFilePermissions.fromString("rwxrwxrwx");
            // 파일 속성
            FileAttribute<Set<PosixFilePermission>> attrib = PosixFilePermissions.asFileAttribute(perms);
            try {
                Files.createDirectories(storageLocation, attrib);
//            	Files.createDirectories(storageLocation);
                //outputLS(storageLocation);
                Files.setPosixFilePermissions(storageLocation, perms);
                //outputLS(storageLocation);
            } catch (Exception e) {
                new File(storageLocation.toString()).mkdir();
            }
        } catch (Exception ex) {
            throw new FileStorageUtilException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * FTP 파일 업로드(For L4)
     * @param uploadPath
     * @return
     * @throws UnknownHostException
     * @throws SftpException
     */
    private boolean ftpFileUpload(String uploadPath, String uploadFile) throws UnknownHostException, SftpException {
        boolean _isSuccess = false;
        try{
            String _serverType = codeConfig.getServerType();
            HashMap<String, String> ftpConfig;
            ftpConfig = getFtpConfig(_serverType);

            String host = ftpConfig.get("url");
            int port = Integer.parseInt(ftpConfig.get("port"));
            String userName = ftpConfig.get("username");
            String password = ftpConfig.get("password");
            String dir = ftpConfig.get("path") + uploadPath; //접근할 폴더가 위치할 경로

            FtpUtil util = new FtpUtil();
            util.init(host, userName, password, port);

            File _uploadFile = new File(uploadFile);
            util.upload(dir, _uploadFile);

            util.disconnect();
            _isSuccess = true;
        }catch (Exception e) {
            _isSuccess = false;
        }

        return _isSuccess;
    }

    public HashMap<String, String> getFtpConfig(String serverType) {
        HashMap<String, String> ftpConfig = new HashMap<String, String>();
        if(serverType.equalsIgnoreCase("dev")) {
            ftpConfig = codeConfig.getFtp().get("dev");
        }else if(serverType.equalsIgnoreCase("live")){
            ftpConfig = codeConfig.getFtp().get("live");
        }else if(serverType.equalsIgnoreCase("live-second")) {
            ftpConfig = codeConfig.getFtp().get("live-second");
        }
        return ftpConfig;
    }
}
