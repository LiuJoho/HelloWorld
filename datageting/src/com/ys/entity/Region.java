package com.ys.entity;

public class Region {

	private String id;
	private String title;
	private String address;
	private String location;
	private String ad_info;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}	
	public String getAd_info() {
		return ad_info;
	}
	public void setAd_info(String ad_info) {
		this.ad_info = ad_info;
	}
	@Override
	public String toString() {
		return "Region [id=" + id + ", title=" + title + ", address=" + address + ", location=" + location
				+ ", ad_info=" + ad_info + "]";
	}	
	
}
