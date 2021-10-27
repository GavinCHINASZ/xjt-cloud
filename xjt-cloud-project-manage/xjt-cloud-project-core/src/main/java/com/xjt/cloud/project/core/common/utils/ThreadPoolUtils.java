package com.xjt.cloud.project.core.common.utils;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池初使化类
 * @author zhiwen.wang
 * @date 2011-12-27
 */
public class ThreadPoolUtils {
	private ThreadPoolUtils(){}

	private static class SingletonThreadPool{
		/** 线程池对象
		 *  最小线程数
		 *  最大线程数
		 *  当线程有60秒没有运行时就注销
		 *  注销值的单位（秒）
		 *  缓冲队列数，当队列数已满时，创建一个新线程
		 *  当线程已满，并且缓存也已满时，对于新请求，做重复请求
		 */
		public static ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 200, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
	}
	
	/**
	 * 初使化线程池方法
	 * @author zhiwen.wang
	 * @date 2011-12-27
	 */
	public static ThreadPoolExecutor getInstance(){
		return SingletonThreadPool.pool;
	}
}
