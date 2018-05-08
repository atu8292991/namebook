package com.atu.dao.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
@Data
@Builder
public class ChnCharUpdateDO {

    private int id;

    /**
     * 笔画数
     */
    private Integer strokeNumber;

    /**
     * 声调
     */
    private Integer levelTone;

    /**
     * 五行属性
     */
    private String element;
    
}
