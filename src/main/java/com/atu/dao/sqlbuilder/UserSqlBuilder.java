package com.atu.dao.sqlbuilder;

import com.atu.model.UserDO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
public class UserSqlBuilder {
    public static final String USER_TABLE_NAME = "tb_user";

    public String insert(UserDO userDO) {

        return new SQL().INSERT_INTO(USER_TABLE_NAME)
            .VALUES("nick_name", "#{userDO.nickName}")
            .VALUES("open_id", "#{userDO.openId}")
            .VALUES("country", "#{userDO.country}")
            .VALUES("province", "#{userDO.province}")
            .VALUES("city", "#{userDO.city}")
            .VALUES("gender", "#{userDO.gender}")
            .VALUES("language", "#{userDO.language}")
            .VALUES("avatar_url", "#{userDO.avatarUrl}")
            .toString();
    }

    public String queryByOpenId(String openId) {
        return new SQL().SELECT("*")
            .FROM(USER_TABLE_NAME)
            .WHERE("open_id = #{openId}")
            .toString();
    }

    public String queryById(int id) {
        return new SQL().SELECT("*")
            .FROM(USER_TABLE_NAME)
            .WHERE("id = #{id}")
            .toString();
    }

}
