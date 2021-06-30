package DTO;

public class TourDTO 
{
	
	private String name;
	private String classification;
	private String address;
	private String convenience;
	private String recreational;
	private String cultural;
	private int capacity;
	private int parking_avaliable;
	private String introduction;
	private String management_institution;
	private String management_institution_phone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
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
	public int getParking_avaliable() {
		return parking_avaliable;
	}
	public void setParking_avaliable(int parking_avaliable) {
		this.parking_avaliable = parking_avaliable;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getManagement_institution() {
		return management_institution;
	}
	public void setManagement_institution(String management_institution) {
		this.management_institution = management_institution;
	}
	public String getManagement_institution_phone() {
		return management_institution_phone;
	}
	public void setManagement_institution_phone(String management_institution_phone) {
		this.management_institution_phone = management_institution_phone;
	}
	
	@Override
	public String toString() {
		return "TourDTO [name=" + name + ", classification=" + classification + ", address=" + address
				+ ", convenience=" + convenience + ", recreational=" + recreational + ", cultural=" + cultural
				+ ", capacity=" + capacity + ", parking_avaliable=" + parking_avaliable + ", introduction="
				+ introduction + ", management_institution=" + management_institution
				+ ", management_institution_phone=" + management_institution_phone + "]";
	}
	
}
