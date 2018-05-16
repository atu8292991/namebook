package com.atu.service;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.atu.dao.BabyDao;
import com.atu.model.BabyDO;
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

    public String register(BabyDO babyDO) {
        babyDao.insert(babyDO);
        return "ok";
    }

    public String queryBabyByParentId(int userId) {
        List<BabyDO> babyDOS = babyDao.queryByParentId(userId);
        return JSON.toJSONString(babyDOS);
    }

}
