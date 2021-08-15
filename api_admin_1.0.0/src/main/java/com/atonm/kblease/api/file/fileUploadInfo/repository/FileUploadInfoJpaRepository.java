package com.atonm.kblease.api.file.fileUploadInfo.repository;

import com.atonm.kblease.api.file.fileUploadInfo.entity.FileUploadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Repository
public interface FileUploadInfoJpaRepository extends JpaRepository<FileUploadInfo, Long> {
}
