package com.atu.controller;

import com.atu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@RequestParam("jsCode")String jsCode) {
        return userService.login(jsCode);
    }
}
