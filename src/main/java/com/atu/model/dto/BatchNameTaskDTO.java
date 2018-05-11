package com.atu.model.dto;

import com.atu.model.Gender;
import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/10
 */
@Data
@Builder
public class BatchNameTaskDTO {
    private String familyName;
    private Gender gender;
}
