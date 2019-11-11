package com.wzy.arms.network.model;

public class PageInfo {
    //当前页
    private int page = 1;
    //加载数量
    private int pageNumber = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
