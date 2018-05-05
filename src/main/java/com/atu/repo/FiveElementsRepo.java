package com.atu.repo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import com.atu.util.HttpClientUtil;
import com.atu.util.ResourceReader;
import com.atu.util.ResourceReader.LineTextCallback;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 从网站查询汉字的五行属性
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/04
 */
@Service
@Slf4j
public class FiveElementsRepo {

    private FiveElementsRepo(){};

    private static FiveElementsRepo instance = new FiveElementsRepo();

    public static FiveElementsRepo getInstance() {
        return instance;
    }

    public static final String REQUEST_URL = "http://www.bm8.com.cn/wuxing/";

    private String getRequestUrl(String chnChar) {
        if (StringUtils.isEmpty(chnChar)) {
            return "";
        }
        try {
            String gb2312 = URLEncoder.encode(chnChar, "gb2312");
            return REQUEST_URL + gb2312 + ".htm";
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException for URLEncoder#encode. e=", e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取汉字的五行属性
     * @param chnChar 输入的汉字
     * @return 对应的五行属性
     */
    public String getElement(String chnChar) {
        String response = HttpClientUtil.executeGet(getRequestUrl(chnChar));
        return parseElement(response);
    }

    private String parseElement(String responseHtml) {
        String feature = "<strong>五行属性</strong>";
        Set<String> elements = Sets.newHashSet("金", "木", "水", "火", "土");

        String[] parsedElement = new String[1];
        parsedElement[0] = "";

        try {
            ResourceReader.readString(responseHtml, new LineTextCallback() {
                @Override
                public void solve(String lineText) {
                    if (lineText.contains(feature)) {
                        for (String element : elements) {
                            if (lineText.contains(element)) {
                                System.out.println(element);
                                parsedElement[0] = element;
                                break;
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedElement[0];
    }

}
