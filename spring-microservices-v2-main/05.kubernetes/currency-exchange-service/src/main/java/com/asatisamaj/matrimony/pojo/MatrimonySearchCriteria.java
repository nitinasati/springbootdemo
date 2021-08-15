package com.asatisamaj.matrimony.pojo;

public class MatrimonySearchCriteria {

    private Long memberId;
    private String samajArea;
    private String gender;
    private String ageRange;
    private String education;
    private String occupation;
    private int page;
    private int size;

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

    public Long getMemberId() {
        return memberId;
    }

    public String getSamajArea() {
        return samajArea;
    }

    public String getGender() {
        return gender;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public String getEducation() {
        return education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setSamajArea(String samajArea) {
        this.samajArea = samajArea;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

}
