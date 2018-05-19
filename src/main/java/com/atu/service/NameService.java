package com.atu.service;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.atu.dao.ChnCharsNormal7000Dao;
import com.atu.dao.NameRepoDao;
import com.atu.dao.NameRepoResgainDao;
import com.atu.dao.model.ChnCharQueryDO;
import com.atu.manager.NameManager;
import com.atu.model.ChineseCharDO;
import com.atu.model.NameRepoDO;
import com.atu.model.NameRepoResgainDO;
import com.atu.model.dto.BatchNameTaskDTO;
import com.atu.repo.ResgainRepo;
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

    @Autowired
    private NameRepoResgainDao nameRepoResgainDao;

    @Autowired
    private NameRepoDao nameRepoDao;

    @Autowired
    private NameManager nameManager;

    @Autowired
    private ResgainRepo resgainRepo;

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

    public String generateNameTask(BatchNameTaskDTO batchNameTaskDTO) {
        List<NameRepoResgainDO> names = nameManager.batchQueryByRand(batchNameTaskDTO);
        return JSON.toJSONString(names);
    }

    public String fetchNameDetailResgain(int id) {
        NameRepoResgainDO nameRepoResgainDO = nameRepoResgainDao.queryById(id);
        NameRepoDO nameRepoDOFromDb = nameRepoDao.queryByOutId(nameRepoResgainDO.getId());
        if (null != nameRepoDOFromDb) {
            return JSON.toJSONString(nameRepoDOFromDb);
        } else {
            NameRepoDO nameRepoDO = resgainRepo.fetchNameDetail(nameRepoResgainDO);
            nameRepoDao.insert(nameRepoDO);
            return JSON.toJSONString(nameRepoDO);
        }
    }

}
