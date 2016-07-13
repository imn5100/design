package com.shaw.utils;

import org.apache.commons.beanutils.ConvertUtilsBean;

public class StrictConvertUtils extends ConvertUtilsBean {
    private static StrictConvertUtils Instance = new StrictConvertUtils();
    
    private StrictConvertUtils() {
        register(true, false, 0);
    }
    
    public static StrictConvertUtils getInstance() {
        return Instance;
    }
    
}
