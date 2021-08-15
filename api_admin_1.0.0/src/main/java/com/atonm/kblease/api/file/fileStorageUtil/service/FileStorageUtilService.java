package com.atonm.kblease.api.file.fileStorageUtil.service;

import com.atonm.kblease.api.common.base.BoardDTO;
import com.atonm.kblease.api.config.generate.CodeConfig;
import com.atonm.kblease.api.config.property.ImageServerProperty;
import com.atonm.kblease.api.file.fileStorageUtil.dto.FileStorageUtilDTO;
import com.atonm.kblease.api.file.fileStorageUtil.exception.FileStorageUtilException;
import com.atonm.kblease.api.file.fileStorageUtil.property.FileStorageUtilProperties;
import com.atonm.kblease.api.file.fileUploadInfo.dto.FileUploadInfoDTO;
import com.atonm.kblease.api.file.fileUploadInfo.mapper.FileUploadMapper;
import com.atonm.kblease.api.utils.DateUtil;
import com.atonm.kblease.api.utils.FtpUtil;
import com.atonm.kblease.api.utils.ImageUtil;
import com.atonm.kblease.api.utils.StringUtil;
import com.atonm.core.common.constant.Constant;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Slf4j
@Service
public class FileStorageUtilService {
    private final FileStorageUtilProperties fileStorageUtilProperties;
    private final String todayDate;
    private final String todayYear;
    private final String todayMonthDay;

    private Path fileTempStorageLocation;
    private Path fileRegularStorageLocation;

    @Autowired
    FileUploadMapper fileUploadMapper;

    @Autowired
    private ImageServerProperty imageServerProperty;


    @Autowired
    private CodeConfig codeConfig;


    @Autowired
    public FileStorageUtilService(FileStorageUtilProperties fileStorageUtilProperties) {
        this.todayDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        this.todayYear = DateUtil.getDateFormat("YYYY");
        this.todayMonthDay = DateUtil.getDateFormat("MMdd");

        this.fileStorageUtilProperties = fileStorageUtilProperties;
    }

    /**
     * 외부 파일저장
     * @param category
     * @param file
     * @param type
     * @return
     */
    public String storeOuterFile(String category, MultipartFile file, String type) {
        if(file.getSize() == 0) {
            throw new FileStorageUtilException("File size is Zero");
        }
        // make file Temp Storage Location
        fileTempStorageLocation = Paths.get(this.fileStorageUtilProperties.getOuterUploadDir() + "/board/" + category + "/" + todayYear + "/" + todayMonthDay + "/").toAbsolutePath().normalize();

        createDirectories(Paths.get(this.fileStorageUtilProperties.getOuterUploadDir() + "/board/" + category + "/").toAbsolutePath().normalize());
        createDirectories(Paths.get(this.fileStorageUtilProperties.getOuterUploadDir() + "/board/" + category + "/" + todayYear + "/").toAbsolutePath().normalize());

        // Create Temp Storage Directories
        createDirectories(fileTempStorageLocation);

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // fileName 재정의
        String fileNameReal = DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 8) + "." + type;

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageUtilException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileTempStorageLocation.resolve(fileNameReal);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
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

            try {
                Files.setPosixFilePermissions(targetLocation, perms);
            } catch (Exception e) {
                new File(targetLocation.toString()).mkdir();
            }
            //ftpFileUpload(fileTempStorageLocation.toString(), targetLocation.toString());

            return this.fileStorageUtilProperties.getOuterUploadDir() + "/board/" + category + "/" + todayYear + "/" + todayMonthDay + "/" + fileNameReal;
        } catch (IOException ex) {
            throw new FileStorageUtilException("Could not store file " + fileName + ". Please try again!", ex);
        } /*catch (SftpException e) {
            throw new FileStorageUtilException("Could not ftp store file " + fileName + ". Please try again!", e);
        }*/
    }

    /**
     * 내부 파일 저장
     * @param category
     * @param file
     * @param type
     * @return
     */
    public String storeLocalFile(String category, MultipartFile file, String type) {
        if(file.getSize() == 0) {
            throw new FileStorageUtilException("File size is Zero");
        }
        // make file Temp Storage Location
        //fileTempStorageLocation = Paths.get(this.fileStorageUtilProperties.getUploadDir() + "/temp/" + this.todayDate + "/" + type).toAbsolutePath().normalize();

        String  _path   =   this.fileStorageUtilProperties.getInnerUploadDir() + "/" + category + "/" + todayYear + "/" + todayMonthDay + "/"  ;
        fileTempStorageLocation = Paths.get(_path).toAbsolutePath().normalize();


        createDirectories(Paths.get( _path).toAbsolutePath().normalize());
        createDirectories(Paths.get( _path +  category + "/" + todayYear + "/" + todayMonthDay + "/" ).toAbsolutePath().normalize());

        // Create Temp Storage Directories
        createDirectories(fileTempStorageLocation);

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // fileName 재정의
        String fileNameReal = DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 8) + "." + type;


        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageUtilException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileTempStorageLocation.resolve(fileNameReal);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

/*
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

            try {
                Files.setPosixFilePermissions(targetLocation, perms);
            } catch (Exception e) {
                new File(targetLocation.toString()).mkdir();
            }
*/

            // ftpFileUpload(fileTempStorageLocation.toString(), targetLocation.toString());

//            return this.fileStorageUtilProperties.getInnerUploadDir() + "/" + category + "/" + todayYear + "/" + todayMonthDay + "/" + fileNameReal;
            return _path + fileNameReal;
        } catch (IOException ex) {
            throw new FileStorageUtilException("Could not store file " + fileName + ". Please try again!", ex);
        } /*catch (SftpException e) {
            throw new FileStorageUtilException("Could not ftp store file " + fileName + ". Please try again!", e);
        }*/
    }

    /**
     * 차량 이미지 등록
     * @param file
     * @param carNo
     * @return
     */
    public HashMap<String, String> storeOnCarImage(MultipartFile file, Long carNo) {
        if(file.getSize() == 0) {
            throw new FileStorageUtilException("File size is Zero");
        }
        // make file Temp Storage Location
        fileTempStorageLocation = Paths.get(this.fileStorageUtilProperties.getOuterUploadDir() + "/carimage/" + String.valueOf(carNo) + "/" + todayYear + "/" + todayMonthDay + "/").toAbsolutePath().normalize();

        createDirectories(Paths.get(this.fileStorageUtilProperties.getOuterUploadDir() + "/carimage/").toAbsolutePath().normalize());
        createDirectories(Paths.get(this.fileStorageUtilProperties.getOuterUploadDir() + "/carimage/" + String.valueOf(carNo) + "/").toAbsolutePath().normalize());
        createDirectories(Paths.get(this.fileStorageUtilProperties.getOuterUploadDir() + "/carimage/" + String.valueOf(carNo) + "/" + todayYear + "/").toAbsolutePath().normalize());
        // Create Temp Storage Directories
        createDirectories(fileTempStorageLocation);

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);

        // fileName 재정의
        String fileNameReal = DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 10) + "." + type;
        String thumbFileNameReal = DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 10) + "_TH." + type;


        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageUtilException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileTempStorageLocation.resolve(fileNameReal);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Path targetThumbnailLocation = fileTempStorageLocation.resolve(thumbFileNameReal);
            ImageUtil imageUtil = new ImageUtil(file.getInputStream());
            BufferedImage thumbBuffer = imageUtil.resize(640, 480).getBuffer();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(thumbBuffer, type, os);
            Files.copy(new ByteArrayInputStream(os.toByteArray()), targetThumbnailLocation, StandardCopyOption.REPLACE_EXISTING);

            HashMap<String, String> onCarImageMap = new HashMap<>();
            onCarImageMap.put("oncarImagePath", this.fileStorageUtilProperties.getImageUrl() + String.valueOf(carNo) + "/" + todayYear + "/" + todayMonthDay + "/" + fileNameReal);
            onCarImageMap.put("oncarThumbImagePath", this.fileStorageUtilProperties.getImageUrl() + String.valueOf(carNo) + "/" + todayYear + "/" + todayMonthDay + "/" + thumbFileNameReal);

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
            try {
                Files.setPosixFilePermissions(targetLocation, perms);
                Files.setPosixFilePermissions(targetThumbnailLocation, perms);
            } catch (Exception e) {
                // TODO: handle exception
            }

            //ftpFileUpload(fileTempStorageLocation.toString(), targetLocation.toString());
            //ftpFileUpload(fileTempStorageLocation.toString(), targetThumbnailLocation.toString());

            return onCarImageMap;
        } catch (IOException ex) {
            throw new FileStorageUtilException("Could not store file " + fileName + ". Please try again!", ex);
        } /*catch (SftpException e) {
            throw new FileStorageUtilException("Could not ftp store file " + fileName + ". Please try again!", e);
        }*/
    }

    public void fileNormalization(FileStorageUtilDTO fileStorageUtilDTO, String type){
        fileRegularStorageLocation = Paths.get(this.fileStorageUtilProperties.getUploadDir() + "/regular/" + type + "/" + fileStorageUtilDTO.getBoardNo()).toAbsolutePath().normalize();
        createDirectories(fileRegularStorageLocation);

        List<String> fileNames = fileStorageUtilDTO.getFileNames();

        try {
            for (String fileName : fileNames) {
                Path tempFile = Paths.get(this.fileStorageUtilProperties.getUploadDir() + "/temp/" + this.todayDate + "/" + type + "/" + fileName).toAbsolutePath().normalize();

                if (fileRegularStorageLocation.resolve(tempFile.getFileName()).toFile().exists()) {
                    Files.delete(fileRegularStorageLocation.resolve(fileName));
                }

                Files.move(tempFile, fileRegularStorageLocation.resolve(tempFile.getFileName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageUtilException("Could not move the files from temp storage directory", e);
        }
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

    public FileUploadInfoDTO getFileById(FileStorageUtilDTO fileStorageUtilDTO) {
        return fileUploadMapper.getFileById(fileStorageUtilDTO);
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

            // System.out.println("my _serverType : " + _serverType);

            if(_serverType.equalsIgnoreCase("live")) {
                _serverType = "live-second";
            }else{
                _serverType = "live";
            }

            HashMap<String, String> ftpConfig = new HashMap<>();
            ftpConfig = getFtpConfig(_serverType);

            String host = ftpConfig.get("url");
            int port = Integer.parseInt(ftpConfig.get("port"));
            String userName = ftpConfig.get("username");
            String password = ftpConfig.get("password");
            String dir = uploadPath; //접근할 폴더가 위치할 경로

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


    /**
     * 이미지파일 FTP 파일 업로드 ( 2019-10-29 added by 온병옥 )
     *
     * @param uploadPath
     * @return
     * @throws UnknownHostException
     * @throws SftpException
     */
    private boolean imageFileFtpUpload(String uploadPath, String uploadFile) throws UnknownHostException, SftpException {
        boolean _isSuccess = false;
        try{
            String  host        =   imageServerProperty.getUrl();
            int     port        =   Integer.parseInt ( imageServerProperty.getPort() );
            String  userName    =   imageServerProperty.getUsername();
            String  password    =   imageServerProperty.getPassword();
            String  dir         =   imageServerProperty.getPath() + "/" + uploadPath; //접근할 폴더가 위치할 경로



            FtpUtil util = new FtpUtil();
            util.init(host, userName, password, port);
            System.out.println("################### _uploadFile ");

            File _uploadFile = new File(uploadFile);
            util.upload(dir, _uploadFile);
            System.out.println("################### _uploadFile ");
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
        // 이미지 서버 FTP를 위한 설정값 가져오기
        else if (serverType.equalsIgnoreCase("image-server")) {
            ftpConfig = codeConfig.getFtp().get("image-server");
        }
        return ftpConfig;
    }

    /**
     * 서비스용 파일 업로드.
     * @param request
     * @param file
     * @return
     */
    public String storeServiceOuterFile(BoardDTO request, MultipartFile file, String type) {
        String dir = this.fileStorageUtilProperties.getUploadDir() ;
        if(request.getServiceBoard().equals("003")) {
            dir += "/event";
        }

        String subDir = "";

        if ( request.getServiceBoard().equals( Constant.IMG_UPLOAD_SYS_PASS ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.PASS_CAR.getValue() + "/";
        } else if ( request.getServiceBoard().equals( Constant.IMG_UPLOAD_SYS_DELAER ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.PASS_DEALER.getValue() + "/";
        } else if ( request.getServiceBoard().equals( Constant.CM_DEALER_IMAGE_PATH ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.CM_DEALER.getValue() + "/";
        } else if ( request.getServiceBoard().equals( Constant.IMG_UPLOAD_SYS_ADMIN ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.PASS_ADMIN.getValue() + "/";
        } else if ( request.getServiceBoard().equals( Constant.IMG_UPLOAD_SYS_CUSTOM_ADMIN ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.PASS_CUS_ADMIN.getValue() + "/";
        } else {
            subDir =   "/nt/";
        }

        String _path =  dir + subDir + todayYear + todayMonthDay + "/";
        fileTempStorageLocation = Paths.get(_path).toAbsolutePath().normalize();


        createDirectories(Paths.get(dir).toAbsolutePath().normalize());
        //createDirectories(Paths.get(dir + todayYear + todayMonthDay + "/").toAbsolutePath().normalize());

        // Create Temp Storage Directories
        createDirectories(fileTempStorageLocation);

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // fileName 재정의
        String  renameFileName  =   DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 8);
        String  fileNameReal    =   renameFileName + "." + type;

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageUtilException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileTempStorageLocation.resolve(fileNameReal);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
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

            try {
                Files.setPosixFilePermissions(targetLocation, perms);
            } catch (Exception e) {
                new File(targetLocation.toString()).mkdir();
            }

            // 워터마크 적용.
            File    waterMarkFile = ImageUtil.addImageWatermark( new File(fileStorageUtilProperties.getPassWatermarkFilePath()), new File(targetLocation.toString()), new File(targetLocation.toString() ) );

            /**
             * 리사이즈 이미지 가로 X 세로
             *      =   ( 원본이미지 가로 / 3 ) X ( 원본이미지 세로 / 3 )
             */
            // 원본 이미지 리사이징
            String  imgOriginalPath =  waterMarkFile.toString();                           // 원본 이미지 파일명
            String  imgTargetPath   =  _path + renameFileName + "_resize" + "." + type;    // 새 이미지 파일명
            String  imgFormat       =  type;                                               // 새 이미지 포맷. jpg, gif 등
            int     resizeRate      =   3;
            int     newWidth        =  ImageIO.read(waterMarkFile).getWidth() / resizeRate;     // 변경 할 넓이
            int     newHeigt        =  ImageIO.read(waterMarkFile).getHeight() / resizeRate;    // 변경 할 높이

            // 원본 이미지 가져오기
            Image   image           =   ImageIO.read(new File(imgOriginalPath));
            Image   resizeImage     =   image.getScaledInstance(newWidth, newHeigt, Image.SCALE_SMOOTH);

            // 새 이미지  저장하기
            BufferedImage   newImage    =   new BufferedImage(newWidth, newHeigt, BufferedImage.TYPE_INT_RGB);
            Graphics        graphics    =   newImage.getGraphics();

            graphics.drawImage(resizeImage, 0, 0, null);
            graphics.dispose();

            File    targetPathFile  =   new File(imgTargetPath);

            ImageIO.write(newImage, imgFormat, targetPathFile);

            imageFileFtpUpload(subDir + todayYear + todayMonthDay + "/",  targetPathFile.toString());

            return _path + targetPathFile.getName();

        } catch (IOException ex) {
            throw new FileStorageUtilException("Could not store file " + fileName + ". Please try again!", ex);
        }catch (SftpException e) {
            throw new FileStorageUtilException("Could not ftp store file " + fileName + ". Please try again!", e);
        }
    }

    /**
     * 서비스용 파일 내부 업로드.
     * @param request
     * @param file
     * @return
     */
    public String storeServiceInnerFile(BoardDTO request, MultipartFile file, String type) {
        String dir = this.fileStorageUtilProperties.getUploadDir() ;
        if(request.getServiceBoard().equals("003")) {
            dir += "/event";
        }

        String subDir = "";

        if ( request.getServiceBoard().equals( Constant.IMG_UPLOAD_SYS_PASS ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.PASS_CAR.getValue() + "/";
        } else if ( request.getServiceBoard().equals( Constant.IMG_UPLOAD_SYS_DELAER ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.PASS_DEALER.getValue() + "/";
        } else if ( request.getServiceBoard().equals( Constant.IMG_UPLOAD_SYS_ADMIN ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.PASS_ADMIN.getValue() + "/";
        } else if ( request.getServiceBoard().equals( Constant.IMG_UPLOAD_SYS_CUSTOM_ADMIN ) ) {
            subDir =   Constant.IMAGE_UPLOAD_PATH.PASS_CUS_ADMIN.getValue() + "/";
        } else {
            subDir =   "/nt/";        }

        String _path =  dir + subDir + todayYear + todayMonthDay + "/";
        fileTempStorageLocation = Paths.get(_path).toAbsolutePath().normalize();

        createDirectories(Paths.get( dir ).toAbsolutePath().normalize());
//        createDirectories(Paths.get(dir + todayYear + todayMonthDay + "/").toAbsolutePath().normalize());

        // Create Temp Storage Directories
        createDirectories ( fileTempStorageLocation );

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // fileName 재정의
        String fileNameReal = DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 8) + "." + type;

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageUtilException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileTempStorageLocation.resolve(fileNameReal);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

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

            try {
                Files.setPosixFilePermissions(targetLocation, perms);
            } catch (Exception e) {
                new File(targetLocation.toString()).mkdir();
            }

            // 워터마크 적용.
//            File waterMarkFile = ImageUtil.addImageWatermark( new File(fileStorageUtilProperties.getPassWatermarkFilePath()), new File(targetLocation.toString()), new File(targetLocation.toString() ) );

            // FTP를 통한 이미지파일을 웹서버에 업로드 수행
//            dir =   StringUtil.replaceAll(dir, this.fileStorageUtilProperties.getUploadDir(),"");
            imageFileFtpUpload(subDir + todayYear + todayMonthDay + "/", targetLocation.toString());

            return _path + fileNameReal;
        } catch (IOException ex) {
            throw new FileStorageUtilException("Could not store file " + fileName + ". Please try again!", ex);
        } catch (SftpException e) {
            throw new FileStorageUtilException("Could not ftp store file " + fileName + ". Please try again!", e);
        }
    }

    /**
     * 해당 경로에 파일이 존재하는지 체크.
     * @param path
     * @return
     */
    public boolean fileExist(String path) {
        boolean result = false;

        try {
            File file = new File(path);
            result = file.isFile();
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /*public String storeTempFile(MultipartFile file, String type) {
        // make file Temp Storage Location
        fileTempStorageLocation = Paths.get(this.fileStorageUtilProperties.getUploadDir() + "/temp/" + this.todayDate + "/" + type).toAbsolutePath().normalize();

        // Create Temp Storage Directories
        createDirectories(fileTempStorageLocation);

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageUtilException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileTempStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageUtilException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = fileTempStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public void fileNormalization(FileStorageUtilDTO fileStorageUtilDTO, String type){
        fileRegularStorageLocation = Paths.get(this.fileStorageUtilProperties.getUploadDir() + "/regular/" + type + "/" + fileStorageUtilDTO.getBoardNo()).toAbsolutePath().normalize();
        createDirectories(fileRegularStorageLocation);

        List<String> fileNames = fileStorageUtilDTO.getFileNames();

        try {
            for (String fileName : fileNames) {
                Path tempFile = Paths.get(this.fileStorageUtilProperties.getUploadDir() + "/temp/" + this.todayDate + "/" + type + "/" + fileName).toAbsolutePath().normalize();

                if (fileRegularStorageLocation.resolve(tempFile.getFileName()).toFile().exists()) {
                    Files.delete(fileRegularStorageLocation.resolve(fileName));
                }

                Files.move(tempFile, fileRegularStorageLocation.resolve(tempFile.getFileName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageUtilException("Could not move the files from temp storage directory", e);
        }
    }

    private void createDirectories(Path storageLocation) {
        // ::todo Exception 처리 - 디렉토리 쓰기 권한이 없을 경우 예외 처리 추가 필요!
        try {
            Files.createDirectories(storageLocation);
        } catch (Exception ex) {
            throw new FileStorageUtilException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }*/

    /**
     * 차량 이미지 등록
     * @param file
     * @param carNo
     * @return HashMap
     */
    public HashMap<String, String> OnCarImage(MultipartFile file, String carNo) {
        if(file.getSize() == 0) {
            throw new FileStorageUtilException("File size is Zero");
        }
        if(carNo.equals("")) {
            throw new FileStorageUtilException("carNo is null");
        }

        // make file Temp Storage Location
        String dir = this.fileStorageUtilProperties.getOuterUploadDir() ;
        String path = dir + "/carimage/" + String.valueOf(carNo) + "/" + todayYear + todayMonthDay + "/";
        fileTempStorageLocation = Paths.get(path).toAbsolutePath().normalize();

        createDirectories(Paths.get(dir + "/carimage/").toAbsolutePath().normalize());
        createDirectories(Paths.get(dir + "/carimage/" + String.valueOf(carNo) + "/").toAbsolutePath().normalize());
        createDirectories(Paths.get(dir + "/carimage/" + String.valueOf(carNo) + "/" + todayYear + todayMonthDay + "/").toAbsolutePath().normalize());

        createDirectories(fileTempStorageLocation);

        //로컬 테스트
        File fileDir = new File(path);
        if (!fileDir.isDirectory()) {
        	fileDir.mkdirs();
        }

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);

        // fileName 재정의
        String fileNameReal = DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 10) + "." + type;
        String thumbFileNameReal = DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 10) + "_TH." + type;

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageUtilException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileTempStorageLocation.resolve(fileNameReal);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Path targetThumbnailLocation = fileTempStorageLocation.resolve(thumbFileNameReal);
            ImageUtil imageUtil = new ImageUtil(file.getInputStream());
            BufferedImage thumbBuffer = imageUtil.resize(640, 480).getBuffer();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(thumbBuffer, type, os);
            Files.copy(new ByteArrayInputStream(os.toByteArray()), targetThumbnailLocation, StandardCopyOption.REPLACE_EXISTING);

            HashMap<String, String> onCarImageMap = new HashMap<>();
            String imgUrl = this.fileStorageUtilProperties.getImageUrl() + Constant.CM_DEALER_IMAGE_PATH + "/" +String.valueOf(carNo) + "/";
            onCarImageMap.put("carImagePath", imgUrl + todayYear + todayMonthDay + "/" + fileNameReal);
            onCarImageMap.put("carImageThumbnailPath", imgUrl + todayYear + todayMonthDay + "/" + thumbFileNameReal);
            onCarImageMap.put("carImageDscrp", fileName);

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
            try {
                Files.setPosixFilePermissions(targetLocation, perms);
                Files.setPosixFilePermissions(targetThumbnailLocation, perms);
            } catch (Exception e) {
                // TODO: handle exception
            }

            //이미지 ftp 전송
            String subDir = Constant.CM_DEALER_IMAGE_PATH + "/";
            imageFileFtpUpload(subDir + String.valueOf(carNo) + "/" + todayYear + todayMonthDay + "/",  targetLocation.toString());
            imageFileFtpUpload(subDir + String.valueOf(carNo) + "/" + todayYear + todayMonthDay + "/",  targetThumbnailLocation.toString());

            return onCarImageMap;
        } catch (IOException ex) {
            throw new FileStorageUtilException("Could not store file " + fileName + ". Please try again!", ex);
        }
        catch (SftpException e) {
        	throw new FileStorageUtilException("Could not ftp store file " + fileName + ". Please try again!", e);
        }
    }



    /**
     * 첨부파일 업로드.
     * @param request
     * @param file
     * @return
     */
    public String storeServiceOuterAttachFileUpload ( FileUploadInfoDTO request, MultipartFile file, String type) {
        String dir = this.fileStorageUtilProperties.getUploadDir() ;

        String  subDir  =   "";
        if( request.getServiceBoard() != null ) {
            subDir = "/" + request.getServiceBoard() + "/";
        }


        String _path =  dir + subDir + todayYear + todayMonthDay + "/";
        fileTempStorageLocation = Paths.get(_path).toAbsolutePath().normalize();

        createDirectories(Paths.get(dir).toAbsolutePath().normalize());

        // Create Temp Storage Directories
        createDirectories(fileTempStorageLocation);

        //로컬 테스트
        File fileDir = new File(_path);
        if (!fileDir.isDirectory()) {
            fileDir.mkdirs();
        }

        // Normalize file name
        String fileName = StringUtils.cleanPath ( file.getOriginalFilename() );

        // fileName 재정의
        String  renameFileName  =   DateUtil.getDateFormat("dd") + StringUtil.getRandom("abc", 8);
        String  fileNameReal    =   renameFileName + "." + type;

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageUtilException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileTempStorageLocation.resolve(fileNameReal);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
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

            try {
                Files.setPosixFilePermissions(targetLocation, perms);
            } catch (Exception e) {
                new File(targetLocation.toString()).mkdir();
            }

            File    targetFile  =   new File(_path + fileNameReal);
            imageFileFtpUpload(subDir + todayYear + todayMonthDay + "/",  targetFile.toString());

            return _path + fileNameReal;

        } catch (IOException ex) {
            throw new FileStorageUtilException("Could not store file " + fileName + ". Please try again!", ex);
        }catch (SftpException e) {
            throw new FileStorageUtilException("Could not ftp store file " + fileName + ". Please try again!", e);
        }
    }
}
