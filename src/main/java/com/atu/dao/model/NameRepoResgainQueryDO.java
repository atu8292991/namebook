package com.atu.dao.model;

import com.atu.model.Gender;
import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/10
 */
@Data
@Builder
public class NameRepoResgainQueryDO {
    static public final int DEFAULT_PAGE_SIZE = 100;
    
    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;

    @Builder.Default
    private OrderBy orderBy = OrderBy.ID;

    private String familyName;

    private Gender gender;

    public int getOffset() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return (pageNo - 1) * pageSize;
    }

    public String getGender() {
        if (null == gender) {
            return null;
        }
        return gender.toString();
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
