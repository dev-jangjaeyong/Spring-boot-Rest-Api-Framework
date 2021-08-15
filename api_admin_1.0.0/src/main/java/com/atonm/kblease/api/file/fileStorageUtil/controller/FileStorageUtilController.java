package com.atonm.kblease.api.file.fileStorageUtil.controller;

import com.atonm.kblease.api.file.fileUploadInfo.dto.FileUploadInfoDTO;
import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.file.fileStorageUtil.dto.FileStorageUtilDTO;
import com.atonm.kblease.api.file.fileStorageUtil.property.UploadFileResponse;
import com.atonm.kblease.api.file.fileStorageUtil.service.FileStorageUtilService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jang jae young
 * @since 2018-11-20
 */
@RestController
public class FileStorageUtilController {

    @Autowired
    private FileStorageUtilService fileStorageUtilService;

    @PostMapping("/uploadFile/{category}")
    public UploadFileResponse uploadFile(@PathVariable String category, @RequestParam("file") MultipartFile file) {
        String orgFileName = file.getOriginalFilename();
        String type = orgFileName.substring(orgFileName.lastIndexOf(".") + 1);
        String fileName = fileStorageUtilService.storeOuterFile(category, file, type);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    /*@PostMapping("/uploadFile/{type}")
    public UploadFileResponse uploadFile(@PathVariable String type, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageUtilService.storeTempFile(file, type);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }*/

    @PostMapping("/uploadMultipleFiles/{type}")
    public List<UploadFileResponse> uploadMultipleFiles(@PathVariable String type, @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(type, file))
                .collect(Collectors.toList());
    }

    @PostMapping("/fileNormalization/{type}")
    public ResponseEntity<ApiResponse> fileNormalization(@PathVariable String type, @RequestBody FileStorageUtilDTO fileStorageUtilDTO) {
        fileStorageUtilService.fileNormalization(fileStorageUtilDTO, type);

        return ResponseEntity.ok(ApiResponse.ok(1));
    }

    /**
     * 파일 다운로드(이미지)
     * @param fileStorageUtilDTO
     * @param req
     * @return
     * @throws IOException
     */
    @GetMapping("/download/image")
    public ResponseEntity<FileSystemResource> fileImageDownload(@ModelAttribute FileStorageUtilDTO fileStorageUtilDTO, HttpServletRequest req) throws IOException {
        FileUploadInfoDTO fileUploadInfoDTO = fileStorageUtilService.getFileById(fileStorageUtilDTO);

        if(fileUploadInfoDTO == null) {
            return null;
        }

        File outputFile = new File(fileUploadInfoDTO.getUrl());
        String ext = FilenameUtils.getExtension(fileUploadInfoDTO.getUrl());
        String name = outputFile.getName();



        //파일 인코딩
        String downName = "";
        String browser = req.getHeader("User-Agent");
        if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
            downName = URLEncoder.encode(name,"UTF-8").replaceAll("\\+", "%20");
        } else {
            downName = new String(name.getBytes("UTF-8"), "ISO-8859-1");
        }
        //String logiPath = fileVo.getLogiPath() + fileVo.getLogiNm();

        //java.io.File file = new File(logiPath);
        FileSystemResource fileSystemResource = new FileSystemResource(outputFile);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downName);

        ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<FileSystemResource>(fileSystemResource, responseHeaders, HttpStatus.OK);

        responseHeaders.add(HttpHeaders.CONTENT_LENGTH, Long.toString(fileSystemResource.contentLength()));

        return responseEntity;
    }
}
