package com.atonm.kblease.api.commapi.dto;

import com.atonm.kblease.api.utils.CheckUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"parent"})
public class CommonCodeDTO extends BaseDTO {

    private Long codeNo;
    private Long upCodeNo;
    private String code;
    private String codeName;
    private Long sortNo;
    private String useYn;
    private String dscrp;
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;

    @JsonIgnore
    private CommonCodeDTO parent;

    private List<CommonCodeDTO> children;

    public boolean hasNoParent() {
        return CheckUtils.isNull(this.getUpCodeNo());
    }

    public void addChild(CommonCodeDTO child) {
        if (CheckUtils.isNull(this.children)) {
            this.children = Lists.newArrayList();
        }

        this.children.add(child);
    }
}
