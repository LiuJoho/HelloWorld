package com.ys.entity;

/**
 * 移动的地址
 * @author Administrator
 *
 */
public class YDaddress {

	private Long id;
	private String city;
	private String county;
	private String towns;
	private String country;
	private String village;
	private String doorplate;
	private String building;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getTowns() {
		return towns;
	}
	public void setTowns(String towns) {
		this.towns = towns;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getDoorplate() {
		return doorplate;
	}
	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	
	@Override
	public String toString() {
		return "YDaddress [id=" + id + ", city=" + city + ", county=" + county + ", towns=" + towns + ", country="
				+ country + ", village=" + village + ", doorplate=" + doorplate + ", building=" + building + "]";
	}
		
}
