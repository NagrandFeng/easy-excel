package com.ysf.excel.service;


/**
 * @author Yeshufeng
 * @title
 * @date 2017/11/30
 */
public class ExcelToolsTest {

    final static String filePath = "/Users/xx/Documents/api_excel_data.xlsx";

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        ExcelTools tools = new KeyValueExcelService();
        tools.dealExcel(filePath);
    }

}
