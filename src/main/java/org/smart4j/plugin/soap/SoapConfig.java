package org.smart4j.plugin.soap;

import org.smart4j.framework.core.ConfigHelper;

/**
 * 从配置文件中获取相关属性
 * Created by Administrator on 2017/5/2 0002.
 */
public class SoapConfig {
    public static boolean isLog(){
        return ConfigHelper.getBoolean(SoapConstant.LOG);
    }
}
