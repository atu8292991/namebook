package com.atu.model;

import lombok.Getter;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/08
 */
public enum Gender {

    UNKNOWN(0),
    BOY(1),
    GIRL(2)
    ;

    @Getter
    private int code;
    Gender(int code) {
        this.code = code;
    }

    public static Gender of(int code) {
        for (Gender gender : Gender.values()) {
            if (gender.equals(code)) {
                return gender;
            }
        }
        return Gender.UNKNOWN;
    }

    private boolean equals(int code) {
        return this.code == code;
    }

    public boolean isBoy() {
        return BOY.equals(this.code);
    }

    public boolean isGirl() {
        return GIRL.equals(this.code);
    }
    
}
