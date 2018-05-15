package com.atu.model;

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
public class UserDO {
    private int id;
    private String nickName;
    private String country;
    private String province;
    private String city;
    private Gender gender;
    private String avatarUrl;
    private String language;
    private String openId;

    public int getGender() {
        return gender.getCode();
    }

    public void setGender(int code) {
        this.gender = Gender.of(code);
    }
}
