package com.asatisamaj.matrimony.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MembersDetailDTO{

	private Long id;
	private Long memberId;
	private String samajArea;
	@NotNull
	@Size(min=2, max=30)
	private String fullName;
	private String fatherName;
	private String motherName;
	private String grandFather;
	private String gender;
	private Date birthDate;
	private String height;
	private String manglik;
	private String education;
	private String educationDetails;
	private String boardUniversity;
	private String occupation;
	private String occupationDetails;
	private String fullAddress;
	private String cityState;
	private String country;
	private String state;
	private String mobile1;
	private String mobile2;
	private String email;
	private String fatherOccupation;
	private String gotra;
	private String comments;
	private String imagePath;
	private String status;
	private Date insertDate;
	private String insertUser;
	private String insertProgram;
	private Date updateDate;
	private String updateUser;
	private String updateProgram;
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getSamajArea() {
		return samajArea;
	}
	public void setSamajArea(String samajArea) {
		this.samajArea = samajArea;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getGrandFather() {
		return grandFather;
	}
	public void setGrandFather(String grandFather) {
		this.grandFather = grandFather;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getManglik() {
		return manglik;
	}
	public void setManglik(String manglik) {
		this.manglik = manglik;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getEducationDetails() {
		return educationDetails;
	}
	public void setEducationDetails(String educationDetails) {
		this.educationDetails = educationDetails;
	}
	public String getBoardUniversity() {
		return boardUniversity;
	}
	public void setBoardUniversity(String boardUniversity) {
		this.boardUniversity = boardUniversity;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getOccupationDetails() {
		return occupationDetails;
	}
	public void setOccupationDetails(String occupationDetails) {
		this.occupationDetails = occupationDetails;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getCityState() {
		return cityState;
	}
	public void setCityState(String cityState) {
		this.cityState = cityState;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFatherOccupation() {
		return fatherOccupation;
	}
	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}
	public String getGotra() {
		return gotra;
	}
	public void setGotra(String gotra) {
		this.gotra = gotra;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getInsertUser() {
		return insertUser;
	}
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	public String getInsertProgram() {
		return insertProgram;
	}
	public void setInsertProgram(String insertProgram) {
		this.insertProgram = insertProgram;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateProgram() {
		return updateProgram;
	}
	public void setUpdateProgram(String updateProgram) {
		this.updateProgram = updateProgram;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
