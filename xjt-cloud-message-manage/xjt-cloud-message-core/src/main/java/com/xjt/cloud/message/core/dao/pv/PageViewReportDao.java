package com.xjt.cloud.message.core.dao.pv;

import com.xjt.cloud.message.core.entity.PageView;
import org.springframework.stereotype.Repository;


/**
 * PageViewReportDao
 *
 * @author huanggc
 * @date 2020/10/23
 */
@Repository
public interface PageViewReportDao {

    /**
     * 保存 pageViewReport
     *
     * @param pageView PV
     * @author huanggc
     * @date 2020/11/03
     **/
    void saveReportByPageView(PageView pageView);
}
