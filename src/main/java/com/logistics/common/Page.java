package com.logistics.common;

public class Page {

    private int pageNo = 0; // 页的序号

    private int pageSize = 10; // 页的大小
    
    private int totalCounts; // 页的大小

    private int totalPage;
    
    public int getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(int totalCounts) {
        this.totalCounts = totalCounts;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 获得页的大小
     * 
     * @return
     */
    public int getTotalPage() {
        // 如果是不分页,则总页数为1
        if (getPageSize() <= 0) {
            return 1;
        }

        int temp = totalCounts / getPageSize();

        if (totalCounts % getPageSize() > 0) {
            // 如果是有余数，则页数加一
            temp++;
        }

        return temp;
    }

}
