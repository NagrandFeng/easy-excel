package com.ysf.excel.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ysf.excel.entity.TableDTO;
import com.ysf.excel.utils.SqlConstant;
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
 * @title excel生成sql
 * @date 2017/11/30
 */
public class ExcelTools {

    public void generateInsertSQL(TableDTO tableDTO) {

        String filePath = tableDTO.getFilePath();

        File file = new File(filePath);
        //读excel
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Workbook workBook = new XSSFWorkbook(bis);
            readSheet(workBook,tableDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //生成sql-可能带点IO的操作了

    }

    private void readSheet(Workbook workbook,TableDTO tableDTO){

        Integer sheetIndex = workbook.getNumberOfSheets();

        for (int i = 0; i < sheetIndex; i++) {

            Sheet sheet = workbook.getSheetAt(i);
            int rowNumbers = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(0);

            Map<Integer,String> titleMap = readeFirstRow(firstRow);

            List<Map<String,Object>> objectList = Lists.newArrayList();

            for (int j = 0; j < rowNumbers; j++) {
                Row row = sheet.getRow(j);
                Map<String,Object> rowObject = readOneRow(row,titleMap);
                objectList.add(rowObject);
            }

            //每个sheet的打印操作
            mapList2SqlList(objectList,tableDTO);

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

    private Map<String,Object> readOneRow(Row row,Map<Integer,String> titleMap){
        Map<String,Object> rowObject = Maps.newHashMap();
        Map<Integer,Object> rowObjectWithIndex = Maps.newHashMap();

        for (int i = 0;i < row.getPhysicalNumberOfCells();i++){
            Object cellValue = readOneCell(row.getCell(i));
            rowObjectWithIndex.put(i,cellValue);
        }
        for (Map.Entry<Integer,String> entry : titleMap.entrySet()) {
            Integer index = entry.getKey();
            String fieldName = entry.getValue();
            Object fieldValue = rowObjectWithIndex.get(index);
            rowObject.put(fieldName,fieldValue);
        }
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
                cellValue = "\""+cell.getStringCellValue()+"\"";
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

    private void mapList2SqlList(List<Map<String,Object>> objectList,TableDTO tableDTO){
        String sql = SqlConstant.INSERT.replace("#{table}",tableDTO.getTableName());

        StringBuilder fields = new StringBuilder("");
        StringBuilder values = new StringBuilder("");
        for (int i = 0; i < objectList.size(); i++) {
            Map<String,Object> sqlObject = objectList.get(i);
            if(i>0){
                values.append("(");
            }
            for (Map.Entry<String,Object> entry: sqlObject.entrySet()) {
                if(i == 0){
                    //因为第一行是title
                    String fieldName = entry.getKey();
                    fields.append(fieldName);
                    fields.append(",");
                }else{
                    Object fieldValue = entry.getValue();
                    values.append(fieldValue);
                    values.append(",");
                }

            }
            if(i>0){
                values.append("),");
            }
        }
        //括号前面的逗号去掉
        String regex = "[\\,\\)]{2}";
        String afterRegex = values.toString().replaceAll(regex,")");
        afterRegex = afterRegex.substring(0,afterRegex.length()-1);
        String fieldsStr = fields.toString().substring(0,fields.length()-1);
        sql = sql.replace("#{fields}",fieldsStr);
        sql = sql.replace("#{values}",afterRegex);

        //

    }




    public void generateUpdateSQL(String filePath){

    }
}
