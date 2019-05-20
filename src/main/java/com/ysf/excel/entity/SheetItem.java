package com.ysf.excel.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author Yeshufeng
 * @title
 * @date 2019/5/20
 */
@Data
public class SheetItem {

    private List<Map<String,Object>> rowObjectList = Lists.newArrayList();

    private String sheetName;

}
