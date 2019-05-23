package com.ysf.excel.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ysf.excel.entity.SheetItem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Yeshufeng
 * @title 读Excel 得到map映射表
 * @date 2016/11/30
 */
public abstract class ExcelTools {

    public void dealExcel(String filePath) {

        File file = new File(filePath);
        //读excel
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Workbook workBook = new XSSFWorkbook(bis);
            dealSheet(workBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理每一行
     * @param workbook
     */
    private void dealSheet(Workbook workbook){

        Integer sheetIndex = workbook.getNumberOfSheets();

        for (int i = 0; i < sheetIndex; i++) {

            Sheet sheet = workbook.getSheetAt(i);
            int rowNumbers = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(0);

            //标题分开读
            Map<Integer,String> titleMap = readeFirstRow(firstRow);

            SheetItem sheetItem = new SheetItem();
            List<Map<String,Object>> objectList = Lists.newArrayList();

            //excel具体内容
            for (int j = 1; j < rowNumbers; j++) {
                Row row = sheet.getRow(j);
                Map<String,Object> rowObject = readOneRow(row,titleMap);
                objectList.add(rowObject);
            }
            sheetItem.setRowObjectList(objectList);
            sheetItem.setSheetName(sheet.getSheetName());

            dealWith(sheetItem);
            dealWith(objectList);
        }
    }


    private Map<Integer,String> readeFirstRow(Row firstRow ){
        Map<Integer,String> params = Maps.newHashMap();

        for (int i = 0; i <firstRow.getPhysicalNumberOfCells() ; i++) {
            Cell cellInFirstRow = firstRow.getCell(i);
            //获取值
            Object value = readOneCell(cellInFirstRow);
            params.put(i,String.valueOf(value));
        }
        return params;

    }

    /**
     * 读出来的结果 key 为titleMap里的tile，value是每一行对应title的值
     * @param row
     * @param titleMap
     * @return
     */
    private Map<String,Object> readOneRow(Row row,Map<Integer,String> titleMap){
        Map<String,Object> rowObject = Maps.newHashMap();
        Map<Integer,Object> rowObjectWithIndex = Maps.newHashMap();

        for (int i = 0;i < row.getPhysicalNumberOfCells();i++){
            Object cellValue = readOneCell(row.getCell(i));
            rowObjectWithIndex.put(i,cellValue);
        }

        titleMap.forEach((index,fieldName) -> {
            Object fieldValue = rowObjectWithIndex.get(index);
            rowObject.put(fieldName,fieldValue);
        });

        return rowObject;
    }

    private Object readOneCell(Cell cell){

        Object cellValue;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                cellValue = cell.getNumericCellValue();
                Double value = (double)cellValue;
                cellValue=value.intValue();
                break;
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                String result = cell.getStringCellValue();
                result = result.replaceAll(" ","");//空格去掉
                result = result.replaceAll("\"","");//引号去掉
                result = result.replaceAll("\'","");//引号去掉
                cellValue = result;
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                cellValue = cell.getCellFormula();
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                cellValue = "";
                break;
            default:
                cellValue = "";
                break;
        }
        return cellValue;
    }

    /**
     * dealSheet 理论上能够读取任何excel，后面针对excel的处理全部子类实现
     * @param objectList
     */
    protected abstract void dealWith(List<Map<String,Object>> objectList);

    /**
     * 优化入参
     * @param sheetItem
     */
    protected void dealWith(SheetItem sheetItem){
        System.out.println("not implements");
    }

}
