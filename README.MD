# 简介

## easy-excel

读取excel，入口为ExcelToolsTest.java中的main方法，filePath的值为输入数据

数据结构封装为SheetItem

继承ExcelTools 实现dealWith方法 处理从excel中读取的数据


## 一些说明

### 数据结构

| 类型 | 描述 | 
| ------ | ------ | 
| List<Map<String,Object>> rowObjectList | key-value形式的映射，key为filePath中对应excel文档的标题,value是标题下对应的值 |

