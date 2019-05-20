package com.ysf.excel.entity;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Data;

/**
 * @author Yeshufeng
 * @title
 * @date 2019/4/24
 */
@Data
public class ClassTemplate {

    private String className;

    private String packageName;

    private List<VariableTemplate> variableTemplates = Lists.newArrayList();

    public boolean addVariable(VariableTemplate e){
        return variableTemplates.add(e);
    }

}
