package com.atu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/19
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NameRepoDO {

    public static final int FROM_USER = 1;
    public static final int FROM_AI = 1 << 1;
    public static final int FROM_SITE_RESGAIN = 1 << 2;

    private int id;
    private String familyName;
    private String givenName;
    private int elements;
    private int nameFrom;
    private String nameDesc;
    private int genderPct;
    private String pinyinFn;
    private String pinyinGn;
    private int outId;
}
