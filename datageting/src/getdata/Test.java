package getdata;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import util.DBUtil;
import util.HttpClientUtil;


public class Test {
public static void main(String[] args) {
	
		/*for (int i = 0x0; i <= 0xf; i++) {
			String one = Integer.toHexString(i).toUpperCase();
			ThreadPoolUtils.execute(new MyRunnable(one) );
		}*/
	insert2ause();
	 
		
	}

public static void insert2ause(){

	Connection conn = null;
	PreparedStatement ps = null;
	try{
		conn = DBUtil.getConn();
		for (int b = 0x0; b <= 0xf; b++) {
		for (int f = 0x0; f <= 0xf; f++) {
			String one = Integer.toHexString(f).toUpperCase();
			for (int y = 0x0; y <= 0xf; y++) {
				String two = Integer.toHexString(y).toUpperCase();
				for (int z = 0x0; z <= 0xf; z++) {
					String three = Integer.toHexString(z).toUpperCase();
					for (int s = 0x0; s <= 0xf; s++) {
						String four = Integer.toHexString(s).toUpperCase();
						String a = HttpClientUtil.doGet("http://www.fjadd.com/shhyy/addr_list.jsp?systemid=BFC4828"+b+"-"+one+two+three+four+"-00EE-E043-0A82290600EE",null);
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
							String sql = "insert into ause_mobile (id,longitude_latitude,address,picurl,pic,police)values(?,?,?,?,?,?)";
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
								 ps.setString(6, police);
							 }else{
								 ps.setString(6, null);
							 }
							 ps.execute();
							System.out.println("【获得的经纬度："+bb+"】,【地址："+addr+"】,【图片地址"+imgUrl+"】");
						}
					}
				}
			}
		}
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
class MyRunnable implements Runnable{
	String b;
	public MyRunnable(String b) {
		this.b=b;
	}
	@Override
	public void run() {
		Connection conn = null;
		PreparedStatement ps = null;
		InputStream in = null;
		try{
			conn = DBUtil.getConn();
			for (int f = 0x0; f <= 0xf; f++) {
				String one = Integer.toHexString(f).toUpperCase();
				for (int y = 0x0; y <= 0xf; y++) {
					String two = Integer.toHexString(y).toUpperCase();
					for (int z = 0x0; z <= 0xf; z++) {
						String three = Integer.toHexString(z).toUpperCase();
						for (int s = 0x0; s <= 0xf; s++) {
							String four = Integer.toHexString(s).toUpperCase();
							String a = HttpClientUtil.doGet("http://www.fjadd.com/shhyy/addr_list.jsp?systemid=BFC4828"+b+"-"+one+two+three+four+"-00EE-E043-0A82290600EE",null);
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
									/*try{
										in = ImageUtil.getImageFromHttp(imgUrl);
									}catch(Exception e){
										e.printStackTrace();
									}*/
								}
								String  bb = a.substring(i+6,j-1);
								String addr = a.substring(c+5,d-1);
								String sql = "insert into mobile (id,longitude_latitude,address,picurl,pic,police)values(?,?,?,?,?,?)";
								 ps = conn.prepareStatement(sql);
								 ps.setString(1, id.toString());
								 ps.setString(2, bb);
								 ps.setString(3, addr);
								 ps.setString(4, imgUrl);
								 /*if(in!=null){
									 ps.setBinaryStream(5, in, in.available());
								 }else{
									 ps.setBinaryStream(5, null);
								 }*/
								 int policeInt = a.indexOf("line-height: 20px;\">");
								 
								 String policeStr = a.substring(policeInt+1);
								 String police = policeStr.substring(0, policeStr.indexOf("</a>"));
								 police=police.substring(police.indexOf(">")+1);
								 ps.setBinaryStream(5, null);
								 ps.setString(6, police);
								 ps.execute();
								 System.out.println(Thread.currentThread().getName());
								//contentToTxt("d:/test/adrss-1.txt","【获得的经纬度："+bb+"】,【地址："+addr+"】");
								System.out.println("【获得的经纬度："+bb+"】,【地址："+addr+"】,【图片地址"+imgUrl+"】");
							}
						}
					}
				}
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
	
	
	public static void contentToTxt(String filePath, String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true));
			writer.write("\n" + content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

