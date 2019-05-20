package com.ysf.excel.service;

import com.ysf.excel.entity.ClassTemplate;
import com.ysf.excel.entity.SheetItem;
import com.ysf.excel.entity.VariableTemplate;
import java.util.List;
import java.util.Map;

/**
 * @author Yeshufeng
 * @title 字段类型+字段值类型的API文档解析
 * @date 2019/5/20
 */
public class KeyValueExcelService extends ExcelTools {

    private final String FEILD_NAME = "Field";
    private final String PROPERTY_NAME = "Name";
    private final String FEILD_TYPE = "String";
    private final String DEFAULT_PACKAGE ="com.xx.entity.xx;";

    private JavaFileWriterService javaFileWriterService = new JavaFileWriterService();

    @Override
    protected void dealWith(List<Map<String, Object>> objectList) {

    }

    @Override
    protected void dealWith(SheetItem sheetItem) {

        List<Map<String,Object>> rowObjects = sheetItem.getRowObjectList();

        ClassTemplate classTemplate = new ClassTemplate();
        classTemplate.setClassName(firstCharUpper(sheetItem.getSheetName()));
        classTemplate.setPackageName("package"+DEFAULT_PACKAGE);
        rowObjects.forEach( item -> {
            VariableTemplate variableTemplate = new VariableTemplate();
            variableTemplate.setAnnotationValue(String.valueOf(item.get(PROPERTY_NAME)));
            variableTemplate.setType(FEILD_TYPE);
            variableTemplate.setVariable(String.valueOf(item.get(FEILD_NAME)));
            classTemplate.addVariable(variableTemplate);
        });

        javaFileWriterService.writeJavaFile(classTemplate);
    }




    /**
     * 首字大写
     * @param type
     * @return
     */
    private String firstCharUpper(String type) {
        char[] cs = type.toCharArray();
        if (cs[0] >= 97 && cs[0] <= 122) {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }
}
