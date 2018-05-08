package com.atu.dao.sqlbuilder;

import com.atu.model.NameRepoResgainDO;
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

}
