package com.ysf.excel.service;

import com.google.common.base.CaseFormat;
import com.ysf.excel.entity.ClassTemplate;
import com.ysf.excel.utils.FileWriterTools;

/**
 * @author Yeshufeng
 * @title
 * @date 2019/4/29
 */
public class JavaFileWriterService {

    private final String ROOT_PATH = "/Users/xx/code/github-work/easy-excel/src/main/resources/code/";

    private final String IMPORT_PREFIX = "import java.util.List;\nimport lombok.Data;\nimport io.swagger.annotations.ApiModel;\nimport io.swagger.annotations.ApiModelProperty;\nimport java.io.Serializable;\ncom.xx.util.annotation.JacksonProperty;\n";

    private final String CLASS_HEADER = "/** \n"
            + "* Auto generate \n"
            + "*/\n"
            + "@Data\n"
            + "@ApiModel\n";

    public void writeJavaFile(ClassTemplate classTemplate) {


        StringBuffer content = new StringBuffer();
        content.append(classTemplate.getPackageName());
        content.append(IMPORT_PREFIX);

        content.append(CLASS_HEADER)
                .append("public class ").append(classTemplate.getClassName()).append(" implements Serializable{\n\r");


        classTemplate.getVariableTemplates().forEach(v -> {
            String underScoreName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, v.getVariable());
            content.append("\t@ApiModelProperty(value=\"").append(v.getAnnotationValue()).append("\")\n")
                    .append("\t@JacksonProperty(\"").append(v.getVariable()).append("\")\n")
                    .append("\tprivate ").append(v.getType()).append(" ").append(underScoreName).append(";\n").append("\n");
        });
        content.append("}");
        FileWriterTools.writeFile(ROOT_PATH + classTemplate.getClassName() + ".java", content);

    }

}
