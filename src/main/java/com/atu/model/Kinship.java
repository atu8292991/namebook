package com.atu.model;

import lombok.Getter;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/20
 */
public enum Kinship {
    FATHER(1, "父亲"),
    MOTHER(2, "母亲"),
    GRANDPA_FATHER_SIDE(3, "爷爷"),
    GRANDMA_FATHER_SIDE(4, "奶奶"),
    GRANDPA_MOTHER_SIDE(5, "外公"),
    GRANDMO_MOTHER_SIDE(6, "外婆"),
    UNCLE_FATHER_SIDE(7, "叔伯"),
    UNCLE_WIFE_FATHER_SIDE(8, "婶婶"),
    UNCLE_MOTHER_SIDE(8, "舅舅"),
    UNCLE_WIFE_MOTHER_SIDE(9, "舅妈"),
    AUNT_FATHER_SISTER(10, "姑姑"),
    AUNT_HUSBAND_FATHER_SIDE(11, "姑父"),
    AUNT_MOTHER_SISTER(12, "姨"),
    AUNT_HUSBAND_MOTHER_SIDE(13, "姨夫"),
    BROTHERS_AND_SISTERS(14, "兄弟姐妹"),
    ;

    @Getter
    private int code;
    
    @Getter
    private String desc;
    Kinship(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Kinship of(int code) {
        for (Kinship kinship : values()) {
            if (kinship.code == code) {
                return kinship;
            }
        }
        return null;
    }

    public static Kinship of(String desc) {
        for (Kinship kinship : values()) {
            if (kinship.desc.equals(desc)) {
                return kinship;
            }
        }
        return null;
    }

}
