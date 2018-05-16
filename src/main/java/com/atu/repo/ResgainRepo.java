package com.atu.repo;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.atu.model.ChineseFamilyNameDO;
import com.atu.model.Gender;
import com.atu.model.NameRepoResgainDO;
import com.atu.model.factory.NameRepoResgainFactory;
import com.atu.util.HttpClientUtil;
import com.atu.util.ResourceReader;
import com.atu.util.ResourceReader.LineTextCallback;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/07
 */
@Service
public class ResgainRepo {

    public static final String NAME_SUFFIX = "/name/";
    public static final String HTML_SUFFIX = ".html";
    public static final String BOYS_PREFIX = "boys";
    public static final String GIRLSS_PREFIX = "girls";

    public static final String CHINESE_CHAR_REGX = "[\\u4E00-\\u9FA5]";

    /**
     * 爬取的最大页数
     */
    public static final int MAX_FETCH_PAGE_NO = 10;

    /**
     * 获取指定姓氏指定性别的第n页名字。e.g. http://zhao.resgain.net/name/girls_2.html
     * @param url       指定姓氏的域名
     * @param gender    性别 boy/girl
     * @param pageNo    页数
     * @return          名字列表的url
     */
    private String getRequestUrl(String url, Gender gender, int pageNo) {
        return url + NAME_SUFFIX
            + (gender.isBoy() ? BOYS_PREFIX : GIRLSS_PREFIX)
            + (pageNo > 1 ? "_" + pageNo : "")
            + HTML_SUFFIX;
    }

    /**
     * 爬取指定姓氏的所有名字
     * @param familyNameDO  姓氏
     * @return              所有的名字
     */
    public Set<NameRepoResgainDO> fetchNames(ChineseFamilyNameDO familyNameDO) {
        Set<NameRepoResgainDO> nameRepoResgainDOs = Sets.newHashSetWithExpectedSize(512);

        for (int pageNo = 0; pageNo <= MAX_FETCH_PAGE_NO; pageNo++) {
            String response = HttpClientUtil.executeGet(getRequestUrl(familyNameDO.getResgainUrl(), Gender.MALE, pageNo));
             nameRepoResgainDOs.addAll(parseNames(response, familyNameDO, Gender.MALE));

            response = HttpClientUtil.executeGet(getRequestUrl(familyNameDO.getResgainUrl(), Gender.FEMALE, pageNo));
            nameRepoResgainDOs.addAll(parseNames(response, familyNameDO, Gender.FEMALE));
        }

        return nameRepoResgainDOs;
    }

    private Set<NameRepoResgainDO> parseNames(String responseHtml, ChineseFamilyNameDO familyNameDO, Gender gender) {
        String familyName = familyNameDO.getFamilyName();
        final String nameRegx = familyName + CHINESE_CHAR_REGX + "+";
        // 返回html中对应名字信息的特征
        final String featureRegx = "\\s*"
            + "<a href=\"/name/"
            + nameRegx
            + ".html\" class=\"btn btn-link\" target=\"_blank\">"
            + nameRegx
            + "</a>";
        Pattern htmlLineContainsNamePattern = Pattern.compile(featureRegx);

        Set<String> names = Sets.newHashSetWithExpectedSize(300);

        try {
            ResourceReader.readString(responseHtml, new LineTextCallback() {
                @Override
                public void solve(String lineText) {
                     if (htmlLineContainsNamePattern.matcher(lineText).matches()) {
                        Pattern namePattern = Pattern.compile(nameRegx);
                         Matcher matcher = namePattern.matcher(lineText);
                         if (matcher.find()) {
                             names.add(matcher.group());
                         }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<NameRepoResgainDO> nameRepoResgainDOs = Sets.newHashSetWithExpectedSize(names.size());
        for (String name : names) {
            NameRepoResgainDO nameRepoResgainDO = NameRepoResgainFactory.getInstance().of(name, familyNameDO, gender);
            nameRepoResgainDOs.add(nameRepoResgainDO);
        }
        
        return nameRepoResgainDOs;
    }

}
