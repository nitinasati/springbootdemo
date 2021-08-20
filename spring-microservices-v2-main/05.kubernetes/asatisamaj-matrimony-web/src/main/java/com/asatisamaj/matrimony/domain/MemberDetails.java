package com.asatisamaj.matrimony.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asatisamaj.matrimony.MatrimonyHomePage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDetails {

    private static final Logger LOGGER = LogManager.getLogger(MatrimonyHomePage.class);

    private Long id;
    private Long memberId;
    private String samajArea;
    private String fullName;
    private String fatherName;
    private String motherName;
    private String grandFather;
    private String gender;
    private int age;

    private String birthDate;
    private String ageRange;
    private String height;
    private String weight;
    private String complexion;
    private Boolean manglik;
    private String education;
    private String educationDetails;
    private String boardUniversity;
    private String occupation;
    private String occupationDetails;
    private String fullAddress;
    private String cityState;
    private String mobile1;
    private String mobile2;
    private String email;
    private String fatherOccupation;
    private String brothers;
    private String marriedBrothers;
    private String sisters;
    private String marriedSisters;
    private String vansh;
    private String gotra;
    private String requirement;
    private String imagePath;

    private String status;

    private Date insertDate;

    private String insertUser;

    private String insertProgram;
    private Date updateDate;
    private String updateUser;
    private String updateProgram;

    public MemberDetails() {

    }

    public Long getId() {
        return id;
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

    public String getGrandFather() {
        return grandFather;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {

        Date date = null;
        try {
            if (null != birthDate) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+ss:ss", Locale.ENGLISH); // Existing
                date = simpleDateFormat.parse(birthDate); // Returns Date Format,
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yyyy"); // New Pattern
                birthDate = simpleDateFormat1.format(date);
            }
        } catch (ParseException e) {
            
        }

        LOGGER.info("gettting Birth Date {}", birthDate);
        return birthDate;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getComplexion() {
        return complexion;
    }

    public Boolean getManglik() {
        return manglik;
    }

    public String getEducation() {
        return education;
    }

    public String getBoardUniversity() {
        return boardUniversity;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getOccupationDetails() {
        return occupationDetails;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getCityState() {
        return cityState;
    }

    public String getMobile1() {
        return mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public String getEmail() {
        return email;
    }

    public String getFatherOccupation() {
        return fatherOccupation;
    }

    public String getBrothers() {
        return brothers;
    }

    public String getMarriedBrothers() {
        return marriedBrothers;
    }

    public String getSisters() {
        return sisters;
    }

    public String getMarriedSisters() {
        return marriedSisters;
    }

    public String getVansh() {
        return vansh;
    }

    public String getGotra() {
        return gotra;
    }

    public String getRequirement() {
        return requirement;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getStatus() {
        return status;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public String getInsertProgram() {
        return insertProgram;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public String getUpdateProgram() {
        return updateProgram;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setGrandFather(String grandFather) {
        this.grandFather = grandFather;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(String birthDate) {
        LOGGER.info("Setting Birth Date {}", birthDate);
        this.birthDate = birthDate;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setComplexion(String complexion) {
        this.complexion = complexion;
    }

    public void setManglik(Boolean manglik) {
        this.manglik = manglik;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setBoardUniversity(String boardUniversity) {
        this.boardUniversity = boardUniversity;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setOccupationDetails(String occupationDetails) {
        this.occupationDetails = occupationDetails;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public void setCityState(String cityState) {
        this.cityState = cityState;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFatherOccupation(String fatherOccupation) {
        this.fatherOccupation = fatherOccupation;
    }

    public void setBrothers(String brothers) {
        this.brothers = brothers;
    }

    public void setMarriedBrothers(String marriedBrothers) {
        this.marriedBrothers = marriedBrothers;
    }

    public void setSisters(String sisters) {
        this.sisters = sisters;
    }

    public void setMarriedSisters(String marriedSisters) {
        this.marriedSisters = marriedSisters;
    }

    public void setVansh(String vansh) {
        this.vansh = vansh;
    }

    public void setGotra(String gotra) {
        this.gotra = gotra;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public void setInsertProgram(String insertProgram) {
        this.insertProgram = insertProgram;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public void setUpdateProgram(String updateProgram) {
        this.updateProgram = updateProgram;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getEducationDetails() {
        return educationDetails;
    }

    public void setEducationDetails(String educationDetails) {
        this.educationDetails = educationDetails;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
