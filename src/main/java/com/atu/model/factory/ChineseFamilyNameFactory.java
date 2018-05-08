package com.atu.model.factory;

import com.atu.model.ChineseFamilyNameDO;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/07
 */
public class ChineseFamilyNameFactory {

    private ChineseFamilyNameFactory() {}

    private static ChineseFamilyNameFactory instance = new ChineseFamilyNameFactory();

    public static ChineseFamilyNameFactory getInstance() {
        return instance;
    }

    public ChineseFamilyNameDO of(String input) {
        String[] values = input.split(",");
        ChineseFamilyNameDO familyNameDO = ChineseFamilyNameDO.builder()
            .familyName(values[0])
            .build();
        if (values.length == 2) {
            familyNameDO.setResgainUrl(values[1]);
        }
        return familyNameDO;
    }

}
