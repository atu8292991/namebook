package com.atu.dao.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/25
 */
@Data
@Builder
public class NameRepoQueryDO {
    static public final int DEFAULT_PAGE_SIZE = 100;

    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;

    @Builder.Default
    private NameRepoResgainQueryDO.OrderBy orderBy = NameRepoResgainQueryDO.OrderBy.ID;

    private String familyName;

    private Integer minPoint;

    public int getOffset() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return (pageNo - 1) * pageSize;
    }

    public enum OrderBy {
        ID("id"),
        RAND("rand()");
        private String column;
        OrderBy(String column) {
            this.column = column;
        }

        @Override
        public String toString() {
            return this.column;
        }
    }
}
