package com.atu.dao.sqlbuilder;

import com.atu.dao.model.ChnCharQueryDO;
import com.atu.dao.model.ChnCharUpdateDO;
import com.atu.model.ChineseCharDO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/03
 */
@Component
public class ChnCharsNormal7000SqlBuilder {

    public static final String CHN_CHARS_NORMAL_7000_TABLE_NAME = "tb_chn_chars_normal_7000";

    public String insertChnChar(ChineseCharDO chineseCharDO) {

        return new SQL().INSERT_INTO(CHN_CHARS_NORMAL_7000_TABLE_NAME)
            .VALUES("chn_char", "#{chineseCharDO.chnChar}")
            .VALUES("pinyin", "#{chineseCharDO.pinyin}")
            .VALUES("stroke_number", "#{chineseCharDO.strokeNumber}")
            .VALUES("level_tone", "#{chineseCharDO.levelTone}")
            .VALUES("element", "#{chineseCharDO.element}")
            .toString();
    }

    public String queryById(long id) {
        return new SQL().SELECT("*")
            .FROM(CHN_CHARS_NORMAL_7000_TABLE_NAME)
            .WHERE("id = #{id}")
            .toString();
    }

    public String queryByCondition(ChnCharQueryDO queryDO) {
        queryDO.validate();
        SQL sql = new SQL();
        sql.SELECT("*").FROM(CHN_CHARS_NORMAL_7000_TABLE_NAME);
        buildWhereCondition(sql, queryDO);
        sql.ORDER_BY(queryDO.getOrderBy() + " limit #{queryDO.offset}, #{queryDO.pageSize}");
        return sql.toString();
    }

    public String queryCount(ChnCharQueryDO queryDO) {
        queryDO.validate();
        SQL sql = new SQL();
        sql.SELECT("count(1)").FROM(CHN_CHARS_NORMAL_7000_TABLE_NAME);
        buildWhereCondition(sql, queryDO);
        return sql.toString();
    }

    public String updateById(ChnCharUpdateDO updateDO) {
        SQL sql = new SQL();
        sql.UPDATE(CHN_CHARS_NORMAL_7000_TABLE_NAME);

        if (updateDO.getElement() != null) {
            sql.SET("element=#{updateDO.element}");
        }
        if (updateDO.getLevelTone() != null) {
            sql.SET("level_tone=#{updateDO.levelTone}");
        }
        if (updateDO.getStrokeNumber() != null) {
            sql.SET("stroke_number=#{updateDO.strokeNumber}");
        }

        sql.WHERE("id=#{updateDO.id}");
        return sql.toString();
    }

    public String queryRandomly(ChnCharQueryDO queryDO, int count) {
        queryDO.validate();
        SQL sql = new SQL();
        sql.SELECT("*").FROM(CHN_CHARS_NORMAL_7000_TABLE_NAME);
        buildWhereCondition(sql, queryDO);
        sql.ORDER_BY("rand() limit #{count}");
        return sql.toString();
    }

    private void buildWhereCondition(SQL sql, ChnCharQueryDO queryDO) {
        if (CollectionUtils.isNotEmpty(queryDO.getElements())) {
            sql.WHERE("element in ("+ StringUtils.join(queryDO.getElements(), ",") + ")");
        }
        if (null != queryDO.getMaxStrokeNumber()) {
            sql.WHERE("stroke_number <= #{queryDO.maxStrokeNumber}");
        }
        if (null != queryDO.getMinStrokeNumber()) {
            sql.WHERE("stroke_number >= #{queryDO.minStrokeNumber}");
        }
        if (CollectionUtils.isNotEmpty(queryDO.getLevelTones())) {
            sql.WHERE("level_tone in ("+ StringUtils.join(queryDO.getLevelTones(), ",") + ")");
        }
    }
    
}
