package com.atu.boot;

import java.util.Set;

import com.atu.dao.ChnCharsNormal7000Dao;
import com.atu.dao.ChnFamilyNameDao;
import com.atu.dao.NameRepoResgainDao;
import com.atu.dao.model.ChnFamilyNameQueryDO;
import com.atu.model.ChineseCharDO;
import com.atu.model.ChineseFamilyNameDO;
import com.atu.model.factory.ChineseCharFactory;
import com.atu.model.factory.ChineseFamilyNameFactory;
import com.atu.repo.ResgainRepo;
import com.atu.util.ResourceReader;
import com.atu.util.ResourceReader.LineTextCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/03
 */
@Slf4j
@Component
public class ChineseCharsBootLoader implements InitializingBean {

    @Value("${chinese.normal700.char.bootloader.init}")
    private boolean needInitNormal7000;

    @Value("${chinese.familyname.bootloader.init}")
    private boolean needInitFamilyName;

    @Autowired               
    private ChnCharsNormal7000Dao chnCharsNormal7000Dao;

    @Autowired
    private ChnFamilyNameDao chnFamilyNameDao;

    @Autowired
    private ResgainRepo resgainRepo;

    @Autowired
    private NameRepoResgainDao nameRepoResgainDao;

    public ChineseCharsBootLoader() {}

    @Override
    public void afterPropertiesSet() throws Exception {
        if (needInitNormal7000) {
            ResourceReader.readFile(
                "chn/chn_chars_normal_7000",
                "utf-8",
                new LineTextCallback() {
                    @Override
                    public void solve(String lineText) {
                        if (lineText.startsWith("#")) {
                            ChineseCharFactory.setStroke(Integer.valueOf(lineText.substring(1, lineText.length())));
                        } else {
                            Set<ChineseCharDO> chineseChars = ChineseCharFactory.getInstance().of(lineText);
                            chineseChars.forEach(chnCharsNormal7000Dao::insertChnChar);
                        }
                    }
                });
        }

        if (needInitFamilyName) {
            ResourceReader.readFile(
                "chn/chn_family_name",
                "utf-8",
                new LineTextCallback() {
                    @Override
                    public void solve(String lineText) {
                        ChineseFamilyNameDO familyNameDO = ChineseFamilyNameFactory.getInstance().of(lineText);
                        chnFamilyNameDao.insert(familyNameDO);
                    }
                });
        }

        ChnFamilyNameQueryDO queryDO = ChnFamilyNameQueryDO.builder().build();

        int count = chnFamilyNameDao.queryCount(queryDO);

        int maxPageNo = (count % queryDO.getPageSize() == 0)
            ? count / queryDO.getPageSize()
            : count / queryDO.getPageSize() + 1;

        //do {
        //    List<ChineseFamilyNameDO> chineseFamilyNameDOS = chnFamilyNameDao.queryByCondition(queryDO);
        //    for (ChineseFamilyNameDO chineseFamilyNameDO : chineseFamilyNameDOS) {
        //        log.info("[" + chineseFamilyNameDO.getFamilyName() + "]: " + "start to fetch names.");
        //        Set<NameRepoResgainDO> nameRepoResgainDOS = resgainRepo.fetchNames(chineseFamilyNameDO);
        //        log.info("[" + chineseFamilyNameDO.getFamilyName() + "]: " + nameRepoResgainDOS.size() + " names fetched.");
        //        nameRepoResgainDOS.forEach(nameRepoResgainDao::insert);
        //    }
        //    queryDO.setPageNo(queryDO.getPageNo() + 1);
        //} while (queryDO.getPageNo() <= maxPageNo);

        //ChnCharQueryDO queryDO = ChnCharQueryDO.builder()
        //    .element("")
        //    .build();
        //int count = chnCharsNormal7000Dao.queryCount(queryDO);
        //
        //int maxPageNo = (count % queryDO.getPageSize() == 0)
        //    ? count / queryDO.getPageSize()
        //    : count / queryDO.getPageSize() + 1;
        //
        //do {
        //    List<ChineseCharDO> chineseCharDOS = chnCharsNormal7000Dao.queryByCondition(queryDO);
        //    chineseCharDOS.stream()
        //        .forEach(p -> {
        //                String element = FiveElementsRepo.getInstance().getElement(p.getChnChar());
        //                if (!StringUtils.isEmpty(element)) {
        //                    ChnCharUpdateDO updateDO = ChnCharUpdateDO.builder()
        //                        .id(p.getId())
        //                        .element(element)
        //                        .build();
        //
        //                    chnCharsNormal7000Dao.updateById(updateDO);
        //                }
        //            }
        //        );
        //    queryDO.setPageNo(queryDO.getPageNo() + 1);
        //} while (queryDO.getPageNo() <= maxPageNo);

    }

}
