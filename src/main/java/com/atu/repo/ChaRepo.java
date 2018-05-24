package com.atu.repo;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.atu.model.NameRepoDO;
import com.atu.util.HttpClientUtil;
import com.atu.util.ResourceReader;
import com.atu.util.ResourceReader.LineTextCallback;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/24
 */
public class ChaRepo {

    private ChaRepo(){}

    private static ChaRepo instance = new ChaRepo();

    public static ChaRepo getInstance() {
        return instance;
    }

    public static final String CHA_URL = "http://www.123cha.com/xm/";

    private String getRequestUrl(String name) {
        return CHA_URL + name + "-0";
    }

    public Integer fetchPoint(NameRepoDO name) {
        String response = HttpClientUtil.executeGet(getRequestUrl(name.getFamilyName() + name.getGivenName()));
        return parsePoint(response);
    }

    private Integer parsePoint(String responseHtml) {

        List<Integer> result = Lists.newArrayList();
        try {
            ResourceReader.readString(responseHtml, new LineTextCallback() {
                @Override
                public void solve(String lineText) {
                    if (lineText.contains("得分")) {
                        String pointRegx = "(\\d)+";
                        Pattern namePattern = Pattern.compile(pointRegx);
                        Matcher matcher = namePattern.matcher(lineText);
                        if (matcher.find()) {
                            Integer point = Integer.valueOf(matcher.group());
                            result.add(point);
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        } else {
            return 0;
        }
    }

}
