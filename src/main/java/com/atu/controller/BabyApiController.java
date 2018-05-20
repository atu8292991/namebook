package com.atu.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.atu.model.BabyDO;
import com.atu.model.BabyRelationDO;
import com.atu.model.Gender;
import com.atu.model.Kinship;
import com.atu.service.BabyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/mybabies/{userId}")
    public String myBabies(@PathVariable("userId") int userId) {
        return babyService.queryBabyByUserId(userId);
    }

    @RequestMapping("/query/{babyId}")
    public String query(@PathVariable("babyId") int babyId) {
        return babyService.queryBabyById(babyId);
    }

    @RequestMapping("/relation/query/{babyId}")
    public String relationQuery(@PathVariable("babyId") int babyId,
                                @RequestParam("userId") int userId) {
        return babyService.queryRelation(babyId, userId);
    }

    @RequestMapping("/bind/{babyId}")
    public String bind(@PathVariable("babyId") int babyId,
                       @RequestParam("userId") int userId,
                       @RequestParam("relation") String relation) {
        return babyService.bind(BabyRelationDO.builder()
            .babyId(babyId)
            .userId(userId)
            .relation(Kinship.of(relation))
            .build());
    }
    
}
