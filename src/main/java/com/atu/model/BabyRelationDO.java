package com.atu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/20
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BabyRelationDO {
    
    private int id;
    private int babyId;
    private int userId;
    private Kinship relation;

    public int getRelation() {
        return relation.getCode();
    }

    public void setRelation(int code) {
        this.relation = Kinship.of(code);
    }

}
