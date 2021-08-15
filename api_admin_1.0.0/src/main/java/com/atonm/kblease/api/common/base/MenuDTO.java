package com.atonm.kblease.api.common.base;

import com.atonm.core.common.constant.Format;
import com.atonm.kblease.api.commapi.dto.BaseDTO;
import com.atonm.kblease.api.utils.CheckUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jang jea young
 * @since 2018-09-07
 */
@Data
@Alias("menuDTO")
@EqualsAndHashCode(callSuper = true)
public class MenuDTO extends BaseDTO {
    private Integer menuNo;

    private Integer upMenuNo;

    private String menuName;

    private String menuTyCd;

    private String menuTyCdName;

    private String menuUrl;

    private String dscrp;

    private String useYn;

    private Short sortNo;

    @JsonFormat(pattern = Format.LOCAL_DATE)
    private LocalDate useStDt;

    @JsonFormat(pattern = Format.LOCAL_DATE)
    private LocalDate useEdDt;

    private List<MenuDTO> children;

    private String parentMenuName; // 상위메뉴명

    private String text; // jsTree Node 라벨용.

    private Integer nodeNo; // jsTree Node.

    private Integer upNodeNo; // jsTree 상위 Node.

    private boolean selected; // 선택된

    private Long menuLv;

    private String menuChnnlCode;

    public void addChild(MenuDTO child) { // 하위 메뉴 추가
        if (CheckUtils.isNull(this.children)) {
            this.children = Lists.newArrayList();
        }

        this.children.add(child);
    }

    public void setDefaultUseDt() {
        this.setDefaultUseStDt();
        this.setDefaultUseEdDt();
    }

    private void setDefaultUseStDt() {
        if (this.useStDt == null) {
           this.useStDt = LocalDate.parse("1970-01-01");
        }
    }

    private void setDefaultUseEdDt() {
        if (this.useEdDt == null) {
            this.useEdDt = LocalDate.parse("9999-12-31");
        }
    }

    public boolean hasNoParent() {
        return CheckUtils.isNull(this.getUpMenuNo());
    }

    // 자식 데이터 확인
    public boolean hasChildren() {
        return CheckUtils.nonNull(this.getChildren());
    }
}
