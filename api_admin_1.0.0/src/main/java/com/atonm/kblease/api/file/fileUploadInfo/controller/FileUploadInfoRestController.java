package com.atonm.kblease.api.file.fileUploadInfo.controller;


import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.file.fileUploadInfo.dto.FileUploadInfoDTO;
import com.atonm.kblease.api.file.fileUploadInfo.dto.FileUploadInfoSearchDTO;
import com.atonm.kblease.api.file.fileUploadInfo.service.FileUploadInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.atonm.core.api.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@RestController
public class FileUploadInfoRestController extends BaseRestController {
    private final FileUploadInfoService fileUploadInfoService;

    @Autowired
    FileUploadInfoRestController(FileUploadInfoService fileUploadInfoService) {
        this.fileUploadInfoService = fileUploadInfoService;
    }

    /*@GetMapping("/file-upload-info")
    public ResponseEntity<ApiResponse> getList(FileUploadInfoSearchDTO request) {
        return ok(fileUploadInfoService.getList(request));
    }*/

    @GetMapping("/file-upload-info/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable Long id) {
        return ok(fileUploadInfoService.getOne(id));
    }

    @PostMapping("/file-upload-info")
    public ResponseEntity<ApiResponse> register(@RequestBody FileUploadInfoDTO request) {
        return ok(fileUploadInfoService.save(request));
    }

    @PutMapping("/file-upload-info")
    public ResponseEntity<ApiResponse> update(@RequestBody FileUploadInfoDTO request) {
        return ok(fileUploadInfoService.save(request));
    }

    @DeleteMapping("/file-upload-info/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        fileUploadInfoService.delete(id);

        return ok();
    }

    /**
     * 2018.12.19
     * mapper 전환
     * @param request
     * @return
     */
    @GetMapping("/file-upload-info")
    public ResponseEntity<ApiResponse> getListCvt(FileUploadInfoSearchDTO request) {
        return ok(fileUploadInfoService.getListCvt(request));
    }

    @PostMapping("/profile/demo-file-upload")
    public ResponseEntity<ApiResponse> attachFileUpload (@ModelAttribute FileUploadInfoDTO request, @RequestParam("file") MultipartFile file) {
        return ok ( fileUploadInfoService.attachFileUpload(request, file));
    }
}
