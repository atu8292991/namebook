package com.atu.dao.sqlbuilder;

import com.atu.dao.model.ChnFamilyNameQueryDO;
import com.atu.model.ChineseFamilyNameDO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/07
 */
public class ChnFamilyNameSqlBuilder {

    public static final String CHN_FAMILY_NAME_TABLE_NAME = "tb_chn_family_name";

    public String insert(ChineseFamilyNameDO chineseFamilyNameDO) {

        return new SQL().INSERT_INTO(CHN_FAMILY_NAME_TABLE_NAME)
            .VALUES("family_name", "#{chineseFamilyNameDO.familyName}")
            .VALUES("char_id", "#{chineseFamilyNameDO.charId}")
            .VALUES("resgain_url", "#{chineseFamilyNameDO.resgainUrl}")
            .toString();
    }

    public String queryByCondition(ChnFamilyNameQueryDO queryDO) {
        SQL sql = new SQL();
        sql.SELECT("*").FROM(CHN_FAMILY_NAME_TABLE_NAME);
        buildWhereCondition(sql, queryDO);
        sql.ORDER_BY("id limit #{queryDO.offset}, #{queryDO.pageSize}");
        return sql.toString();
    }

    public String queryCount(ChnFamilyNameQueryDO queryDO) {
        SQL sql = new SQL();
        sql.SELECT("count(1)").FROM(CHN_FAMILY_NAME_TABLE_NAME);
        buildWhereCondition(sql, queryDO);
        return sql.toString();
    }

    private void buildWhereCondition(SQL sql, ChnFamilyNameQueryDO queryDO) {
    }
    
}
