package com.atonm.kblease.api.common.base;

import com.atonm.kblease.api.utils.CheckUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.atonm.kblease.api.commapi.dto.BaseDTO;

import java.util.List;

/**
 * @author jang jea young
 * @since 2018-10-24
 */
@Data
@Alias("orgnztUnitDTO")
@EqualsAndHashCode(callSuper = true)
public class OrgnztUnitDTO extends BaseDTO {
    private Long orgnztUnitNo;

    private Long upOrgnztUnitNo;

    private String name;

    private String fullName;

    private String dispName;

    private String dispFullName;


    private String areaCode;

    private String zipCode;

    private String addr;

    private String addrDetail;

    private String phoneNo;

    private String extensionPhoneNo;

    private String faxNo;

    private String webUrl;

    private String bizrno;

    private String jesiUseYn;

    private String dscrp;

    private String useYn;

    private String rprsntvName;

    private Long lv;

    private Boolean isLeaf = true; // jsTree 작성용.

    private List<OrgnztUnitDTO> orgnztChildren;

    // 하위 메뉴 추가
    public void addChild(OrgnztUnitDTO child) {
        if (CheckUtils.isNull(this.orgnztChildren)) {
            this.orgnztChildren = Lists.newArrayList();
        }

        this.orgnztChildren.add(child);
    }

    // 자식 데이터 확인
    public boolean hasChildren() {
        return CheckUtils.nonNull(this.getOrgnztChildren());
    }
}
