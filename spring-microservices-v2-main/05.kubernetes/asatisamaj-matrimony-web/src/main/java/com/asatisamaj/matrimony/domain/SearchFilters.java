package com.asatisamaj.matrimony.domain;

import java.util.Date;

public class SearchFilters {
    private String memberId;
    private String samajArea;
    private String fullName;
    private String fatherName;
    private String motherName;
    private String gender;
    private String education;
    private String educationDetails;
    private String occupation;
    private Date birthDate;
    private String ageRange;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSamajArea() {
        return samajArea;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getGender() {
        return gender;
    }

    public String getEducation() {
        return education;
    }

    public String getEducationDetails() {
        return educationDetails;
    }

    public String getOccupation() {
        return occupation;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setSamajArea(String samajArea) {
        this.samajArea = samajArea;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setEducationDetails(String educationDetails) {
        this.educationDetails = educationDetails;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

}
