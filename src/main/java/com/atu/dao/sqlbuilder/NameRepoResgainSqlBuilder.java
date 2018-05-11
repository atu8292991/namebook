package com.atu.dao.sqlbuilder;

import com.atu.dao.model.NameRepoResgainQueryDO;
import com.atu.model.NameRepoResgainDO;
import com.google.common.base.Strings;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/08
 */
public class NameRepoResgainSqlBuilder {
    
    public static final String NAME_REPO_RESGAIN_TABLE_NAME = "tb_name_repo_resgain";

    public String insert(NameRepoResgainDO nameRepoResgainDO) {

        return new SQL().INSERT_INTO(NAME_REPO_RESGAIN_TABLE_NAME)
            .VALUES("family_name", "#{nameRepoResgainDO.familyName}")
            .VALUES("family_name_id", "#{nameRepoResgainDO.familyNameId}")
            .VALUES("given_name", "#{nameRepoResgainDO.givenName}")
            .VALUES("given_name_length", "#{nameRepoResgainDO.givenNameLength}")
            .VALUES("gender", "#{nameRepoResgainDO.gender}")
            .VALUES("detail_url", "#{nameRepoResgainDO.detailUrl}")
            .toString();
    }

    public String queryByCondition(NameRepoResgainQueryDO queryDO) {
        SQL sql = new SQL();
        sql.SELECT("*").FROM(NAME_REPO_RESGAIN_TABLE_NAME);
        buildWhereCondition(sql, queryDO);
        sql.ORDER_BY(queryDO.getOrderBy() + " limit #{queryDO.offset}, #{queryDO.pageSize}");
        return sql.toString();
    }

    private void buildWhereCondition(SQL sql, NameRepoResgainQueryDO queryDO) {
        if (!Strings.isNullOrEmpty(queryDO.getFamilyName())) {
            sql.WHERE("family_name=#{queryDO.familyName}");
        }
        if (null != queryDO.getGender()) {
            sql.WHERE("gender=#{queryDO.gender}");
        }
    }


}
