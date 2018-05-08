package com.atu.dao.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/07
 */
@Data
@Builder
public class ChnFamilyNameQueryDO {

    static public final int DEFAULT_PAGE_SIZE = 100;

    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;

    public int getOffset() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return (pageNo - 1) * pageSize;
    }
    
}
