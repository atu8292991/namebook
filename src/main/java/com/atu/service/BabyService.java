package com.atu.service;

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
}
