package com.ysf.excel.entity;

import lombok.Data;

/**
 * @author Yeshufeng
 * @title
 * @date 2018/12/7
 */
@Data
public class TemplateEntity {

    private String permission = "private";

    private String fieldType;

    private String fieldName;

    private String apiPropertyValue;

    private String apiPropertyExample;

}
