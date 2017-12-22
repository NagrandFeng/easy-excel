package com.ysf.excel.service;

import com.ysf.excel.entity.TableDTO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yeshufeng
 * @title
 * @date 2017/11/30
 */
public class ExcelToolsTest {

    final static String filePath = "/Users/nagrandyf/Documents/work_in_zbj/jiaoyi/php事件转java/外部mq列表.xlsx";
    static ExcelTools excelTools = new ExcelTools();

    public static void main(String[] args) {
        run();

    }

    public static void runGenerateINsert(ExcelTools excelTools) {
//        String sql = excelTools.generateInsertSQL(filePath);
//        System.out.println(sql);
    }

    public static void runGenerateUpdate(ExcelTools excelTools) {
//        String sql = excelTools.generateUpdateSQL(filePath);
//        System.out.println(sql);
    }

    public static void run() {
        TableDTO tableDTO = new TableDTO();
        tableDTO.setFilePath(filePath);
        tableDTO.setTableName("mk_trade_mq");
        tableDTO.setPrimaryKeyName("user_id");
        tableDTO.setPrimaryKeyValue("123");

        ExcelTools tools = new ExcelTools();
        tools.generateInsertSQL(tableDTO);
    }

    public static void testRegex(){
        String url = "(yeshufeng1,t1,1.0,),(yeshufeng2,t3,2.0,),(yeshufeng3,t3,3.0,),(yeshufeng4,t4,4.0,),";
        char[] arr = new char[100];
        for (int i =0;i<url.length();i++){
            arr[i] = url.charAt(i);
        }
        String str = "da jia hao,ming tian bu fang jia!";

        String regex = "[\\,\\)]{2}";
        String result = url.replaceAll(regex,")");
        System.out.println("result: "+result);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        System.out.println(url);
        while (m.find()) {
            System.out.println(m.group());//获取匹配的子序列

            System.out.println(m.start() + ":" + m.end());

            System.out.println(arr[m.start()]+":"+arr[m.end()]);
        }
    }
}
