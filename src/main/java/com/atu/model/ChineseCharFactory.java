package com.atu.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.atu.repo.FiveElementsRepo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/03
 */
@Component
public class ChineseCharFactory {

    private ChineseCharFactory(){};

    private static ChineseCharFactory instance = new ChineseCharFactory();

    private static List<String> highLevelTone = Lists.newArrayList("ā", "ō", "ē", "ī", "ū", "ǖ");
    private static List<String> risingTone = Lists.newArrayList("á", "ó", "é", "í", "ú", "ǘ", "ń");
    private static List<String> fallingRisingTone = Lists.newArrayList("ǎ", "ǒ", "ě", "ǐ", "ǔ", "ǚ", "ň");
    private static List<String> fallingTone = Lists.newArrayList("à", "ò", "è", "ì", "ù", "ǜ", "\uE7C8");

    private static Map<Integer, List<String>> chnTones = Maps.newHashMap();

    static {
        chnTones.put(1, highLevelTone);
        chnTones.put(2, risingTone);
        chnTones.put(3, fallingRisingTone);
        chnTones.put(4, fallingTone);
    }

    @Setter
    private static int stroke = 1;

    public static ChineseCharFactory getInstance() {
        return instance;
    }

    /**
     * format text to {@link ChineseCharDO}
     *
     * @param input e.g. "chǎng,ān,hàn;厂"
     * @return
     */
    public Set<ChineseCharDO> of(String input) {
        if (StringUtils.isEmpty(input)) {
            return Collections.emptySet();
        }
        Set<ChineseCharDO> chnChars = Sets.newHashSet();
        String[] inputArray = input.split(";");
        String pinyins = inputArray[0];
        String chnChar = inputArray[1];
        String[] titles = pinyins.split(",");

        String element = FiveElementsRepo.getInstance().getElement(chnChar);

        Arrays.stream(titles).forEach(pinyin ->
            chnChars.add(
                ChineseCharDO.builder()
                    .chnChar(chnChar)
                    .pinyin(pinyin)
                    .strokeNumber(stroke)
                    .levelTone(parseLevelTone(pinyin))
                    .element(element)
                    .build()));

        return chnChars;
    }

    private int parseLevelTone(String pinyin) {
        for (Map.Entry<Integer, List<String>> chnToneCharsEntry: chnTones.entrySet()) {
            Integer levelTone = chnToneCharsEntry.getKey();
            List<String> chnLevelToneChars = chnToneCharsEntry.getValue();
            for (String chnLevelToneChar : chnLevelToneChars) {
                if (pinyin.contains(chnLevelToneChar)) {
                    return levelTone;
                }
            }
        }
        return 0;
    }

}
