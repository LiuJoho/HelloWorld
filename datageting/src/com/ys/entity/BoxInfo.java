package com.ys.entity;

/**
 * 箱体
 * @author Administrator
 *
 */
public class BoxInfo {

	private String id;				//uuid
	private String boxName;			//箱体名称
	private String village;			//小区
	private String longitude;		//经度
	private String latitude;		//纬度
	private String city;			//城市
	private String county;			//县级
	private String country;			//村级
	private String towns;			//乡镇
	private String doorplate;		//门牌
	private String boxNum;			//箱体端口数
	private String freeNum;			//空闲数
	private String houseNum;		//箱体八级地址
	private String boxAddress;		//箱体七级地址
	private String remarks;			//备注名称
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoxName() {
		return boxName;
	}
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	public String getVillage() {
		return village;
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
	public String getTowns() {
		return towns;
	}
	public void setTowns(String towns) {
		this.towns = towns;
	}
	public String getDoorplate() {
		return doorplate;
	}
	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}

	public String getBoxNum() {
		return boxNum;
	}
	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}
	public String getFreeNum() {
		return freeNum;
	}
	public void setFreeNum(String freeNum) {
		this.freeNum = freeNum;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHouseNum() {
		return houseNum;
	}
	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}
	public String getBoxAddress() {
		return boxAddress;
	}
	public void setBoxAddress(String boxAddress) {
		this.boxAddress = boxAddress;
	}	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Override
	public String toString() {
		return "BoxInfo [id=" + id + ", boxName=" + boxName + ", village=" + village + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", city=" + city + ", county=" + county + ", country=" + country
				+ ", towns=" + towns + ", doorplate=" + doorplate + ", boxNum=" + boxNum + ", freeNum=" + freeNum
				+ ", houseNum=" + houseNum + ", boxAddress=" + boxAddress + ", remarks=" + remarks + "]";
	}		
	
}
