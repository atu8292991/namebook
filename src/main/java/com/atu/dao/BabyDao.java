package com.atu.dao;

import com.atu.dao.sqlbuilder.UserSqlBuilder;
import com.atu.model.BabyDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Mapper
@Repository
public interface BabyDao {

    @InsertProvider(type = UserSqlBuilder.class, method = "insert")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "babyDO.id", before = false,
        resultType = int.class)
    void insert(@Param("babyDO") BabyDO babyDO);
    
}
