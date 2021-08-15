package com.atonm.kblease.api.file.fileUploadInfo.service;


import com.atonm.kblease.api.common.base.BoardDTO;
import com.atonm.kblease.api.file.fileStorageUtil.property.FileStorageUtilProperties;
import com.atonm.kblease.api.file.fileStorageUtil.service.FileStorageUtilService;
import com.atonm.kblease.api.file.fileUploadInfo.entity.FileUploadInfo;
import com.atonm.kblease.api.file.fileUploadInfo.mapper.FileUploadMapper;
import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.common.base.BaseService;
import com.atonm.kblease.api.file.fileUploadInfo.dto.FileUploadInfoDTO;
import com.atonm.kblease.api.file.fileUploadInfo.dto.FileUploadInfoSearchDTO;
import com.atonm.kblease.api.file.fileUploadInfo.repository.FileUploadInfoJpaRepository;
import com.atonm.kblease.api.utils.StringUtil;
import com.atonm.kblease.api.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.atonm.kblease.api.utils.CheckUtils.nonNull;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Service
public class FileUploadInfoService extends BaseService {
    private final FileUploadInfoJpaRepository fileUploadInfoJpaRepository;
    private final FileStorageUtilProperties fileStorageUtilProperties;
    private final FileUploadMapper fileUploadMapper;

    @Value("${file.upload-dir}")
    private String rootPath;

    @Value("${file.image-url}")
    private String imageUrl;

    @Autowired
    private FileStorageUtilService fileStorageUtilService;

    @Autowired
    FileUploadInfoService(FileUploadInfoJpaRepository fileUploadInfoJpaRepository, FileUploadMapper fileUploadMapper, FileStorageUtilProperties fileStorageUtilProperties) {
        this.fileUploadInfoJpaRepository = fileUploadInfoJpaRepository;
        this.fileUploadMapper = fileUploadMapper;
        this.fileStorageUtilProperties = fileStorageUtilProperties;
    }

    public ApiResponse save(FileUploadInfoDTO request) {
        AtomicInteger resultCount = new AtomicInteger(0);

        if (CheckUtils.nonNull(request.getFileUploadInfos())) {
            List<FileUploadInfoDTO> fileUploadInfos = request.getFileUploadInfos();

            fileUploadInfos.forEach((fileUploadInfo) -> {
                resultCount.getAndIncrement();
                fileUploadInfoJpaRepository.save(map(fileUploadInfo, FileUploadInfo.class));
            });
        }

        return ok(resultCount.get());
    }


    public int saveFile(FileUploadInfoDTO request) {
        return fileUploadMapper.saveFile(request);
    }

    public int deleteFile(FileUploadInfoDTO request) {
        return fileUploadMapper.deleteFiles(request);
    }

    public ApiResponse getOne(Long id) {
        return ok(map(fileUploadInfoJpaRepository.getOne(id), FileUploadInfoDTO.class));
    }

    public void delete(Long id) {
        fileUploadInfoJpaRepository.deleteById(id);
    }


    /**
     * 외부에 드러나는 파일업로드(링크)
     * @param files
     * @return
     */
    public List<FileUploadInfoDTO> convert(String category, MultipartFile... files) {
        List<FileUploadInfoDTO> result = new ArrayList<>();
        FileUploadInfoDTO dto;
        for (int i = 0; i < files.length; i++) {
            if(files[i] == null) {
                break;
            }
            MultipartFile file = files[i];
            dto = new FileUploadInfoDTO();
            String orgFileName = file.getOriginalFilename();
            String type = orgFileName.substring(orgFileName.lastIndexOf(".") + 1);

            String fileName = fileStorageUtilService.storeOuterFile(category, file, type);
            //String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            String fileUrl = StringUtil.replaceAll(fileName, this.fileStorageUtilProperties.getOuterUploadDir(), "/link");
            //String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileUrl).toUriString();
            String fileDownloadUri = fileUrl;

            dto.setFilePath(fileName);
            dto.setUrl(fileDownloadUri);
            dto.setUseYn("Y");
            dto.setSortNo((long) i);
            result.add(dto);
        }

        return result;
    }

    /**
     * 내부적으로 감추는 파일업로드
     * @param category
     * @param files
     * @return
     */
    public List<FileUploadInfoDTO> convertLocal(String category, MultipartFile... files) {
        List<FileUploadInfoDTO> result = new ArrayList<>();
        FileUploadInfoDTO dto;
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            dto = new FileUploadInfoDTO();
            String orgFileName = file.getOriginalFilename();
            String type = orgFileName.substring(orgFileName.lastIndexOf(".") + 1);

            String fileName = fileStorageUtilService.storeLocalFile(category, file, type);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/upload").path(fileName).toUriString();

            dto.setFilePath(fileName);
            //dto.setUrl(fileDownloadUri);
            dto.setUrl(fileName);
            dto.setUseYn("Y");
            dto.setSortNo((long) i);
            result.add(dto);
        }

        return result;
    }

    public ApiResponse getListCvt(FileUploadInfoSearchDTO request) {
        return ok(fileUploadMapper.getFiles(request));
    }

    public List<FileUploadInfoDTO> getListDataCvt(FileUploadInfoSearchDTO request) {
        return fileUploadMapper.getFiles(request);
    }

    /**
     * 서비스용 파일 업로드 (외부에 드러나는 파일)
     * @param request
     * @return
     */
    public List<FileUploadInfoDTO> convertService(BoardDTO request) {
        List<FileUploadInfoDTO> result = new ArrayList<>();
        FileUploadInfoDTO dto;

        for (int i = 0; i < request.getFiles().length; i++) {
            if(request.getFiles()[i] == null) {
                break;
            }
            MultipartFile file = request.getFiles()[i];
            dto = new FileUploadInfoDTO();
            String orgFileName = file.getOriginalFilename();
            String type = orgFileName.substring(orgFileName.lastIndexOf(".") + 1);

            String fileName = fileStorageUtilService.storeServiceOuterFile(request, file, type);
            String fileUrl = StringUtil.replaceAll(fileName, this.fileStorageUtilProperties.getOuterUploadDir(), "/link");
            String fileDownloadUri = fileUrl;

            dto.setServiceBoard(request.getServiceBoard());
            dto.setFilePath(fileName);
            dto.setUrl(fileDownloadUri);
            dto.setUseYn("Y");
            dto.setSortNo((long) i);
            dto.setCreateUid(request.getCreateUid());
            result.add(dto);
        }

        return result;
    }

    /**
     * 공지사항, 이벤트 에디터 이미지 등록/수정 시 사용.
     * @param filePath
     * @param cnt
     * @return
     */
    public FileUploadInfoDTO fileDataGet(String filePath, int cnt) {

        FileUploadInfoDTO dto = new FileUploadInfoDTO();

        File file = new File(filePath);

        String fileName = filePath;
        String fileUrl = StringUtil.replaceAll(fileName, this.fileStorageUtilProperties.getOuterUploadDir(), "/link");
        String fileDownloadUri = fileUrl;

        dto.setFilePath(fileName);
        dto.setUrl(fileDownloadUri);
        dto.setSortNo((long) cnt);

        return dto;
    }

    /**
     * 이벤트, 공지사항 파일 sort
     * @param board
     */
    public void editorSortFile(BoardDTO board) {

        String[] fileUrlList = board.getListImgSrc();
        int cnt = 1;

        FileUploadInfoDTO dto = new FileUploadInfoDTO();

        dto.setServiceBoard(board.getServiceBoard());
        dto.setBoardNo(board.getBoardNo());
        dto.setUpdateUid(board.getUpdateUid());
        dto.setFileUrl(fileUrlList);

        for(String fileUrl : fileUrlList){
            dto.setUrl(fileUrl);
            dto.setSortNo((long)cnt);
            fileUploadMapper.updateEventSortNo(dto);
            cnt++;
        }

        fileUploadMapper.deleteEditorFile(dto);
    }

    /*@Autowired
    FileUploadInfoService(FileUploadInfoJooqRepository fileUploadInfoJooqRepository, FileUploadInfoJpaRepository fileUploadInfoJpaRepository) {
        this.fileUploadInfoJooqRepository = fileUploadInfoJooqRepository;
        this.fileUploadInfoJpaRepository = fileUploadInfoJpaRepository;
    }

    public ApiResponse getList(FileUploadInfoSearchDTO request) {
        return ok(fileUploadInfoJooqRepository.getList(request));
    }

    public ApiResponse save(FileUploadInfoDTO request) {
        AtomicInteger resultCount = new AtomicInteger(0);

        if (nonNull(request.getFileUploadInfos())) {
            List<FileUploadInfoDTO> fileUploadInfos = request.getFileUploadInfos();

            fileUploadInfos.forEach((fileUploadInfo) -> {
                resultCount.getAndIncrement();
                fileUploadInfoJpaRepository.save(map(fileUploadInfo, FileUploadInfo.class));
            });
        }

        return ok(resultCount.get());
    }

    public ApiResponse getOne(Long id) {
        return ok(map(fileUploadInfoJpaRepository.getOne(id), FileUploadInfoDTO.class));
    }

    public void delete(Long id) {
        fileUploadInfoJpaRepository.deleteById(id);
    }*/

    public ApiResponse attachFileUpload (FileUploadInfoDTO request, MultipartFile file) {
        if(file == null) return badRequest();

        String  orgFileName =   file.getOriginalFilename();
        String  type        =   orgFileName.substring(orgFileName.lastIndexOf(".") + 1);

        String  fileName    =   fileStorageUtilService.storeServiceOuterAttachFileUpload ( request, file, type );
        String  fileUrl     =   "";

        return ok(fileName);
    }
}
