package org.smart4j.plugin.soap;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.smart4j.framework.ioc.BeanHelper;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import java.util.Set;

/**
 * soap servlet
 * Created by Administrator on 2017/5/2 0002.
 */
@WebServlet(urlPatterns = SoapConstant.SERVLET_URL,loadOnStartup = 0)
public class SoapServlet extends CXFNonSpringServlet {
    @Override
    protected void loadBus(ServletConfig sc){
        //初始化CXF总线
        super.loadBus(sc);
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);
        //发布soap服务
        publishSoapService();
    }
    private void publishSoapService(){
        //遍历所有标注了SOAP注解的类
        Set<Class<?>> soapClassSet = ClassHelper.getClassSetByAnnotation(Soap.class);
        if (CollectionUtil.isNotEmpty(soapClassSet)){
            for (Class<?>soapClass:soapClassSet){
                //获取soap地址
                String address = getAddress(soapClass);
                //获取soap类的接口
                Class<?> soapInterfaceClass = getSoapInterfaceClass(soapClass);
                //获取soap类的实例
                Object soapInstance = BeanHelper.getBean(soapClass);
                //发布soap服务
                SoapHelper.publishService(address,soapInterfaceClass,soapInstance);
            }
        }
    }
    private Class<?> getSoapInterfaceClass(Class<?>soapClass){
        //获取soap实现类的第一个接口作为soap服务接口
        return soapClass.getInterfaces()[0];
    }
    private String getAddress(Class<?>soapClass){
        String address;
        //若soap注解的value属性不为空,则获取当前值,否则获取类名
        String soapValue = soapClass.getAnnotation(Soap.class).values();
        if (StringUtil.isNotEmpty(soapValue)){
            address = soapValue;
        }else{
            address = getSoapInterfaceClass(soapClass).getSimpleName();
        }
        //确保最前面只有一个
        if (!address.startsWith("/")){
            address = "/" + address;
        }
        address = address.replaceAll("\\/+","/");
        return address;
    }
}
