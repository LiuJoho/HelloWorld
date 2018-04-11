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
			case "地市":
				childHeadMap.put("city", flag);
				break;
			case "县市":
				childHeadMap.put("county", flag);
				break;
			case "乡镇":
				childHeadMap.put("towns", flag);
				break;
			case "村居名称":
				childHeadMap.put("country", flag);
				break;
			case "小区名称":
				childHeadMap.put("village", flag);
				break;
			case "门牌号":
				childHeadMap.put("doorplate", flag);
				break;
			case "楼名称":
				childHeadMap.put("building", flag);
				break;
			case "村居编码":
				childHeadMap.put("cellCoding", flag);
				break;
			case "小区编码":
				childHeadMap.put("villageCode", flag);
				break;
			case "楼道名称":
				childHeadMap.put("buildName", flag);
				break;
			case "房间号":
				childHeadMap.put("houseName", flag);
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
