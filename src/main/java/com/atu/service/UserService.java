package com.atu.service;

import com.alibaba.fastjson.JSON;

import com.atu.model.WxAppUserDO;
import com.atu.model.WxUserLoginResult;
import com.atu.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Service
public class UserService {

    @Value("wx.login.url")
    private String wxLoginUrl;

    @Value("wx.app.id")
    private String wxAppId;

    @Value("wx.app.secret")
    private String wxAppSecret;

    public String login(String jsCode) {
        StringBuilder stringBuilder = new StringBuilder(wxLoginUrl);
        stringBuilder.append("?appid=")
            .append(wxAppId)
            .append("&secret=")
            .append(wxAppSecret)
            .append("&js_code=")
            .append(jsCode)
            .append("&grant_type=authorization_code");
        String requestUrl = stringBuilder.toString();

        String response = HttpClientUtil.executeGet(requestUrl);

        WxUserLoginResult result;
        if (response.contains("openid")) {
            WxAppUserDO wxAppUserDO = JSON.parseObject(response, WxAppUserDO.class);
            result = WxUserLoginResult.builder()
                .success(true)
                .openId(wxAppUserDO.getOpenId())
                .newUser(true)
                .build();
        } else {
            result = WxUserLoginResult.builder()
                .success(false)
                .build();
        }
        return JSON.toJSONString(result);
    }
}
