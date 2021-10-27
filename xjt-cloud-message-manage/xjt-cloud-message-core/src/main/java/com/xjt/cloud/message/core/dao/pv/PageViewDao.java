package com.xjt.cloud.message.core.dao.pv;

import com.xjt.cloud.message.core.entity.PageView;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PageViewDao 页面浏览量或点击量
 *
 * @author huanggc
 * @date 2020/10/23
 */
@Repository
public interface PageViewDao {

    /**
     * 保存 PV
     *
     * @param pageView PV
     * @author huanggc
     * @date 2020/10/23
     **/
    void savePageView(PageView pageView);

    /**
     * 查询 PV list
     *
     * @param pageView PV
     * @author huanggc
     * @date 2020/10/23
     * @return List<PageView>
     **/
    List<PageView> findPageViewList(PageView pageView);
}
