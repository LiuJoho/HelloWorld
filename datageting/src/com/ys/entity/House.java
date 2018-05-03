package com.ys.entity;

public class House {

	private String county;
	private String region;
	private String address;

	public House(String county, String region) {
		super();
		this.county = county;
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}	

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "House [county=" + county + ", region=" + region + ", address=" + address + "]";
	}	
	
}
