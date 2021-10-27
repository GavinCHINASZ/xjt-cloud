package com.xjt.cloud.device.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/22 09:20
 * @Description: 二维码配置
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class QrNoConfig {
    private Long id;
    private Date createTime;//创建时间
    private Integer status;//状态1正常 99删除
    private Integer specification;//二维码规格 XX * XX
    private String modelName;//模板名字
    private Integer percentum;//二维码占图片的百分比
    private Integer modelType;//模板类型 1系统默认模板  2模式模板 3图标模板  4背景模板  5项目模板
    private Integer modelIndex;//模板索引

    private String qrNoBackImgUrl;//整体背景图

    private String title;//标题
    private Integer titleSize;//标题文字大小
    private String titleColor;//标题颜色
    private Integer titlePaddingTop;//标题上边距

    //生成二维码用
    private Integer type;//类型 1 巡检点码 2 设备码
    private String[] qrNos;//要生成二维码的数组
    private Integer num;//要生成二维码的个数
    private Long projectId;//项目id
    //生成二维码用
    private Integer colorInt;//二维码颜色（RGB）new Color(192,14,235).getRGB();
    private Integer colorBackInt;//二维码背景颜色 new Color(255, 255, 255).getRGB();
    private Integer width;//二维码图片宽度
    private Integer height;//二维码图片高度
    private Integer margin;//边框
    private String logoUrl;//logo图片路径
    private Integer logoWidth;//logo宽度
    private Integer logoHeight;//logo高度
    private Integer logoClipRadius;//logo图片圆角半径

    private Integer qrNoBgWidth;//二维码容器宽
    private Integer qrNoPaddingTop;//二维码上边距
    private Integer qrNoSize;//二维码大小

    private String backImgUrl;//背景图片路径

    private String bottomBackgroundColor;//底部区域背景色
    private Integer bottomHeight;//底部区域高度

    private String fontBackColor;//id文字背景色
    private String fontColor;//id文字颜色
    private Integer fontSize;//id文字大小

    private Integer phoneSize;//手机号文字大小
    private String phoneColor;//手机号文字颜色
    private Integer phonePaddingTop;//手机号码上边距
    private String phone;//手机号码

    private String companyName;//公司名称
    private String companyColor;//公司文字颜色
    private Integer companySize;//公司文字大小
    private Integer companyPaddingTop;//公司名称上边距

    private String address;//地址
    private String addressColor;//地址文字颜色
    private Integer addressSize;//地址文字大小
    private Integer addressPaddingTop;//地址名称上边距

    public Integer getSpecification() {
        return specification;
    }

    public void setSpecification(Integer specification) {
        this.specification = specification;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    public Integer getModelIndex() {
        return modelIndex;
    }

    public void setModelIndex(Integer modelIndex) {
        this.modelIndex = modelIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(Integer titleSize) {
        this.titleSize = titleSize;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public Integer getTitlePaddingTop() {
        return titlePaddingTop;
    }

    public void setTitlePaddingTop(Integer titlePaddingTop) {
        this.titlePaddingTop = titlePaddingTop;
    }

    public Integer getQrNoBgWidth() {
        return qrNoBgWidth;
    }

    public void setQrNoBgWidth(Integer qrNoBgWidth) {
        this.qrNoBgWidth = qrNoBgWidth;
    }

    public Integer getQrNoPaddingTop() {
        return qrNoPaddingTop;
    }

    public void setQrNoPaddingTop(Integer qrNoPaddingTop) {
        this.qrNoPaddingTop = qrNoPaddingTop;
    }

    public Integer getQrNoSize() {
        return qrNoSize;
    }

    public void setQrNoSize(Integer qrNoSize) {
        this.qrNoSize = qrNoSize;
    }

    public String getBottomBackgroundColor() {
        return bottomBackgroundColor;
    }

    public void setBottomBackgroundColor(String bottomBackgroundColor) {
        this.bottomBackgroundColor = bottomBackgroundColor;
    }

    public Integer getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(Integer bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getPhoneSize() {
        return phoneSize;
    }

    public void setPhoneSize(Integer phoneSize) {
        this.phoneSize = phoneSize;
    }

    public String getPhoneColor() {
        return phoneColor;
    }

    public void setPhoneColor(String phoneColor) {
        this.phoneColor = phoneColor;
    }

    public Integer getPhonePaddingTop() {
        return phonePaddingTop;
    }

    public void setPhonePaddingTop(Integer phonePaddingTop) {
        this.phonePaddingTop = phonePaddingTop;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyColor() {
        return companyColor;
    }

    public void setCompanyColor(String companyColor) {
        this.companyColor = companyColor;
    }

    public Integer getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public Integer getCompanyPaddingTop() {
        return companyPaddingTop;
    }

    public void setCompanyPaddingTop(Integer companyPaddingTop) {
        this.companyPaddingTop = companyPaddingTop;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressColor() {
        return addressColor;
    }

    public void setAddressColor(String addressColor) {
        this.addressColor = addressColor;
    }

    public Integer getAddressSize() {
        return addressSize;
    }

    public void setAddressSize(Integer addressSize) {
        this.addressSize = addressSize;
    }

    public Integer getAddressPaddingTop() {
        return addressPaddingTop;
    }

    public void setAddressPaddingTop(Integer addressPaddingTop) {
        this.addressPaddingTop = addressPaddingTop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackImgUrl() {
        return backImgUrl;
    }

    public void setBackImgUrl(String backImgUrl) {
        this.backImgUrl = backImgUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(Integer logoWidth) {
        this.logoWidth = logoWidth;
    }

    public Integer getLogoHeight() {
        return logoHeight;
    }

    public void setLogoHeight(Integer logoHeight) {
        this.logoHeight = logoHeight;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public Integer getColorInt() {
        return colorInt;
    }

    public void setColorInt(Integer colorInt) {
        this.colorInt = colorInt;
    }

    public Integer getColorBackInt() {
        return colorBackInt;
    }

    public void setColorBackInt(Integer colorBackInt) {
        this.colorBackInt = colorBackInt;
    }

    public Integer getLogoClipRadius() {
        return logoClipRadius;
    }

    public void setLogoClipRadius(Integer logoClipRadius) {
        this.logoClipRadius = logoClipRadius;
    }

    public String[] getQrNos() {
        return qrNos;
    }

    public void setQrNos(String[] qrNos) {
        this.qrNos = qrNos;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFontBackColor() {
        return fontBackColor;
    }

    public void setFontBackColor(String fontBackColor) {
        this.fontBackColor = fontBackColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getQrNoBackImgUrl() {
        return qrNoBackImgUrl;
    }

    public void setQrNoBackImgUrl(String qrNoBackImgUrl) {
        this.qrNoBackImgUrl = qrNoBackImgUrl;
    }

    public Integer getPercentum() {
        return percentum;
    }

    public void setPercentum(Integer percentum) {
        this.percentum = percentum;
    }
}
