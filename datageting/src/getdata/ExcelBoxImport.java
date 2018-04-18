package getdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ys.entity.BoxInfo;
import com.ys.entity.NumAndCode;
import com.ys.entity.YDaddress;

import util.DBUtil;
import util.ThreadPoolUtils;

public class ExcelBoxImport {
	
	Map<Object,Integer> childHeadMap = new HashMap<Object, Integer>();

	public static void main(String[] args) {
		String path = "D:\\箱体及GIS信息4.18.xlsx";
		ExcelBoxImport ei = new ExcelBoxImport();
		ei.getDataFromExcel(path);			
	}
	
	public void getDataFromExcel(String filePath){
        //判断是否为excel类型文件
        if(!filePath.endsWith(".xls")&&!filePath.endsWith(".xlsx"))
        {
            System.out.println("文件不是excel类型");
        }
        
        FileInputStream fis = null;
        Workbook wookbook = null;      
        try
        {
            //获取一个绝对地址的流
              fis = new FileInputStream(filePath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       
        try 
        {
            //2003版本的excel，用.xls结尾
            wookbook = new HSSFWorkbook(fis);//得到工作簿
             
        } 
        catch (Exception ex) 
        {
            //ex.printStackTrace();
            try
            {
                //这里需要重新获取流对象，因为前面的异常导致了流的关闭—————————————————————————————加了这一行
                 fis = new FileInputStream(filePath);
                //2007版本的excel，用.xlsx结尾
                
                wookbook = new XSSFWorkbook(filePath);//得到工作簿
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        //得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);                                       
        getSheetOne(sheet);
    }
	
	//处理学生工作表一		7个表头
    public void getSheetOne(Sheet sheet){
    	int flag = 0;
    	//获得表头
        Row rowHead = sheet.getRow(0);   
        
        if(rowHead == null)
        {
            System.out.println("表头为空！");
        }else{
        	int num = 0;
        	for (Cell cell : rowHead) {
            	String cellHead = (String) NumAndCode.getRightTypeCell(cell);
    			if (cell != null && !"".equals(cellHead) ) {
    				num++;
    				System.out.println(cell + "," + num);
    			}
    		}
        	if (num != 14) {
				System.out.println("Excel表头列数不对，请核实！");
			}
        }
        
        
        try
        {
            //----------------这里是表头列
            while (flag < 14)
            {
            	childHeadMap = NumAndCode.setChildHeader(rowHead,flag);
                flag++;
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("表头不合规范，请修改后重新导入");
        }
        /*for(Object key : childHeadMap.keySet()){ 
            Integer value = childHeadMap.get(key); 
            System.out.println(key+"  "+value); 
        }*/
        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        if(0 == totalRowNum)
        {
            System.out.println("Excel内没有数据！");
        }
        
        //获得所有数据
        for(int i = 1 ; i <= totalRowNum; i++)
        {
            //获得第i行对象
            Row row = sheet.getRow(i);
            try
            {
           	
            	setData(row); 
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("获取单元格错误");
            }            
        }
    } 
    
    public void setData(Row row){
    	BoxInfo yd = new BoxInfo();	
    	Cell cityNum = row.getCell(childHeadMap.get("city"));
		String city = (String) NumAndCode.getRightTypeCell(cityNum);
		yd.setCity(city);
		Cell countyNum = row.getCell(childHeadMap.get("county"));
		String county = (String) NumAndCode.getRightTypeCell(countyNum);
		yd.setCounty(county);
		Cell townsNum = row.getCell(childHeadMap.get("towns"));
		String towns = (String) NumAndCode.getRightTypeCell(townsNum);
		yd.setTowns(towns);
		Cell countryNum = row.getCell(childHeadMap.get("country"));
		String country = (String) NumAndCode.getRightTypeCell(countryNum);
		yd.setCountry(country);		
		Cell box_nameNum = row.getCell(childHeadMap.get("box_name"));
		String box_name = (String) NumAndCode.getRightTypeCell(box_nameNum);
		yd.setBoxName(box_name);
		Cell longitudeNum = row.getCell(childHeadMap.get("longitude"));
		String longitude = (String) NumAndCode.getRightTypeCell(longitudeNum);
		yd.setLongitude(longitude);
		Cell latitudeNum = row.getCell(childHeadMap.get("latitude"));
		String latitude = (String) NumAndCode.getRightTypeCell(latitudeNum);
		yd.setLatitude(latitude);
		Cell villageNum = row.getCell(childHeadMap.get("village"));
		String village = (String) NumAndCode.getRightTypeCell(villageNum);
		yd.setVillage(village);
		Cell doorplateNum = row.getCell(childHeadMap.get("doorplate"));
		String doorplate = (String) NumAndCode.getRightTypeCell(doorplateNum);
		yd.setDoorplate(doorplate);	
		Cell box_numNum = row.getCell(childHeadMap.get("box_num"));
		String box_num = (String) NumAndCode.getRightTypeCell(box_numNum);
		yd.setBoxNum(box_num);
		Cell free_numNum = row.getCell(childHeadMap.get("free_num"));
		String free_num = (String) NumAndCode.getRightTypeCell(free_numNum);
		yd.setFreeNum(free_num);
		Cell house_numNum = row.getCell(childHeadMap.get("house_num"));
		String house_num = (String) NumAndCode.getRightTypeCell(house_numNum);		
		if("/".equals(house_num)){
			yd.setHouseNum(null);
		} else {
			yd.setHouseNum(house_num);
		}	
		Cell remarksNum = row.getCell(childHeadMap.get("remarks"));
		String remarks = (String) NumAndCode.getRightTypeCell(remarksNum);
		yd.setRemarks(remarks);
		Cell box_addressNum = row.getCell(childHeadMap.get("box_address"));
		String box_address = (String) NumAndCode.getRightTypeCell(box_addressNum);
		if("/".equals(box_address)){
			yd.setBoxAddress(null);
		} else {
			yd.setBoxAddress(box_address);
		}		
		insertYD(yd);
    }
	
    
    public void insertYD(BoxInfo yd){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
				UUID id = UUID.randomUUID();
				String sql = "insert into yd_boxinfo(box_name,county,towns,country,village,doorplate,box_num,longitude,latitude,free_num,id,city,remarks,box_address,house_num) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, yd.getBoxName());
				ps.setString(2, yd.getCounty());
				ps.setString(3, yd.getTowns());
				ps.setString(4, yd.getCountry());
				ps.setString(5, yd.getVillage());
				ps.setString(6, yd.getDoorplate());
				ps.setString(7, yd.getBoxNum());
				ps.setString(8, yd.getLongitude());
				ps.setString(9, yd.getLatitude());
				ps.setString(10, yd.getFreeNum());
				ps.setString(11, id.toString());
				ps.setString(12, yd.getCity());
				ps.setString(13, yd.getRemarks());
				ps.setString(14, yd.getBoxAddress());
				ps.setString(15, yd.getHouseNum());
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
