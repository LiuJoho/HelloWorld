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
public class UpdateNoVillage {

	public static void main(String[] args) {
		goGet();
	}
	
	public static void goGet(){
		List<Village> village = queryInfo();		
		
		for (Village village2 : village) {					
			String building = "";
			System.out.println("--------------------------------------------------------------------------------------");
			System.out.println(village2);
			boolean elt = false;
			elt = queryAllRe(village2.getBuilding());
			System.out.println(elt);
			if (elt) {
				updateTemp(village2.getVillage(),village2.getBuilding());					
			} else {
				building = village2.getBuilding();
				insertTemp(village2.getVillage(),building,village2.getCounty(),village2.getCountry());
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
			String sql = "select * from village_temp";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			list = new ArrayList<Village>();
			while (rs.next()){
	            list.add(new Village(rs.getString("village"),rs.getString("building"),rs.getString("county"),rs.getString("country")));
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
	
	//查询是否有数据
	public static boolean queryAllRe(String building){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean elt = false;
		try{
			conn = DBUtil.getConn();			
			String sql = "SELECT * FROM village_all_remove a WHERE a.address LIKE '%" + building + "%'";
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
	
	public static void updateTemp(String village,String location){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
				String sql = "UPDATE village_all_remove SET village = CONCAT(village,',',?) WHERE address LIKE '%"+ location +"%'";
				ps = conn.prepareStatement(sql);
				ps.setString(1, village);
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

	public static void insertTemp(String village,String building,String county,String country){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
				String sql = "insert into village_temp_2 (village,building,county,country) values(?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, village);
				ps.setString(2, building);
				ps.setString(3, county);
				ps.setString(4, country);
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
