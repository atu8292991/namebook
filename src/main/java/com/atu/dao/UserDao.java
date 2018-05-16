package com.atu.dao;

import com.atu.dao.sqlbuilder.UserSqlBuilder;
import com.atu.model.UserDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Mapper
@Repository
public interface UserDao {
    @InsertProvider(type = UserSqlBuilder.class, method = "insert")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "userDO.id", before = false,
        resultType = int.class)
    void insert(@Param("userDO") UserDO userDO);

    @SelectProvider(type = UserSqlBuilder.class, method = "queryByOpenId")
    UserDO queryByOpenId(@Param("openId") String openId);

    @SelectProvider(type = UserSqlBuilder.class, method = "queryById")
    UserDO queryById(@Param("id") int id);
    
}
