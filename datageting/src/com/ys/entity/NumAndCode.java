package com.ys.entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class NumAndCode {
		

	private Double num;
	private String childCode;
	
	//储存表头
	static Map<Object,Integer> childHeadMap = new HashMap<Object, Integer>();
	
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public String getChildCode() {
		return childCode;
	}
	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}
	
	//学生表头
    public static Map<Object,Integer> setChildHeader(Row rowHead,int flag){  
   	
    	Cell cell = rowHead.getCell(flag);
    	switch (getRightTypeCell(cell).toString()) {
	    	case "city":
				childHeadMap.put("city", flag);
				break;
			case "county":
				childHeadMap.put("county", flag);
				break;
			case "towns":
				childHeadMap.put("towns", flag);
				break;
			case "country":
				childHeadMap.put("country", flag);
				break;
			case "village":
				childHeadMap.put("village", flag);
				break;
			case "doorplate":
				childHeadMap.put("doorplate", flag);
				break;
			case "box_num":
				childHeadMap.put("box_num", flag);
				break;
			case "latitude":
				childHeadMap.put("latitude", flag);
				break;
			case "longitude":
				childHeadMap.put("longitude", flag);
				break;
			case "free_num":
				childHeadMap.put("free_num", flag);
				break;
			case "box_name":
				childHeadMap.put("box_name", flag);
				break;
			case "house_num":
				childHeadMap.put("house_num", flag);
				break;
			case "remarks":
				childHeadMap.put("remarks", flag);
				break;
			case "box_address":
				childHeadMap.put("box_address", flag);
				break;
		}
    	return childHeadMap;
    }   
	
    /**
     *     
     * @param cell 一个单元格的对象
     * @return 返回该单元格相应的类型的值
     */
    public static Object getRightTypeCell(Cell cell){
    
        Object object = null;
        switch(cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING :
            {
                object=cell.getStringCellValue();
                break;
            }
            case Cell.CELL_TYPE_NUMERIC :
            {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                object=cell.getNumericCellValue();
                break;
            }
                
            case Cell.CELL_TYPE_FORMULA :
            {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                object=cell.getNumericCellValue();
                break;
            }
            
            case Cell.CELL_TYPE_BLANK :
            {
                cell.setCellType(Cell.CELL_TYPE_BLANK);
                object=cell.getStringCellValue();
                break;
            }
        }
        return object;
    }
    
}
