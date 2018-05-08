package com.atu.dao.model;

import java.util.Set;

import com.atu.model.ChineseElement;
import com.atu.model.ChineseTone;
import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
@Data
@Builder
public class ChnCharQueryDO {

    static public final int DEFAULT_PAGE_SIZE = 100;

    private Set<ChineseElement> elements;

    private Set<ChineseTone> levelTones;

    private Integer maxStrokeNumber;

    private Integer minStrokeNumber;

    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;

    public void validate() {}

    public int getOffset() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return (pageNo - 1) * pageSize;
    }
    
}
