package com.atu.service;

import java.util.List;

import com.atu.dao.ChnCharsNormal7000Dao;
import com.atu.dao.model.ChnCharQueryDO;
import com.atu.model.ChineseCharDO;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.atu.model.ChineseElement.EARTH;
import static com.atu.model.ChineseElement.METAL;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
@Service
public class NameService {

    @Autowired
    private ChnCharsNormal7000Dao chnCharsNormal7000Dao;

    public String generateName() {
        ChnCharQueryDO queryDO = ChnCharQueryDO.builder()
            .elements(Sets.newHashSet(METAL, EARTH))
            .maxStrokeNumber(10)
            .build();

        List<ChineseCharDO> chineseCharDOS = chnCharsNormal7000Dao.queryRandomly(queryDO, 2);

        StringBuilder stringBuilder = new StringBuilder();

        chineseCharDOS.stream().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
    
}
