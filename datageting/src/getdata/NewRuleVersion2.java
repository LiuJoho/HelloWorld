package getdata;


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import util.DBUtil;
import util.HttpClientUtil;


public class NewRuleVersion2 {
public static void main(String[] args) throws FileNotFoundException {
		MyThreadRule1 tr = new MyThreadRule1();
		tr.start();
	}

	

}
class MyThreadRule1 extends Thread{
	@Override
	public void run() {
				
			for (int a1 = 0x0; a1 <= 0xf; a1++) {
				for(int a12=0x0; a12<=0xf; a12++){								
						StringBuffer buf = new StringBuffer();
							buf.append(Integer.toHexString(a1).toUpperCase())
								.append(Integer.toHexString(a12).toUpperCase());
						String url = "http://www.fjadd.com/addr?id=6382CDD3-D0"+buf.toString()+"-15A7-E054-90E2BA510A0C";
						System.out.println(url);
						getInfo(url);
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
				String sql = "insert into mobile_add (id,longitude_latitude,address,picurl,pic,police)values(?,?,?,?,?,?)";
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

