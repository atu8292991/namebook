package com.atu.model.dto;

import com.atu.model.BabyDO;
import com.atu.model.UserDO;
import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Data
@Builder
public class BabyDTO {
    private BabyDO baby;
    private UserDO father;
    private UserDO mother;

}
