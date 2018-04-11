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

import util.DBUtil;
import util.HttpClientUtil;


public class TengXunExcelData {
	public static void main(String[] args) throws InterruptedException {
//		MyThreadRule3 tr = new MyThreadRule3();
//		tr.start();
		insertVillage();
	}

	public static void insertVillage() throws InterruptedException{
		List<House> list = new ArrayList<House>();
		list = queryInfo();
		int count = 0;
		for (House house : list) {
			count++;
			System.out.println("小区名称"+count+"：" + house.getRegion());
			String url = "http://apis.map.qq.com/ws/place/v1/search?boundary=region(厦门,0)"
					+"&keyword=" + house.getRegion()
					+"&page_size=2&page_index=1&orderby=_distance&key=TXBBZ-KTE3D-E7T4B-HLHQQ-LUZJ3-X5FGL";
				//System.out.println(url);
				String a = HttpClientUtil.doGet(url);
				System.out.println("返回数据："+a);
				String b = a.substring(a.indexOf("[") + 1, a.indexOf("],"));
				String[] c = b.split("},");
				//System.out.println("c:" + c[0]);
				for (int i = 0; i < c.length; i++) {
					if (c[i].indexOf("address") != -1) {
						String z = c[i].replace(" ", "");
						//System.out.print("z:" + z);
						String d = z.substring(z.indexOf("address") + 10, z.indexOf("tel") - 4);
						System.out.println("地址：" + d);
						String[] e = z.substring(z.indexOf("lat") + 5, z.length()).split(",");									
						System.out.println("纬度：" + e[0]);
						String f = e[1].substring(e[1].indexOf(":") + 1, e[1].length());
						System.out.println("经度：" + f);
						getInfo(house.getRegion(),f,e[0],d);
					}
					
				}
				Thread.sleep(500);
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
	           // list.add(new House(rs.getString("region")));
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
	
	public static void getInfo(String village,String longitude,String latitude,String location){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
				String sql = "insert into village_report (village,longitude,latitude,location)values(?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, village);
				ps.setString(2, longitude);
				ps.setString(3, latitude);
				ps.setString(4, location);
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

