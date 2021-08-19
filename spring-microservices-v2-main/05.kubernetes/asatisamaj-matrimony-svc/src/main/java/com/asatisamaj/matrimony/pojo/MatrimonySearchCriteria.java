package com.asatisamaj.matrimony.pojo;

import java.util.List;

public class MatrimonySearchCriteria {

    private int page;
    private int size;
    private String sortColumn;
    private String sortDirection;

    private List<SearchCriteria> searchCriteriaList;
    
    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public List<SearchCriteria> getSearchCriteriaList() {
        return searchCriteriaList;
    }

    public void setSearchCriteriaList(List<SearchCriteria> searchCriteriaList) {
        this.searchCriteriaList = searchCriteriaList;
    }

}
