package com.atonm.kblease.api.dto;


import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Data
@Alias("kbTermListDTO")
public class KbTermListDTO {
    private List<KbTermDTO> editTermlist;
}
