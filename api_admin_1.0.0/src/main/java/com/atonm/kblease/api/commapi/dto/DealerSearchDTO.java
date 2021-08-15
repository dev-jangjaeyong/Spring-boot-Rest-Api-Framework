package com.atonm.kblease.api.commapi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DealerSearchDTO  extends CommApiBaseDTO {
	String taskId;
	String userName;
	String licenseNo;
	String younhapType;
}
