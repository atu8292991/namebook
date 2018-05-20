package com.atu.service;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.atu.dao.BabyDao;
import com.atu.dao.BabyRelationDao;
import com.atu.dao.UserDao;
import com.atu.model.BabyDO;
import com.atu.model.BabyRelationDO;
import com.atu.model.UserDO;
import com.atu.model.dto.BabyDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Service
public class BabyService {

    @Autowired
    private BabyDao babyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BabyRelationDao babyRelationDao;

    public String register(BabyDO babyDO) {
        babyDao.insert(babyDO);
        return "ok";
    }

    public String queryBabyByUserId(int userId) {
        List<BabyRelationDO> babyRelationDOS = babyRelationDao.queryByUserId(userId);
        List<BabyDTO> babyDTOS = Lists.newArrayListWithExpectedSize(babyRelationDOS.size());

        if (CollectionUtils.isNotEmpty(babyRelationDOS)) {
            for (BabyRelationDO babyRelationDO : babyRelationDOS) {
                BabyDO babyDO = babyDao.queryById(babyRelationDO.getBabyId());
                babyDTOS.add(fillBabyDTO(babyDO));
            }
        }
        return JSON.toJSONString(babyDTOS);
    }

    public String queryBabyByParentId(int userId) {
        List<BabyDO> babyDOS = babyDao.queryByParentId(userId);
        List<BabyDTO> babyDTOS = Lists.newArrayListWithExpectedSize(babyDOS.size());

        for (BabyDO baby : babyDOS) {
            babyDTOS.add(fillBabyDTO(baby));
        }
        return JSON.toJSONString(babyDTOS);
    }

    public String queryBabyById(int babyId) {
        BabyDO babyDO = babyDao.queryById(babyId);
        return JSON.toJSONString(fillBabyDTO(babyDO));
    }

    public String bind(BabyRelationDO babyRelationDO) {
        BabyRelationDO babyRelationDOFromDb = babyRelationDao.queryByUserIdAndBabyId(babyRelationDO.getUserId(),
            babyRelationDO.getBabyId());
        if (null == babyRelationDOFromDb) {
            babyRelationDao.insert(babyRelationDO);
        }
        return "ok";
    }

    public String queryRelation(int babyId, int userId) {
        BabyRelationDO babyRelationDO = babyRelationDao.queryByUserIdAndBabyId(userId, babyId);
        if (babyRelationDO != null) {
            return "ok";
        } else {
            return "fail";
        }
    }

    private BabyDTO fillBabyDTO(BabyDO babyDO){
        UserDO father = null;
        UserDO mother = null;
        if (babyDO.getFatherId() > 0) {
            father = userDao.queryById(babyDO.getFatherId());
        }
        if (babyDO.getMotherId() > 0) {
            mother = userDao.queryById(babyDO.getMotherId());
        }
        return BabyDTO.builder()
            .baby(babyDO)
            .father(father)
            .mother(mother)
            .build();
    }
}
