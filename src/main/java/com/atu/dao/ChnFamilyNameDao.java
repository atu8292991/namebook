package com.atu.dao;

import java.util.List;

import com.atu.dao.model.ChnFamilyNameQueryDO;
import com.atu.dao.sqlbuilder.ChnFamilyNameSqlBuilder;
import com.atu.model.ChineseFamilyNameDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/07
 */
@Mapper
@Repository
public interface ChnFamilyNameDao {

    @InsertProvider(type = ChnFamilyNameSqlBuilder.class, method = "insert")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "chineseFamilyNameDO.id", before = false,
        resultType = int.class)
    void insert(@Param("chineseFamilyNameDO") ChineseFamilyNameDO chineseFamilyNameDO);

    @SelectProvider(type = ChnFamilyNameSqlBuilder.class, method = "queryByCondition")
    List<ChineseFamilyNameDO> queryByCondition(@Param("queryDO") ChnFamilyNameQueryDO queryDO);

    @SelectProvider(type = ChnFamilyNameSqlBuilder.class, method = "queryCount")
    int queryCount(@Param("queryDO") ChnFamilyNameQueryDO queryDO);
    
}
