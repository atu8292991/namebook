package com.atu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 汉字
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/03
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChineseCharDO {
    private int id;

    /**
     * 汉字
     */
    private String chnChar;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 笔画数
     */
    private int strokeNumber;

    /**
     * 声调
     */
    private int levelTone;

    /**
     * 五行属性
     */
    private String element;

    @Override
    public String toString() {
        return chnChar
            + "(" + pinyin + ")"
            + "【" + element + "】"
            + " 笔划：" + strokeNumber + "\n";
    }

}
