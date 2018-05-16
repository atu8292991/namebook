package com.atu.dao.sqlbuilder;

import com.atu.model.BabyDO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
public class BabySqlBuilder {

    public static final String BABY_TABLE_NAME = "tb_baby";

    public String insert(BabyDO babyDO) {

        return new SQL().INSERT_INTO(BABY_TABLE_NAME)
            .VALUES("family_name", "#{babyDO.familyName}")
            .VALUES("gender", "#{babyDO.gender}")
            .VALUES("nick", "#{babyDO.nick}")
            .VALUES("birth_time", "#{babyDO.birthTime}")
            .VALUES("father_id", "#{babyDO.fatherId}")
            .VALUES("mother_id", "#{babyDO.motherId}")
            .toString();
    }
    
}
