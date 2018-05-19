package com.atu.repo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.atu.dao.ChnCharsNormal7000Dao;
import com.atu.dao.model.ChnCharQueryDO;
import com.atu.model.ChineseCharDO;
import com.atu.model.ChineseElement;
import com.atu.model.ChineseFamilyNameDO;
import com.atu.model.Gender;
import com.atu.model.NameRepoDO;
import com.atu.model.NameRepoResgainDO;
import com.atu.model.factory.NameRepoResgainFactory;
import com.atu.util.HttpClientUtil;
import com.atu.util.ResourceReader;
import com.atu.util.ResourceReader.LineTextCallback;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ChnCharsNormal7000Dao chnCharsNormal7000Dao;

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

    public NameRepoDO fetchNameDetail(NameRepoResgainDO nameRepoResgainDO) {
        String response = HttpClientUtil.executeGet(nameRepoResgainDO.getDetailUrl());
        NameRepoDO nameRepoDO = NameRepoDO.builder()
            .familyName(nameRepoResgainDO.getFamilyName())
            .givenName(nameRepoResgainDO.getGivenName())
            .nameFrom(NameRepoDO.FROM_SITE_RESGAIN)
            .outId(nameRepoResgainDO.getId())
            .build();
        fillNameDetail(response, nameRepoDO);
        fillNamePinyin(nameRepoDO);
        return nameRepoDO;
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

    private void fillNameDetail(String responseHtml, NameRepoDO nameRepoDO) {

        Map<String, Boolean> matchTagMap = Maps.newHashMap();
        matchTagMap.put("elements", false);
        matchTagMap.put("desc", false);

        String elementsFeatureStr = "名字五行";
        String elementsRegx = "[金|木|水|火|土]+";

        String genderPctFeatureStr = "适用于男孩名";

        String descFeatureStr = "姓名\"" + nameRepoDO.getFamilyName() + nameRepoDO.getGivenName() + "\"总解";

        try {
            ResourceReader.readString(responseHtml, new LineTextCallback() {
                @Override
                public void solve(String lineText) {
                    if (matchTagMap.get("elements")) {
                        Pattern elementsPattern = Pattern.compile(elementsRegx);
                        Matcher matcher = elementsPattern.matcher(lineText);
                        if (matcher.find()) {
                            String elements = matcher.group();
                            nameRepoDO.setElements(ChineseElement.encodeElementTags(elements));
                        }
                        matchTagMap.put("elements", false);
                    }

                    if (lineText.contains(elementsFeatureStr)) {
                        matchTagMap.put("elements", true);
                    }

                    if (lineText.contains(genderPctFeatureStr)) {
                        String pctRegx = "\\d+.\\d+";
                        Pattern pctPattern = Pattern.compile(pctRegx);
                        Matcher matcher = pctPattern.matcher(lineText);
                        if (matcher.find()) {
                            String pct = matcher.group();
                            Double pctD = Double.valueOf(pct);
                            nameRepoDO.setGenderPct((int)(pctD * 100));
                        }
                    }

                    if (matchTagMap.get("desc")) {
                        if (lineText.contains("strong")) {
                            nameRepoDO.setNameDesc(lineText.replaceAll("</*strong>", "").trim());
                            matchTagMap.put("desc", false);
                        }
                    }

                    if (lineText.contains(descFeatureStr)) {
                        matchTagMap.put("desc", true);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillNamePinyin(NameRepoDO nameRepoDO) {
        nameRepoDO.setPinyinFn(getPinyinFromString(nameRepoDO.getFamilyName()));
        nameRepoDO.setPinyinGn(getPinyinFromString(nameRepoDO.getGivenName()));
    }

    private String getPinyinFromString(String str) {
        StringBuilder pinyinStringBuilder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            String c = String.valueOf(str.charAt(i));
            List<ChineseCharDO> chineseCharDOS = chnCharsNormal7000Dao.queryByCondition(ChnCharQueryDO.builder()
                .chnChar(c)
                .build());
            if (pinyinStringBuilder.length() > 0 ) {
                pinyinStringBuilder.append(" ");
            }
            if (CollectionUtils.isNotEmpty(chineseCharDOS)) {
                pinyinStringBuilder.append(chineseCharDOS.get(0).getPinyin());
            } else {
                pinyinStringBuilder.append("?");
            }
        }
        return pinyinStringBuilder.toString();
    }

}
