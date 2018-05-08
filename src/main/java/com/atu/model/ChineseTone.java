package com.atu.model;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
public enum ChineseTone {

    NEUTRAL_TONE(0),

    HIGH_LEVEL_TONE(1),

    RISING_TONE(2),

    FALLING_RISING_TONE(3),

    FALLING_TONE(4)
    ;

    private int code;

    ChineseTone(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "" + code;
    }
}
