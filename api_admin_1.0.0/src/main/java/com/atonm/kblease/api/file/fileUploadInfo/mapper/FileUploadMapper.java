package com.atonm.kblease.api.file.fileUploadInfo.mapper;

import com.atonm.kblease.api.file.fileStorageUtil.dto.FileStorageUtilDTO;
import com.atonm.kblease.api.file.fileUploadInfo.dto.FileUploadInfoDTO;
import com.atonm.kblease.api.file.fileUploadInfo.dto.FileUploadInfoSearchDTO;
import com.atonm.kblease.api.config.datasource.annotation.Master;

import java.util.List;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Master
public interface FileUploadMapper {

	int saveFile(FileUploadInfoDTO request) ;

	List<FileUploadInfoDTO> getFiles(FileUploadInfoSearchDTO request);

	FileUploadInfoDTO getFileById(FileStorageUtilDTO fileStorageUtilDTO);

	int deleteFiles(FileUploadInfoDTO request);

	/**
	 * 파일 임시 업로드 등록.
	 * @param request
	 */
	void insert(FileUploadInfoDTO request);

	/**
	 * 파일 데이터 완전 삭제
	 * @param request
	 */
	void delete(FileUploadInfoDTO request);

	/**
	 * 파일 sortNo 수정
	 * @param request
	 */
	void updateEventSortNo(FileUploadInfoDTO request);

	/**
	 * 이벤트,공지사항 에디터 파일 useYn = N 으로 수정
	 * @param request
	 */
	void deleteEditorFile(FileUploadInfoDTO request);
	//public ApiResponse getfileListCvt(FileUploadInfoSearchDTO search);
}
