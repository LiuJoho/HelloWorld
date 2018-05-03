package getdata;


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ys.entity.Village;
import com.ys.entity.YDaddress2;

import util.DBUtil;
import util.HttpClientUtil;
import util.ThreadPoolUtils;


public class UpdateJSP {
	public static void main(String[] args) throws FileNotFoundException {
		//MyThreadRule6 tr = new MyThreadRule6();
		ThreadPoolUtils.execute(new MyThreadRule12());
		//tr.start();		
	}

	

}
class MyThreadRule12 extends Thread{
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
		for (Village village : list) {
			String urlPath = "http://www.fjadd.com/addr?id=" + village.getDoorid();
			getInfo(village.getDoorid(),urlPath,village.getVillage() + ",73051部队（四林区域）");				
		}											
	}
	
	public static List<Village> queryInfo(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Village> list = null;
		try{
			conn = DBUtil.getConn();			
			String sql = "SELECT * FROM village_all_remove WHERE address LIKE '%四林%'";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			list = new ArrayList<Village>();
			while (rs.next()){
	            list.add(new Village(rs.getString("id"),rs.getString("village")));
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
	
	public void getInfo(String id,String str,String village){
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
				int imgFrom = a.indexOf("showimg('");
				String imgUrl = null;
				if(imgFrom>0){
					String imgStr = a.substring(imgFrom);
					int imgTo = imgStr.indexOf("')");
					imgUrl = imgStr.substring(9, imgTo);
				}
				String[] bb = a.substring(i+6,j-1).split(",");
				String addr = a.substring(c+5,d-1);
				String sql = "update village_all_remove set address = ?,village = ? where address LIKE '%四林%'";
				ps = conn.prepareStatement(sql);
				ps.setString(1, addr);
				ps.setString(2, village);
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

