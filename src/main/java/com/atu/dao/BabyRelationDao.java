package com.atu.dao;

import com.atu.dao.sqlbuilder.BabyRelationSqlBuilder;
import com.atu.model.BabyRelationDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/20
 */
@Mapper
@Repository
public interface BabyRelationDao {
    @InsertProvider(type = BabyRelationSqlBuilder.class, method = "insert")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "babyRelationDO.id", before = false,
        resultType = int.class)
    void insert(@Param("babyRelationDO") BabyRelationDO babyRelationDO);

    @SelectProvider(type = BabyRelationSqlBuilder.class, method = "queryByUserId")
    BabyRelationDO queryByUserId(@Param("userId") int userId, @Param("babyId") int babyId);
}
