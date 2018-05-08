package com.atu.controller;

import com.atu.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
@RestController
@RequestMapping("/api/name")
public class NameApiController {

    @Autowired
    private NameService nameService;

    @RequestMapping("/new")
    public String newName() {
        return nameService.generateName();
    }
    
}
