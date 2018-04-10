package com.ys.entity;

public class Village {

	private String village;
	private String longitude;
	private String latitude;
	private String location;
	private String county;
	
	public String getVillage() {
		return village;
	}
	
	public Village(String village, String location, String county) {
		super();
		this.village = village;
		this.location = location;
		this.county = county;
	}

	public void setVillage(String village) {
		this.village = village;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	
	@Override
	public String toString() {
		return "Village [village=" + village + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", location=" + location
				+ ", county=" + county + "]";
	}
		
}
