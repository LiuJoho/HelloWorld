package getdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ys.entity.Village;

import util.DBUtil;
import util.ThreadPoolUtils;
/**
 * 找没有的小区
 * @author Administrator
 *
 */
public class ZhaoNoVillage {

	public static void main(String[] args) {
		ThreadPoolUtils.execute(new MyThreadRule16());
	}
}
	
class MyThreadRule16 extends Thread{
	@Override
	public void run() {
		
			List<Village> village = queryInfo();		
			
			for (Village village2 : village) {					
				String doorplate = "";
				String county = "";
				String building = "";
				System.out.println("--------------------------------------------------------------------------------------");
				System.out.println(village2);
				boolean elt = false;
				elt = queryAllRe(village2.getVillage());
				System.out.println(elt);
				if (!elt) {
					Village villa = queryYD(village2.getVillage());
					System.out.println("移动数据：" + villa);
					if (villa != null) {
						doorplate = villa.getLocation();
						county = villa.getCounty();
						building = villa.getBuilding();
					}
					insertTemp(village2.getVillage(),doorplate,county,village2.getDoorid(),building);
				}			
			}

	}


	//获取小区
	public static List<Village> queryInfo(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Village> list = null;
		try{
			conn = DBUtil.getConn();			
			String sql = "select * from region_yuan";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			list = new ArrayList<Village>();
			while (rs.next()){
	            list.add(new Village(rs.getString("country"),rs.getString("village")));
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
	
	//查找移动数据库
	public static Village queryYD(String village){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Village vill = null;
		try{
			conn = DBUtil.getConn();			
			String sql = "SELECT * FROM yd_address a WHERE a.village = ? LIMIT 0,1";
			ps = conn.prepareStatement(sql);
			ps.setString(1, village);
			rs= ps.executeQuery();			
			while (rs.next()){
				vill = new Village(rs.getString("village"),rs.getString("doorplate"),rs.getString("county"),rs.getString("building"));
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
		return vill;
	}
	
	//查找数据是否有重复
	public static boolean queryAllRe(String village){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean elt = false;
		try{
			conn = DBUtil.getConn();			
			String sql = "SELECT * FROM village_all_remove a WHERE a.village LIKE '%" + village + "%'";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while (rs.next()){
	            elt = true;
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
		return elt;
	}

	public static void insertTemp(String village,String location,String county,String country,String building){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
				String sql = "insert into village_temp (village,doorplate,county,country,building) values(?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, village);
				ps.setString(2, location);
				ps.setString(3, county);
				ps.setString(4, country);
				ps.setString(5, building);
				ps.executeUpdate();
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
