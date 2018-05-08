package com.atu.dao;

import java.util.List;

import com.atu.dao.model.ChnCharQueryDO;
import com.atu.dao.model.ChnCharUpdateDO;
import com.atu.dao.sqlbuilder.ChnCharsNormal7000SqlBuilder;
import com.atu.model.ChineseCharDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/03
 */
@Mapper
@Repository
public interface ChnCharsNormal7000Dao {
    
    @InsertProvider(type = ChnCharsNormal7000SqlBuilder.class, method = "insertChnChar")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "chineseCharDO.id", before = false,
        resultType = int.class)
    void insertChnChar(@Param("chineseCharDO") ChineseCharDO chineseCharDO);

    @SelectProvider(type = ChnCharsNormal7000SqlBuilder.class, method = "queryById")
    ChineseCharDO queryById(@Param("id") long id);

    @SelectProvider(type = ChnCharsNormal7000SqlBuilder.class, method = "queryByCondition")
    List<ChineseCharDO> queryByCondition(@Param("queryDO") ChnCharQueryDO queryDO);

    @SelectProvider(type = ChnCharsNormal7000SqlBuilder.class, method = "queryCount")
    int queryCount(@Param("queryDO") ChnCharQueryDO queryDO);

    @UpdateProvider(type = ChnCharsNormal7000SqlBuilder.class, method = "updateById")
    void updateById(@Param("updateDO") ChnCharUpdateDO updateDO);

    @SelectProvider(type = ChnCharsNormal7000SqlBuilder.class, method = "queryRandomly")
    List<ChineseCharDO> queryRandomly(@Param("queryDO") ChnCharQueryDO queryDO, @Param("count")int count);

}
