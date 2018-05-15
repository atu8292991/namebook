package com.atu.service;

import com.alibaba.fastjson.JSON;

import com.atu.dao.UserDao;
import com.atu.model.UserDO;
import com.atu.model.wx.WxAppUser;
import com.atu.model.wx.WxUserLoginResult;
import com.atu.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Service
public class UserService {

    @Value("${wx.login.url}")
    private String wxLoginUrl;

    @Value("${wx.app.id}")
    private String wxAppId;

    @Value("${wx.app.secret}")
    private String wxAppSecret;

    @Autowired
    private UserDao userDao;

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
            WxAppUser wxAppUser = JSON.parseObject(response, WxAppUser.class);
            UserDO userDO = userDao.queryByOpenId(wxAppUser.getOpenId());
            result = WxUserLoginResult.builder()
                .success(true)
                .openId(wxAppUser.getOpenId())
                .newUser(userDO == null)
                .user(userDO)
                .build();
        } else {
            result = WxUserLoginResult.builder()
                .success(false)
                .build();
        }
        return JSON.toJSONString(result);
    }

    public String registe(UserDO userDO) {
        userDao.insert(userDO);
        return "ok";
    }
}
