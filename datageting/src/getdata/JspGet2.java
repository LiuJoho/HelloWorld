package getdata;


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ys.entity.Village;

import util.DBUtil;
import util.HttpClientUtil;


public class JspGet2 {
	public static void main(String[] args) throws FileNotFoundException {
		MyThreadRule4 tr = new MyThreadRule4();
		tr.start();		
	}

	

}
class MyThreadRule4 extends Thread{
	@Override
	public void run() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("思明区","350203");
		map.put("海沧区","350205");
		map.put("湖里区","350206");
		map.put("集美区","350211");
		map.put("同安区","350212");
		map.put("翔安区","350213");
		List<Village> list = new ArrayList<Village>();
		list = queryInfo();
		String url = "";
		//String[] quyu = {"350203","350205","350206","350211","350212","350213"};
		for (Village village : list) {
			int num = 0;
			boolean max = true;
			while(max){
				url = "http://zawb.fjgat.gov.cn/weixin/zhfw/czw_qwjs_cx.jsp?sunitname="+village.getLocation()
						+ "&phrase=福建省厦门市"+village.getCounty()
						+ "&rowpage="+num
						+"&ss_qx="+map.get(village.getCounty())+"&rPageSize=15";
				System.out.println("url:" + url);
				String a = HttpClientUtil.doGet(url);
				System.out.println(a);
				if (a.indexOf("<li") != -1) {
					num++;
					String[] b = a.split("</li>");		
					for (int j = 0; j < b.length - 1; j++) {
						String c = b[j].substring(b[j].indexOf("pic(") + 4, b[j].indexOf(");"));
						String d = c.substring(c.indexOf(",") + 1, c.length()).replace("'", "");
						String urlPath = "http://www.fjadd.com/addr?id=" + d;
						System.out.println(urlPath);
						getInfo(urlPath,village.getVillage());
					}
				}else{
					max = false;
					if (num == 0) {
						System.out.println(village.getVillage() + "," 
					+ village.getLocation() + ","+ village.getCounty());
					}
				}
			}								
		}						
	
	}
	
	public static List<Village> queryInfo(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Village> list = null;
		try{
			conn = DBUtil.getConn();			
			String sql = "select * from village_report";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			list = new ArrayList<Village>();
			while (rs.next()){
	            list.add(new Village(rs.getString("village"),rs.getString("location"),rs.getString("county")));
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
	
	public void insertTemp(String village,String location,String county){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
				String sql = "insert into village_temp (village,location,county)values(?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, village);
				ps.setString(2, location);
				ps.setString(3, county);
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
	
	public void getInfo(String str,String village){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
			String a = HttpClientUtil.doGet(str);
			System.out.println("url地址："+str);
			int i = a.indexOf("coord:");
			int j = a.indexOf("title:");
			int c = a.indexOf("addr:");
			int d = a.indexOf("key=");
			if (i>0&&j>0) {
				UUID id = UUID.randomUUID();
				int imgFrom = a.indexOf("showimg('");
				String imgUrl = null;
				if(imgFrom>0){
					String imgStr = a.substring(imgFrom);
					int imgTo = imgStr.indexOf("')");
					imgUrl = imgStr.substring(9, imgTo);
				}
				String[] bb = a.substring(i+6,j-1).split(",");
				String addr = a.substring(c+5,d-1);
				String sql = "insert into village_add (id,latitude,longitude,address,picurl,police,village)values(?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id.toString());
				ps.setString(2, bb[0]);
				ps.setString(3, bb[1]);
				ps.setString(4, addr);
				int policeInt = a.indexOf("line-height: 20px;\">");
				ps.setString(5, imgUrl);
				if(policeInt>0){
					String policeStr = a.substring(policeInt+1);
					String police = policeStr.substring(0, policeStr.indexOf("</a>"));
					police=police.substring(police.indexOf(">")+1);
					if(police.length()>10){
						ps.setString(6, null);
					}else{
						ps.setString(6, police);
					}
				}else{
					ps.setString(6, null);
				}
				ps.setString(7,village);
				ps.execute();
				System.out.println(Thread.currentThread().getName());
				System.out.println("【获得的纬度："+bb[0]+"】,【地址："+addr+"】,【图片地址:"+village+"】"+"】,【获得的经度："+bb[1]+"】");
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

	}
	
}

