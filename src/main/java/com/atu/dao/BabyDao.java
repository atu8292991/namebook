package com.atu.dao;

import java.util.List;

import com.atu.dao.sqlbuilder.BabySqlBuilder;
import com.atu.model.BabyDO;
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
public interface BabyDao {

    @InsertProvider(type = BabySqlBuilder.class, method = "insert")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "babyDO.id", before = false,
        resultType = int.class)
    void insert(@Param("babyDO") BabyDO babyDO);

    @SelectProvider(type = BabySqlBuilder.class, method = "queryByParentId")
    List<BabyDO> queryByParentId(@Param("parentId") int parentId);

    @SelectProvider(type = BabySqlBuilder.class, method = "queryById")
    BabyDO queryById(@Param("id") int id);

}
