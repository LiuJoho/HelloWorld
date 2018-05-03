package com.ys.entity;

/**
 * 移动的地址
 * @author Administrator
 *
 */
public class YDaddress2 {

	private int id;
	private String county;
	private String towns;
	private String country;
	private String village;
	private String location;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public YDaddress2(int id, String county, String towns, String country, String village,String location) {
		super();
		this.id = id;
		this.county = county;
		this.towns = towns;
		this.country = country;
		this.village = village;
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "YDaddress2 [id=" + id + ", county=" + county + ", towns=" + towns + ", country=" + country
				+ ", village=" + village + ", location=" + location + "]";
	}		

		
}
