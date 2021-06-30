package DTO;

import java.io.Serializable;

public class ReviewDTO implements Serializable
{
	private static final long serialVersionUID = 123456789L;
	
	private String member_id;
	private String tour_name;
	private int restaurant_id;
	private String content;
	private int point_id;
	private float point;
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getTour_name() {
		return tour_name;
	}
	public void setTour_name(String tour_name) {
		this.tour_name = tour_name;
	}
	public int getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPoint_id() {
		return point_id;
	}
	public void setPoint_id(int point_id) {
		this.point_id = point_id;
	}
	public float getPoint() {
		return point;
	}
	public void setPoint(float point) {
		this.point = point;
	}
	
	@Override
	public String toString() {
		return "ReviewDTO [member_id=" + member_id + ", tour_name=" + tour_name + ", restaurant_id=" + restaurant_id
				+ ", content=" + content + ", point_id=" + point_id + ", point=" + point + "]";
	}
	
	
	
}
