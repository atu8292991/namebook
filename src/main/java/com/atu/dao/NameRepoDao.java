package com.atu.dao;

import com.atu.dao.sqlbuilder.NameRepoSqlBuilder;
import com.atu.model.NameRepoDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/19
 */
@Mapper
@Repository
public interface NameRepoDao {
    @InsertProvider(type = NameRepoSqlBuilder.class, method = "insert")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "nameRepoDO.id", before = false,
        resultType = int.class)
    void insert(@Param("nameRepoDO") NameRepoDO nameRepoDO);

    @SelectProvider(type = NameRepoSqlBuilder.class, method = "queryByOutId")
    NameRepoDO queryByOutId(@Param("outId") int outId);
}
