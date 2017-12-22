package com.ysf.excel.utils;

/**
 * @author Yeshufeng
 * @title SQL语句常量
 * @date 2017/11/30
 */
public class SqlConstant {
    public static String INSERT = "insert into #{table}(#{fields}) values#{values}";
    public static String UPDATE_PRE = "update #{table} set ";
    public static String UPDATE_FIELD = "#{key}=#{value} ";
    public static String UPDATE_SUF = "where #{pk}=#{pk_value}";
}
