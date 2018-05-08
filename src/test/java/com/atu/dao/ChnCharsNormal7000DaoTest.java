package com.atu.dao;

import com.atu.model.ChineseCharDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChnCharsNormal7000DaoTest {

    @Autowired
    private ChnCharsNormal7000Dao chnCharsNormal7000Dao;

    @Test
    public void queryById() {
        ChineseCharDO chineseCharDO = chnCharsNormal7000Dao.queryById(1);
        Assert.assertTrue(chineseCharDO != null);
    }

}