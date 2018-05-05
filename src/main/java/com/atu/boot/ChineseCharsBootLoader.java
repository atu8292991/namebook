package com.atu.boot;

import com.atu.dao.ChnCharsNormal7000Dao;
import com.atu.model.ChineseCharDO;
import com.atu.repo.FiveElementsRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/03
 */
@Component
public class ChineseCharsBootLoader implements InitializingBean {

    @Autowired
    private ChnCharsNormal7000Dao chnCharsNormal7000Dao;

    @Override
    public void afterPropertiesSet() throws Exception {
        //ResourceReader.readFile(
        //    "chn/chn_chars_normal_7000",
        //    "utf-8",
        //    new LineTextCallback() {
        //        @Override
        //        public void solve(String lineText) {
        //            if (lineText.startsWith("#")) {
        //                ChineseCharFactory.setStroke(Integer.valueOf(lineText.substring(1, lineText.length())));
        //            } else {
        //                Set<ChineseCharDO> chineseChars = ChineseCharFactory.getInstance().of(lineText);
        //                chineseChars.forEach(chnCharsNormal7000Dao::insertChnChar);
        //            }
        //        }
        //    });
        ChineseCharDO chineseCharDO = chnCharsNormal7000Dao.queryChineseCharById(1);
        FiveElementsRepo.getInstance().getElement(chineseCharDO.getChnChar());
    }

}
