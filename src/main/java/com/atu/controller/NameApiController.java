package com.atu.controller;

import com.atu.model.Gender;
import com.atu.model.dto.BatchNameTaskDTO;
import com.atu.service.NameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
@Slf4j
@RestController
@RequestMapping("/api/name")
public class NameApiController {

    @Autowired
    private NameService nameService;

    @RequestMapping("/new")
    public String newName() {
        return nameService.generateName();
    }

    @RequestMapping("/batch")
    public String batch() {
        log.info("NameApiController batch query");
        BatchNameTaskDTO batchNameTaskDTO = BatchNameTaskDTO.builder()
            .gender(Gender.of(2))
            .build();
        return nameService.generateNameTask(batchNameTaskDTO);
    }
    
}
