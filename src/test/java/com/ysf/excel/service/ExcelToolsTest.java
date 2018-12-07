package com.ysf.excel.service;


/**
 * @author Yeshufeng
 * @title
 * @date 2017/11/30
 */
public class ExcelToolsTest {

    final static String filePath = "/Users/XX/Documents/pojo.xlsx";

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        TemplateEntityService tools = new TemplateEntityService();
        tools.dealExcel(filePath);
    }

}
