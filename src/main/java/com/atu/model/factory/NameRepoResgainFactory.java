package com.atu.model.factory;

import com.atu.model.ChineseFamilyNameDO;
import com.atu.model.Gender;
import com.atu.model.NameRepoResgainDO;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/07
 */
public class NameRepoResgainFactory {

    private static NameRepoResgainFactory instance = new NameRepoResgainFactory();

    private NameRepoResgainFactory() {}

    public static NameRepoResgainFactory getInstance() {
        return instance;
    }

    public NameRepoResgainDO of(String name, ChineseFamilyNameDO familyNameDO, Gender gender) {
        return NameRepoResgainDO.builder()
            .familyName(familyNameDO.getFamilyName())
            .familyNameId(familyNameDO.getId())
            .givenName(name.replaceFirst(familyNameDO.getFamilyName(), ""))
            .givenNameLength(name.length() - familyNameDO.getFamilyName().length())
            .detailUrl(familyNameDO.getResgainUrl() + "/name/" + name + ".html")
            .gender(gender)
            .build();
    }

}
