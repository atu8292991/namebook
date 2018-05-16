package com.atu.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    @RequestMapping("/register")
    public String register(@RequestParam("familyName") String familyName,
                           @RequestParam("gender") String gender,
                           @RequestParam("nick") String nick,
                           @RequestParam("birthTime") String birthTime,
                           @RequestParam("userId") int userId,
                           @RequestParam("relation") String relation) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        BabyDO babyDO = null;

        try {
            babyDO = BabyDO.builder()
                .familyName(familyName)
                .gender(Gender.of(gender))
                .nick(nick)
                .birthTime(simpleDateFormat.parse(birthTime))
                .build();

            if (relation.equals("爸爸")) {
                babyDO.setFatherId(userId);
            } else if (relation.equals("妈妈")) {
                babyDO.setMotherId(userId);
            } else {
                log.error("father and mother are both null.");
                return "fail";
            }
        } catch (ParseException e) {
            log.error("illegal date format. date=" + birthTime);
            return "fail";
        }
        return babyService.register(babyDO);
    }
    
}