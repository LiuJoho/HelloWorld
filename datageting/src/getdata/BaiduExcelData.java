package getdata;


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.ys.entity.House;

import util.DBUtil;
import util.HttpClientUtil;


public class BaiduExcelData {
	public static void main(String[] args) throws InterruptedException {
//		MyThreadRule3 tr = new MyThreadRule3();  竹坝新村
//		tr.start();  
//		http://api.map.baidu.com/place/v2/search?query=%E8%A5%BF%E5%B1%B1%E6%9D%91&region=%E5%8E%A6%E9%97%A8&output=json&page_size=1&page_num=0&ak=gThPVCUBgUyx1M2IlNOCDoE7KZ237qNK
	/*	String url = "http://api.map.baidu.com/place/v2/search?query=西山村"
		+"&region=厦门&output=json&page_size=1&page_num=0&ak=gThPVCUBgUyx1M2IlNOCDoE7KZ237qNK";
		//System.out.println(url);
		String a = HttpClientUtil.doGet(url);
		System.out.println(a);
		String z = a.replace(" ", "");
		String y = "测试区";
		String s = z.substring(z.indexOf("[") + 1, z.indexOf("]")).trim();
		System.out.println("s:" + s.length());
		if (s.length() != 0) {
			String[] b = z.substring(z.indexOf("results") + 12, z.indexOf("]") - 2).split(",");
			for (int i = 0; i < b.length; i++) {	
				if (b[i].indexOf("lat") != -1) {
					String c = b[i].substring(b[i].indexOf("lat") + 5, b[i].length());
					System.out.println("纬度：" + c);
				} else if(b[i].indexOf("lng") != -1){
					String d = b[i].substring(b[i].indexOf("lng") + 5, b[i].length() - 1);
					System.out.println("经度：" + d);
				} else if(b[i].indexOf("address") != -1){
					String d = b[i].substring(b[i].indexOf("address") + 10, b[i].length() - 1);
					System.out.println("地址：" + d);
				} else if(b[i].indexOf("area") != -1){
					String d = b[i].substring(b[i].indexOf("area") + 7, b[i].length() - 1);
					y = d;
				}
			}
			System.out.println("县级：" + y);
			String[] e = z.substring(z.indexOf("lat") + 5, z.length()).split(",");									
			System.out.println("纬度：" + e[0]);
			String f = e[1].substring(e[1].indexOf(":") + 1, e[1].length());
			System.out.println("经度：" + f);
		} else {
			System.out.println();
		}*/
		/*List<House> list = new ArrayList<House>();
		list = queryInfo();
		System.out.println(list.get(0));*/
		insertVillage();
	}

	public static void insertVillage() throws InterruptedException{
		List<House> list = new ArrayList<House>();
		list = queryInfo();
		int count = 0;
		for (House house : list) {
			count++;
			System.out.println("小区名称"+count+"：" + house.getRegion());
			String url = "http://api.map.baidu.com/place/v2/search?query="+house.getRegion().trim()
					+"&region=厦门&output=json&page_size=1&page_num=0&ak=gThPVCUBgUyx1M2IlNOCDoE7KZ237qNK";
				//System.out.println(url);
			String a = HttpClientUtil.doGet(url);
			String c = "";
			String d = "";
			String e = "";
			String f = "";
			System.out.println("返回数据：" + a);
			String z = a.replace(" ", "");
			String y = house.getCounty().trim();
			String s = z.substring(z.indexOf("[") + 1, z.indexOf("]")).trim();
			if (s.length() != 0) {
				String[] b = z.substring(z.indexOf("results") + 12, z.indexOf("]") - 2).split(",");
				for (int i = 0; i < b.length; i++) {	
					if (b[i].indexOf("lat") != -1) {
						c = b[i].substring(b[i].indexOf("lat") + 5, b[i].length());
						System.out.println("纬度：" + c);
					} else if(b[i].indexOf("lng") != -1){
						d = b[i].substring(b[i].indexOf("lng") + 5, b[i].length() - 1);
						System.out.println("经度：" + d);
					} else if(b[i].indexOf("address") != -1){
						e = b[i].substring(b[i].indexOf("address") + 10, b[i].length() - 1);
						System.out.println("地址：" + e);
					} else if(b[i].indexOf("area") != -1){
						f = b[i].substring(b[i].indexOf("area") + 7, b[i].length() - 1);
						System.out.println("返回县级:" + f);
						y = f;
					}
				}
			}		
			getInfo(house.getRegion(),d,c,e,y);			
			System.out.println("县级：" + y);
			Thread.sleep(400);
		}
						
						//getInfo(url);					
						/*JSONObject jsonOne = JSONObject.fromObject(a);
						JsonState obj = (JsonState) JSONObject.toBean(jsonOne, JsonState.class);
						System.out.println("json" + obj);*/						
	}
	
	public static List<House> queryInfo(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<House> list = null;
		try{
			conn = DBUtil.getConn();			
			String sql = "select * from region";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			list = new ArrayList<House>();
			while (rs.next()){
	            list.add(new House(rs.getString("county"),rs.getString("region")));
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 DBUtil.closeConn(conn);
	            if (null != ps) {
	                try {
	                    ps.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	               }
	           }
	         
		}
		return list;
	}
	
	public static void getInfo(String village,String longitude,String latitude,String location,String county){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
				String sql = "insert into village_report (village,longitude,latitude,location,county)values(?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, village);
				ps.setString(2, longitude);
				ps.setString(3, latitude);
				ps.setString(4, location);
				ps.setString(5, county);
				ps.executeUpdate();
				//System.out.println(Thread.currentThread().getName());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 DBUtil.closeConn(conn);
	            if (null != ps) {
	                try {
	                    ps.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	               }
	           }
		}

	}
	
}

