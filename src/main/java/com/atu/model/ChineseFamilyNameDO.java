package com.atu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 姓氏
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/07
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChineseFamilyNameDO {
    private int id;
    private String familyName;
    private int charId;
    private String resgainUrl;
}
