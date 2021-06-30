package com.persistence;

public class Tour {
	private String name;
	private String classification;
	private String address;
	private String convenience;
	private String recreational;
	private String cultural;
	private int capacity;
	private int parkingAvailable;
	private String introduction;
	private String managementInstitutionPhone;
	private String managementInstitution;

	public Tour(String name, String classification, String address, String convenience, String recreational,
			String cultural, int capacity, int parkingAvailable, String introduction, String managementInstitutionPhone,
			String managementInstitution) {
		super();
		this.name = name;
		this.classification = classification;
		this.address = address;
		this.convenience = convenience;
		this.recreational = recreational;
		this.cultural = cultural;
		this.capacity = capacity;
		this.parkingAvailable = parkingAvailable;
		this.introduction = introduction;
		this.managementInstitutionPhone = managementInstitutionPhone;
		this.managementInstitution = managementInstitution;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classfication) {
		this.classification = classfication;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConvenience() {
		return convenience;
	}
	public void setConvenience(String convenience) {
		this.convenience = convenience;
	}
	public String getRecreational() {
		return recreational;
	}
	public void setRecreational(String recreational) {
		this.recreational = recreational;
	}
	public String getCultural() {
		return cultural;
	}
	public void setCultural(String cultural) {
		this.cultural = cultural;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getParkingAvailable() {
		return parkingAvailable;
	}
	public void setParkingAvailable(int parkingAvailable) {
		this.parkingAvailable = parkingAvailable;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getManagementInstitutionPhone() {
		return managementInstitutionPhone;
	}
	public void setManagementInstitutionPhone(String managementInstitutionPhone) {
		this.managementInstitutionPhone = managementInstitutionPhone;
	}
	public String getManagementInstitution() {
		return managementInstitution;
	}
	public void setManagementInstitution(String managementInstitution) {
		this.managementInstitution = managementInstitution;
	}
	
	
	
}
