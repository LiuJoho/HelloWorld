package com.ys.entity;

public class Village {

	private int id;
	private String doorid;
	private String village;
	private String longitude;
	private String latitude;
	private String location;
	private String county;
	private String country;
	private String building;
	
	public String getVillage() {
		return village;
	}
	
	public Village(int id, String village, String location, String county) {
		super();
		this.id = id;
		this.village = village;
		this.location = location;
		this.county = county;
	}		

	public Village(String doorid, String village) {
		super();
		this.doorid = doorid;
		this.village = village;
	}	

	public Village(int id, String village, String location, String county, String country) {
		super();
		this.id = id;
		this.village = village;
		this.location = location;
		this.county = county;
		this.country = country;
	}
	
	public Village(String village, String building, String county, String country) {
		super();
		this.village = village;
		this.building = building;
		this.county = county;
		this.country = country;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDoorid() {
		return doorid;
	}

	public void setDoorid(String doorid) {
		this.doorid = doorid;
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
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	@Override
	public String toString() {
		return "Village [id=" + id + ", doorid=" + doorid + ", village=" + village + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", location=" + location + ", county=" + county + ", country=" + country
				+ ", building=" + building + "]";
	}

		
}
