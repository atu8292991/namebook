package com.atu.manager;

import java.util.List;

import com.atu.dao.NameRepoResgainDao;
import com.atu.dao.model.NameRepoResgainQueryDO;
import com.atu.dao.model.NameRepoResgainQueryDO.OrderBy;
import com.atu.model.NameRepoResgainDO;
import com.atu.model.dto.BatchNameTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/10
 */
@Component
public class NameManager {

    public static final int BATCH_TASK_SIZE = 90;

    @Autowired
    private NameRepoResgainDao nameRepoResgainDao;

    public List<NameRepoResgainDO> batchQueryByRand(BatchNameTaskDTO batchNameTaskDTO) {
        NameRepoResgainQueryDO queryDO = NameRepoResgainQueryDO.builder()
            .pageSize(BATCH_TASK_SIZE)
            .familyName(batchNameTaskDTO.getFamilyName())
            .gender(batchNameTaskDTO.getGender())
            .orderBy(OrderBy.RAND)
            .build();
        return nameRepoResgainDao.queryByCondition(queryDO);
    }
}
