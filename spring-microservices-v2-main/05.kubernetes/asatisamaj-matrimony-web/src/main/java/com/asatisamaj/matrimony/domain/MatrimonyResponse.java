package com.asatisamaj.matrimony.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatrimonyResponse implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private long totalItems;
    private int totalPages;
    private List<MemberDetails> memberDetails;
    private int currentPage;
    private String status;

    public long getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<MemberDetails> getMemberDetails() {
        return memberDetails;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public String getStatus() {
        return status;
    }

    public void setTotalItems(long l) {
        this.totalItems = l;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setMemberDetails(List<MemberDetails> memberDetails) {
        this.memberDetails = memberDetails;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MatrimonyResponse [totalItems=" + totalItems + ", totalPages=" + totalPages + ", memberDetails="
                + memberDetails + ", currentPage=" + currentPage + ", status=" + status + "]";
    }

}
