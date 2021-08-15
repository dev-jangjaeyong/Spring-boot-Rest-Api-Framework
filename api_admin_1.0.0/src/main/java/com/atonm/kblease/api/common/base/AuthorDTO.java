package com.atonm.kblease.api.common.base;

import com.atonm.kblease.api.commapi.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author jang jae young
 * @since 2018-10-24
 */
@Data
@Alias("authorDTO")
@EqualsAndHashCode(callSuper = true)
public class AuthorDTO extends BaseDTO {
    /**
     * 권한번호
     */
    private Long authorNo;
    /**
     * 상위권한번호
     */
    private Long upAuthorNo;
    /**
     * 상위권한명
     */
    private String upAuthorName;
    /**
     * 권한명
     */
    private String prmisnAuthorName;
    /**
     * 권한유형
     */
    private String prmisnAuthorTyCd;
    /**
     * 권한설명
     */
    private String dscrp;
    /**
     * 사용여부
     */
    private String useYn;
    /**
     * text명 (treeView의 text)
     */
    private String text;
    /**
     * nodeNo명 (treeView의 nodeNo)
     */
    private Long nodeNo;
    /**
     * nodeNo명 (treeView의 upNodeNo)
     */
    private Long upNodeNo;
    /**
     * childre명 (treeView의 children)
     */
    private List<AuthorDTO> children;
}
