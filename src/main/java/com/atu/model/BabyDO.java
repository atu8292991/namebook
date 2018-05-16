package com.atu.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/16
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BabyDO {

    private int id;
    private String familyName;
    private Gender gender;
    private String nick;
    private Date birthTime;
    private int fatherId;
    private int motherId;

    public int getGender() {
        return gender.getCode();
    }

    public void setGender(int code) {
        this.gender = Gender.of(code);
    }
}
