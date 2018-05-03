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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ys.entity.Village;
import com.ys.entity.VillageRoom;

import util.DBUtil;
import util.HttpClientUtil;
import util.ThreadPoolUtils;


public class RoomGet {
	public static void main(String[] args) throws FileNotFoundException {
		/*MyThreadRule5 tr = new MyThreadRule5();
		tr.start();*/	
		ThreadPoolUtils.execute(new MyThreadRule5());		
	}

	

}
class MyThreadRule5 extends Thread{
	@Override
	public void run() {
		List<Village> list = new ArrayList<Village>();
		list = queryInfo();
		String url = "";
		for (Village vill : list) {
			url = "http://zawb.fjgat.gov.cn/weixin/zhfw/house_jzw_room.jsp?operid=ovT0guIoRy-oFZCXDUATnH5Np2H4&systemid=" + vill.getDoorid();
			System.out.println("url:" + url);
			String a = HttpClientUtil.doGet(url);
			String[] b = a.split("</table>");
			for (int i = 0; i < b.length; i++) {
				if (b[i].indexOf("厦门市") != -1) {
					System.out.println(i + ":" + b[i]);
				} else if (b[i].indexOf("厦门市") == -1 && b[i].indexOf("goto_fwxx('") != -1 ) {
					String[] c = b[i].split("goto_fwxx");
					for (int j = 1; j < c.length; j++) {
						String d = c[j].substring(c[j].indexOf("('") + 2, c[j].indexOf("','"));
						System.out.println(d);
						String urlPath = "http://www.fjadd.com/addr?id=" + d;
						try{
							getInfo(vill.getDoorid(),d,urlPath,vill.getVillage());
						}catch(Exception e){
							e.printStackTrace();
						}
						
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
			String sql = "select * from village_all_remove7";
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
	
	public void getInfo(String doorid,String roomid,String str,String village){
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
				String sql = "insert into village_add_room (doorid,roomid,latitude,longitude,address,picurl,police,village,createtime)values(?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, doorid);
				ps.setString(2, roomid);
				ps.setString(3, bb[0]);
				ps.setString(4, bb[1]);
				ps.setString(5, addr);
				int policeInt = a.indexOf("line-height: 20px;\">");
				ps.setString(6, imgUrl);
				if(policeInt>0){
					String policeStr = a.substring(policeInt+1);
					String police = policeStr.substring(0, policeStr.indexOf("</a>"));
					police=police.substring(police.indexOf(">")+1);
					if(police.length()>10){
						ps.setString(7, null);
					}else{
						ps.setString(7, police);
					}
				}else{
					ps.setString(7, null);
				}
				ps.setString(8,village);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ps.setString(9,df.format(new Date()));
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

