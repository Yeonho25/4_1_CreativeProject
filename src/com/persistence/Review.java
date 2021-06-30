package com.persistence;

import java.time.LocalDateTime;

public class Review {
	private int id;
	private String memberId;
	private String restaurantId;
	private String tourName;
	private String content;
	private Double grade;
	private LocalDateTime registerDate;
	
	public Review(String memberId, String content,Double grade) {

		this.memberId = memberId;
		this.content = content;
		this.grade = grade;
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getTourName() {
		return tourName;
	}
	public void setTourName(String tourName) {
		this.tourName = tourName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}
	
	
}
