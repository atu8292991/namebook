package com.atu.service;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.atu.dao.BabyDao;
import com.atu.dao.UserDao;
import com.atu.model.BabyDO;
import com.atu.model.UserDO;
import com.atu.model.dto.BabyDTO;
import com.google.common.collect.Lists;
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

    public String register(BabyDO babyDO) {
        babyDao.insert(babyDO);
        return "ok";
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
