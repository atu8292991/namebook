package com.atu.dao;

import com.atu.model.ChineseCharDO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
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

    @SelectProvider(type = ChnCharsNormal7000SqlBuilder.class, method = "queryChineseCharById")
    ChineseCharDO queryChineseCharById(@Param("id") long id);

}
