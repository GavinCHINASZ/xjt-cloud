package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * @Auther: zhangzf
 * @Date: 2019/11/12
 * @Description: 故障等级 实体类
 */
public class FaultLevel extends BaseEntity {
    //等级2 level3至level2
    private Integer level2;
    //等级3 level4至level3
    private Integer level3;
    //等级4 level5至level4
    private Integer level4;
    //等级5 0至level5
    private Integer level5;
    //版本 1、新版本  2、旧版本
    private Integer newVersion;

    public FaultLevel() {
    }

    public FaultLevel(int level2, int level3, int level4, int level5, int newVersion, Long projectId) {
        this.level2 = level2;
        this.level3 = level3;
        this.level4 = level4;
        this.level5 = level5;
        this.newVersion = newVersion;
        this.setProjectId(projectId);
    }


    public Integer getLevel2() {
        return level2;
    }

    public void setLevel2(Integer level2) {
        this.level2 = level2;
    }

    public Integer getLevel3() {
        return level3;
    }

    public void setLevel3(Integer level3) {
        this.level3 = level3;
    }

    public Integer getLevel4() {
        return level4;
    }

    public void setLevel4(Integer level4) {
        this.level4 = level4;
    }

    public Integer getLevel5() {
        return level5;
    }

    public void setLevel5(Integer level5) {
        this.level5 = level5;
    }

    public Integer getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(Integer newVersion) {
        this.newVersion = newVersion;
    }
}
