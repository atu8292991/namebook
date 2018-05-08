package com.atu.dao;

import com.atu.dao.sqlbuilder.NameRepoResgainSqlBuilder;
import com.atu.model.NameRepoResgainDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/08
 */
@Mapper
@Repository
public interface NameRepoResgainDao {
    
    @InsertProvider(type = NameRepoResgainSqlBuilder.class, method = "insert")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "nameRepoResgainDO.id", before = false,
        resultType = int.class)
    void insert(@Param("nameRepoResgainDO") NameRepoResgainDO nameRepoResgainDO);
}