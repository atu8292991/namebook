package com.atu.dao.sqlbuilder;

import com.atu.model.NameRepoDO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/19
 */
public class NameRepoSqlBuilder {
    public static final String NAME_REPO_TABLE_NAME = "tb_name_repo";

    public String insert(NameRepoDO nameRepoDO) {

        return new SQL().INSERT_INTO(NAME_REPO_TABLE_NAME)
            .VALUES("family_name", "#{nameRepoDO.familyName}")
            .VALUES("given_name", "#{nameRepoDO.givenName}")
            .VALUES("elements", "#{nameRepoDO.elements}")
            .VALUES("name_from", "#{nameRepoDO.nameFrom}")
            .VALUES("name_desc", "#{nameRepoDO.nameDesc}")
            .VALUES("gender_pct", "#{nameRepoDO.genderPct}")
            .VALUES("pinyin_fn", "#{nameRepoDO.pinyinFn}")
            .VALUES("pinyin_gn", "#{nameRepoDO.pinyinGn}")
            .VALUES("out_id", "#{nameRepoDO.outId}")
            .toString();
    }

    public String queryByOutId(int outId) {
        return new SQL().SELECT("*")
            .FROM(NAME_REPO_TABLE_NAME)
            .WHERE("out_id = #{outId}")
            .toString();
    }
}
