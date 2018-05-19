package com.atu.model;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * 五行
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/05
 */
public enum ChineseElement {

    METAL("金", 1),

    WOOD("木", 1 << 1),

    WATER("水", 1 << 2),

    FIRE("火", 1 << 3),

    EARTH("土", 1 << 4)
    ;

    private String name;
    private int tag;

    ChineseElement(String name, int tag) {
        this.name = name;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "\"" + this.name + "\"";
    }

    public static ChineseElement of(String element) {
        for (ChineseElement chineseElement : values()) {
            if (chineseElement.name.equals(element)) {
                return chineseElement;
            }
        }
        return null;
    }

    /**
     * 返回包含相应元素的位图。第0位到第4位，分别代表 金、木、水、火、土
     * e.g. 输入：火土   返回：00011 即 3
     * @param elementsStr
     * @return
     */
    public static int encodeElementTags(String elementsStr) {
        int tag = 0;

        for (int i = 0; i < elementsStr.length(); i++) {
            char c = elementsStr.charAt(i);
            ChineseElement chineseElement = ChineseElement.of(String.valueOf(c));
            if (null != chineseElement) {
                tag |= chineseElement.tag;
            }
        }
        return tag;
    }

    public static Set<ChineseElement> decodeTags(int tags) {
        Set<ChineseElement> elements = Sets.newHashSet();
        for (ChineseElement element : values()) {
            if ((tags & element.tag) == element.tag) {
                elements.add(element);
            }
        }
        return elements;
    }

    public static void main(String[] args) {
        System.out.println(ChineseElement.of("金"));
        System.out.println(ChineseElement.encodeElementTags("金金火土"));
        System.out.println(ChineseElement.decodeTags(25));
    }
}
