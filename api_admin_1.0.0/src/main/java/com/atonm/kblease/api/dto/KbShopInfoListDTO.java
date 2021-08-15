package com.atonm.kblease.api.dto;


import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Data
@Alias("kbShopInfoListDTO")
public class KbShopInfoListDTO {
    private List<KbShopInfoDTO> editShopList;
}
