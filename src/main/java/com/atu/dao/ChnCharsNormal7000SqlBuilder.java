package com.atu.dao;

import com.atu.model.ChineseCharDO;
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

    public String queryChineseCharById(long id) {
        return new SQL().SELECT("*")
            .FROM(CHN_CHARS_NORMAL_7000_TABLE_NAME)
            .WHERE("id = #{id}")
            .toString();
    }
    
}
