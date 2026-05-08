package com.huiyi.medical.dto;

public class PageQuery {
    private Integer page = 1;
    private Integer pageSize = 10;

    public int safePage() {
        return page == null || page < 1 ? 1 : page;
    }

    public int safePageSize() {
        if (pageSize == null || pageSize < 1) {
            return 10;
        }
        return Math.min(pageSize, 100);
    }

    public int offset() {
        return (safePage() - 1) * safePageSize();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

