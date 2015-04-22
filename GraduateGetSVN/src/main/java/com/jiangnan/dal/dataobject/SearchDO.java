package com.jiangnan.dal.dataobject;

/**
 * Created by gaoshuai.gs on 2015/3/22.
 */
public class SearchDO {
    private String searchText;
    private Integer startRow;
    private Integer pageSize;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
