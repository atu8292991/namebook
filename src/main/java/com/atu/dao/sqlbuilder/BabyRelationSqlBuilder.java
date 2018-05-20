package com.atu.dao.sqlbuilder;

import com.atu.model.BabyRelationDO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/20
 */
public class BabyRelationSqlBuilder {
    public static final String BABY_RELATION_TABLE_NAME = "tb_baby_relation";

    public String insert(BabyRelationDO babyRelationDO) {

        return new SQL().INSERT_INTO(BABY_RELATION_TABLE_NAME)
            .VALUES("baby_id", "#{babyRelationDO.babyId}")
            .VALUES("user_id", "#{babyRelationDO.userId}")
            .VALUES("relation", "#{babyRelationDO.relation}")
            .toString();
    }

    public String queryByUserId(int userId, int babyId) {
        return new SQL().SELECT("*")
            .FROM(BABY_RELATION_TABLE_NAME)
            .WHERE("user_id = #{userId} and baby_id = #{babyId}")
            .toString();
    }
}
