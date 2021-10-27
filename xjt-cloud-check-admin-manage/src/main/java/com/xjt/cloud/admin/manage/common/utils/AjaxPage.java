package com.xjt.cloud.admin.manage.common.utils;


import org.springframework.stereotype.Repository;


@Repository
public class AjaxPage {

    /**
     * 页码
     */
    public int page;
    /**
     * 每页记录条数
     */
    public int rows = 30;


    public int pageIndex;
    /**
     * 每页记录条数
     */
    public int pageSize = 30;

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
        this.pageIndex = page;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
        this.pageSize = rows;
    }

    /**
     * @return the pageIndex
     */
    public int getPageIndex() {
        return pageIndex * this.pageSize;
    }

    /**
     * @param pageIndex the pageIndex to set
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex - 1 < 0 ? 0 : pageIndex - 1;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
