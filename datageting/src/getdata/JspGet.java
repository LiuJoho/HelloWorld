package getdata;


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import util.DBUtil;
import util.HttpClientUtil;


public class JspGet {
public static void main(String[] args) throws FileNotFoundException {
		MyThreadRule2 tr = new MyThreadRule2();
		tr.start();
	}

	

}
class MyThreadRule2 extends Thread{
	@Override
	public void run() {
		
		
		String url = "";
		String[] quyu = {"350203","350205","350206","350211","350212","350213"};
		for (int i = 0; i < quyu.length; i++) {	
			int num = 0;
			boolean max = true;
			while(max){
				url = "http://zawb.fjgat.gov.cn/weixin/zhfw/czw_qwjs_cx.jsp?rowpage="+num+"&ss_qx="+quyu[i]+"&rPageSize=15";
				String a = HttpClientUtil.doGet(url);
				if (a.indexOf("<li") != -1) {
					num++;
					String[] b = a.split("</li>");		
					for (int j = 0; j < b.length - 1; j++) {
						String c = b[j].substring(b[j].indexOf("pic(") + 4, b[j].indexOf(");"));
						String d = c.substring(c.indexOf(",") + 1, c.length()).replace("'", "");
						String urlPath = "http://www.fjadd.com/addr?id=" + d;
						System.out.println(urlPath);
						getInfo(urlPath);
					}
				}else{
					max = false;
				}
			}
					
		}						
	
	}
	
	public void getInfo(String str){
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
				String  bb = a.substring(i+6,j-1);
				String addr = a.substring(c+5,d-1);
				String sql = "insert into mobile_system (id,longitude_latitude,address,picurl,pic,police)values(?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id.toString());
				ps.setString(2, bb);
				ps.setString(3, addr);
				ps.setString(4, imgUrl);
				int policeInt = a.indexOf("line-height: 20px;\">");
				ps.setBinaryStream(5, null);
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
				ps.execute();
				System.out.println(Thread.currentThread().getName());
				System.out.println("【获得的经纬度："+bb+"】,【地址："+addr+"】,【图片地址:"+imgUrl+"】");
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

