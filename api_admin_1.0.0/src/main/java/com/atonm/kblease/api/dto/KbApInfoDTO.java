package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseEntity;
import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("kbApInfoDTO")
public class KbApInfoDTO extends BaseEntity {
    private String pid;
    private String channel;
    private String rowNumber;
    private String userNo;
    private String userOrgId;
    private String apId;
    private String cmCarNo;

    @ApiModelProperty(value = "")
    private String apSubmitType;

    @ApiModelProperty(value = "")
    private String apName;

    @ApiModelProperty(value = "")
    private String apHp;

    @ApiModelProperty(value = "")
    private String apKbId;

    @ApiModelProperty(value = "")
    private String apSafeHp;

    @ApiModelProperty(value = "영업권역")
    private String apSalesArea;

    @ApiModelProperty(value = "")
    private String apUseYn;

    @ApiModelProperty(value = "넘어온 프로필 이미지 이름")
    private String apProfileImgName;

    @ApiModelProperty(value = "프로필 이미지 - 상대경로")
    private String apProfileImgUrl;

    @ApiModelProperty(value = "")
    private String apCmId;

    @ApiModelProperty(value = "")
    private String apCmPassword;

    @ApiModelProperty(value = "")
    private String apNote;

    @ApiModelProperty(value = "카매니저 ShopNo")
    private String cmShopNo;

    @ApiModelProperty(value = "ap가 담당하고있는 상사의 수")
    private String apShopCount;

    @ApiModelProperty(value = "프로빌 변경 유무")
    private String profileImgChange;


}
