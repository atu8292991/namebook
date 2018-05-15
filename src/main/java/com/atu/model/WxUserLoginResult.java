package com.atu.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Data
@Builder
public class WxUserLoginResult {
    private boolean success;
    private String openId;
    private boolean newUser;
}
