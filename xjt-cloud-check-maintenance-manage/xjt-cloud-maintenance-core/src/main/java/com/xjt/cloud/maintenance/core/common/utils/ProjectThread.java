package com.xjt.cloud.maintenance.core.common.utils;


import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

public class ProjectThread extends Thread{
    private Object instance;
    private String methodName;
    @Autowired
    public ProjectThread(){}

    public ProjectThread(Object instance,String methodName){
        this.instance = instance;
        this.methodName = methodName;
    }
    @Override
    public void run(){
        try {
            Method get = instance.getClass().getMethod(methodName);
            get.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
