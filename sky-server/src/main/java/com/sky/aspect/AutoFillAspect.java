package com.sky.aspect;


import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sky.autoZhuJie.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j

public class AutoFillAspect {
	@Pointcut("execution((* com.sky.mapper.*.*(..)) && @anotation(com.sky.autoZhuJie.AutoFill)")
	public void AutoFillAspect() {
		
	}
	
	@Before("autoFillAspect")
	public void autoFill(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		OperationType value =  method.getAnnotation(AutoFill.class).value();
		Object [] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
		    return;
		}
		Object arg = args[0];

		LocalDateTime now = LocalDateTime.now();

		Long currentId = BaseContext.getCurrentId();
		if(value == OperationType.INSERT) {
		    try {
			    Method setCreateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
			    Method setCreateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
			    Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
			    Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
			    setCreateTime.invoke(arg, now);
			    setCreateUser.invoke(arg, currentId);
			    setUpdateTime.invoke(arg, now);
			    setUpdateUser.invoke(arg, currentId);
		    }catch (Exception e){
		    	e.printStackTrace();
		    }
		}else if (value == OperationType.UPDATE) {
			 try {
			    Method setUpdateTime = arg.getClass().getDeclaredMethod( AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
			    Method setUpdateUser = arg.getClass().getDeclaredMethod( AutoFillConstant.SET_UPDATE_USER, Long.class);

			    setUpdateTime.invoke(arg, now);
			    setUpdateUser.invoke(arg, currentId);
		    }catch (Exception e){
		    	e.printStackTrace();
		    }
		}
	}
}
