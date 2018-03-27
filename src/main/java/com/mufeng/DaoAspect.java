package com.mufeng;

import org.apache.commons.beanutils.MethodUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//标识为spring自动配置类
@Configuration
@Aspect
//保证切数据源在事务之前执行
@Order(-1)
public class DaoAspect {
    @Autowired
    private Environment environment;

    //指定切面为com.alibaba.~~~.dao包下的方法，约定大于配置！
    //支持事务
    @Pointcut("execution(* com.mufeng.dao..*.*(..)) || @within(org.springframework.transaction.annotation.Transactional) || @annotation(org.springframework.transaction.annotation.Transactional)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Class clazz = signature.getDeclaringType();
        Object[] args = joinPoint.getArgs();
        Class[] classes = new Class[0];
        classes = Stream.of(args).map(x -> x == null ? null : x.getClass()).collect(Collectors.toList()).toArray(classes);
        Method accessibleMethod = MethodUtils.getMatchingAccessibleMethod(clazz, joinPoint.getSignature().getName(), classes);
        Use annotation = accessibleMethod.getAnnotation(Use.class);
        if(annotation != null) {
            RoutingDataSource.setDataSource(getDataSource(annotation));
            return;
        }
        annotation = (Use) clazz.getAnnotation(Use.class);
        if(annotation != null) {
            RoutingDataSource.setDataSource(getDataSource(annotation));
            return;
        }
        RoutingDataSource.setDataSource(null);
    }

    private DataSourceEnum getDataSource(Use use) {
        if(StringUtils.isEmpty(use.dataSource())) {
            return use.value();
        }
        String dataSourceName = use.dataSource().trim();
        //支持在spring配置文件中指定数据源
        if(!dataSourceName.startsWith("${") || !dataSourceName.endsWith("}")) {
            throw new IllegalArgumentException("dataSourceName定义错误，错误的key为{0}，正确的格式为：${XXX}");
        }
        dataSourceName = dataSourceName.substring(2, dataSourceName.length() - 1);
        String property = environment.getProperty(dataSourceName);
        return DataSourceEnum.valueOf(property);
    }
}
