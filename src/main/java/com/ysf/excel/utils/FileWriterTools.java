package com.ysf.excel.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * @author Yeshufeng
 * @title
 * @date 2019/4/25
 */
public class FileWriterTools {

    public static void writeFile(String filePath,StringBuffer content){
        try {
            File writeFile = new File(filePath);
            boolean create = writeFile.createNewFile(); // 创建新文件

            BufferedWriter out = new BufferedWriter(new java.io.FileWriter(writeFile));

            out.write(content.toString());

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
