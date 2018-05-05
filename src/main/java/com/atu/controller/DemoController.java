package com.atu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/03
 */
@RestController
public class DemoController {
    @RequestMapping("/")
    public String index() {
        return "hello world!";
    }
}
