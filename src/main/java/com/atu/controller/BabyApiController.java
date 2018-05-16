package com.atu.controller;

import java.util.Date;

import com.atu.model.BabyDO;
import com.atu.model.Gender;
import com.atu.service.BabyService;
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
@RequestMapping("/api/baby")
public class BabyApiController {
    
    @Autowired
    private BabyService babyService;

    @RequestMapping("/registe")
    public String registe(@RequestParam("familyName") String familyName,
                          @RequestParam("gender") int gender,
                          @RequestParam("nick") String nick,
                          @RequestParam("birthTime") Date birthTime,
                          @RequestParam("fatherId") int fatherId,
                          @RequestParam("motherId") int motherId) {
        BabyDO babyDO = BabyDO.builder()
            .familyName(familyName)
            .gender(Gender.of(gender))
            .nick(nick)
            .birthTime(birthTime)
            .fatherId(fatherId)
            .motherId(motherId)
            .build();
        return babyService.registe(babyDO);
    }
    
}
