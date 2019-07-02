package com.wanma.demoes.ds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切点类 
 * @author libg 
 * @since 2018-12-10
 * @version 1.0
 **/
@Aspect
@Component
@Order(1)
public  class DataSourceAspect {
     private  static  final Logger logger = LoggerFactory.getLogger(DataSourceAspect. class);

     @Pointcut("@annotation(com.wanma.demoes.ds.DataSourceAspectService)")
     public  void serviceAspect() { }

    /** 
     * 前置通知 用于拦截service层
     * @param joinPoint 切点
     */  
    @Before("serviceAspect()")
    public  void doBefore(JoinPoint joinPoint) {
         try {
            //*========控制台输出=========*//
             logger.info("=====前置通知开始====="+"请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
             String dataSourceName = getServiceMthodTargetValue(joinPoint,2);
             logger.info("指定数据源:" +dataSourceName +"=====修改前的数据源====="+ DynamicDataSource.getDataSource());
             if(!"".equals(dataSourceName)){//
                 DynamicDataSource.contextHolder.set(dataSourceName);
                 return;
             }
             //获取使用注解的方法名称，根据名称切换数据源
             String methodName = joinPoint.getSignature().getName();
             //查询操作切换成从数据库
             if(methodName.contains("select")||methodName.contains("get")){
                 DynamicDataSource.contextHolder.set("slaveDB");
             }
//             else if(methodName.contains("insert")){//插入操作切换成主数据源
//                 DynamicDataSource.contextHolder.set("slaveDataSource");//DynamicDataSource.setDataSource("slaveDataSource");
//             }
             logger.info("=====修改后的数据源====="+ DynamicDataSource.getDataSource());
        }  catch (Exception e) {
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    @After("serviceAspect()")
    public  void doAfter(JoinPoint joinPoint) {
        DynamicDataSource.contextHolder.set("dataSource");
    }


    /**
     * 获取注解中注解的值  flag:1=description,2:dataSourceName
     * @param joinPoint 切点
     * @return 注解的值
     * @throws Exception
     */
    public  static String getServiceMthodTargetValue(JoinPoint joinPoint, int flag)throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String targetValue = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    if(flag == 1){
                        targetValue = method.getAnnotation(DataSourceAspectService. class).description();
                    }else{
                        targetValue = method.getAnnotation(DataSourceAspectService. class).dataSourceName();
                    }

                    break;
                }
            }
        }
        return targetValue;
    }
}