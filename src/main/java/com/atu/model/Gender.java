package com.atu.model;

import lombok.Getter;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/08
 */
public enum Gender {

    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女")
    ;

    @Getter
    private int code;
    private String desc;

    Gender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Gender of(int code) {
        for (Gender gender : Gender.values()) {
            if (gender.equals(code)) {
                return gender;
            }
        }
        return Gender.UNKNOWN;
    }

    public static Gender of(String desc) {
        for (Gender gender : Gender.values()) {
            if (gender.desc.equals(desc)) {
                return gender;
            }
        }
        return Gender.UNKNOWN;
    }

    private boolean equals(int code) {
        return this.code == code;
    }

    public boolean isBoy() {
        return MALE.equals(this.code);
    }

    public boolean isGirl() {
        return FEMALE.equals(this.code);
    }

    @Override
    public String toString() {
        return "" + this.code;
    }
    
}
