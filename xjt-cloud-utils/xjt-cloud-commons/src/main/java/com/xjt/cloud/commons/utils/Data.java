package com.xjt.cloud.commons.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 19:13
 * @Description:
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data<T extends BaseEntity> {
    private int status;

    private String msg;

    private T obj;

    private Integer totalCount;

    private List<T> listObj;

    private List<Object> objects;

    private Object object;

    public Data(){

    }

    public Data(int status){
        this.status = status;
    }
    public Data(int status, List<T> listObj){
        this.status = status;
        this.listObj = listObj;
    }
    public Data(int status, Integer totalCount, List<T> listObj){
        this.status = status;
        this.totalCount = totalCount;
        this.listObj = listObj;
    }
    public Data(int status, String msg, List<T> listObj){
        this.status = status;
        this.msg = msg;
        this.listObj = listObj;
    }

    public Data(int status, T obj){
        this.status = status;
        this.obj = obj;
    }
    public Data(int status, String msg){
        this.status = status;
        this.msg = msg;
    }
    public Data(int status, String msg, T obj){
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public static Data isFail(){
        return new Data(Constants.FAIL_CODE);
    }

    public static Data isSuccess(){
        return new Data(Constants.SUCCESS_CODE);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public List<T> getListObj() {
        return listObj;
    }

    public void setListObj(List<T> listObj) {
        this.listObj = listObj;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }
}
