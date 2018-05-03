package getdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ys.entity.NumAndCode;
import com.ys.entity.YDaddress;

import util.DBUtil;

public class ExcelImport2 {
	
	Map<Object,Integer> childHeadMap = new HashMap<Object, Integer>();

	public static void main(String[] args) {
		String path = "D:\\20180419-新.xlsx";
		ExcelImport2 ei = new ExcelImport2();
		ei.getDataFromExcel(path);
	}
	
	public void getDataFromExcel(String filePath)
    {
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
        	if (num != 5) {
				System.out.println("Excel表头列数不对，请核实！");
			}
        }
        
        
        try
        {
            //----------------这里是表头列
            while (flag < 5)
            {
            	childHeadMap = NumAndCode.setChildHeader(rowHead,flag);
                flag++;
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("表头不合规范，请修改后重新导入");
        }
        for(Object key : childHeadMap.keySet()){ 
            Integer value = childHeadMap.get(key); 
            System.out.println(key+"  "+value); 
        }
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
    	YDaddress yd = new YDaddress();	
		Cell countyNum = row.getCell(childHeadMap.get("county"));
		String county = (String) NumAndCode.getRightTypeCell(countyNum);
		yd.setCounty(county);
		Cell townsNum = row.getCell(childHeadMap.get("towns"));
		String towns = (String) NumAndCode.getRightTypeCell(townsNum);
		yd.setTowns(towns);
		Cell countryNum = row.getCell(childHeadMap.get("country"));
		String country = (String) NumAndCode.getRightTypeCell(countryNum);
		yd.setCountry(country);
		Cell villageNum = row.getCell(childHeadMap.get("village"));
		String village = (String) NumAndCode.getRightTypeCell(villageNum);
		yd.setVillage(village);
		Cell doorplateNum = row.getCell(childHeadMap.get("doorplate"));
		String doorplate = String.valueOf(NumAndCode.getRightTypeCell(doorplateNum)) ;
		if ("1".equals(doorplate)) {
			yd.setDoorplate(null);
		}else{
			yd.setDoorplate(doorplate);
		}
		insertYD(yd);
    }
	
    public void insertYD(YDaddress yd){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DBUtil.getConn();			
				String sql = "insert into region_new (county,towns,country,village,doorplate)values(?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, yd.getCounty());
				ps.setString(2, yd.getTowns());
				ps.setString(3, yd.getCountry());
				ps.setString(4, yd.getVillage());
				ps.setString(5, yd.getDoorplate());
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
