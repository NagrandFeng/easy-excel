package com.ysf.excel.service;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.ysf.excel.entity.TemplateEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Yeshufeng
 * @title 读excel生成Java pojo，带上swagger的@ApiModelProperty
 * @date 2018/12/7
 */
public class TemplateEntityService extends ExcelTools {

    private static final String templateAPiProperty = "@ApiModelProperty(value = \"#{value}\", example = \"#{example}\")";

    private static final String fieldDeclare = "#{permission} #{fieldType} #{fieldName};";

    @Override
    protected void dealWith(List<Map<String, Object>> objectList) {
        List<TemplateEntity> entityList = Lists.newArrayList();
        //转对象
        objectList.forEach(item -> {
            TemplateEntity entity = new TemplateEntity();
            entity.setFieldType(String.valueOf(item.get("fieldType")));
            entity.setFieldName(String.valueOf(item.get("fieldName")));
            entity.setApiPropertyValue(String.valueOf(item.get("apiPropertyValue")));
            entity.setApiPropertyExample(String.valueOf(item.get("apiPropertyExample")));
            entityList.add(entity);
        });

        System.out.println();


        StringBuilder sb= new StringBuilder();
        //生成java头
        entityList.forEach( entity ->{
            appendApiModel(sb,entity.getApiPropertyValue(),entity.getApiPropertyExample());
            appendFiled(sb,entity.getPermission(),entity.getFieldType(),entity.getFieldName());
            sb.append("\r\n");
        });

        //生成.java文件
        try {
            String javaFilePath = "/Users/XX/Documents/demo.java";

            File file = new File(javaFilePath);
            if (!file.exists()) {	//文件不存在则创建文件，先创建目录
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);	//文件输出流用于将数据写入文件
            outStream.write(sb.toString().getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void appendFiled(StringBuilder sb,String permission,String type,String name){
        //name转驼峰
        String underScoreName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
        sb.append(permission).append(" ").append(type).append(" ").append(underScoreName).append(";").append("\r\n");
    }

    private void appendApiModel(StringBuilder sb,String value,String example){
        sb.append("@ApiModelProperty(value = \"").append(value).append("\", example = \"").append(example).append("\")").append("\r\n");
    }
}
