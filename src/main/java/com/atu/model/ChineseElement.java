package com.atu.model;

/**
 * 五行
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
public enum ChineseElement {

    METAL("金"),

    WOOD("木"),

    WATER("水"),

    FIRE("火"),

    EARTH("土")
    ;

    private String name;

    ChineseElement(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\"" + this.name + "\"";
    }
}
