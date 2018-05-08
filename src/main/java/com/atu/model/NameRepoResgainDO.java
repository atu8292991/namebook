package com.atu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 从resgain.net爬取的名字
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/07
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NameRepoResgainDO {
    private int id;
    private String familyName;
    private int familyNameId;
    private String givenName;
    private int givenNameLength;
    private Gender gender;
    private String detailUrl;

    public int getGender() {
        return gender.getCode();
    }

    public void setGender(int code) {
        this.gender = Gender.of(code);
    }
}
