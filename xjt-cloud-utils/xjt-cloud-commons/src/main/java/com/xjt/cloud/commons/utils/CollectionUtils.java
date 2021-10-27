package com.xjt.cloud.commons.utils;


import java.util.*;


@SuppressWarnings("unchecked")
public class CollectionUtils {


	/**
	 * 判断集合是否为空，如果集合没有元素返回真，如果有NULL元素，返回FALSE
	 * 否则返假
	 * 2008-8-19
	 * @author yaoyuan
	 * @param coll
	 * @return
	 */
	public static boolean isEmpty(Collection coll){
		return coll == null || coll.isEmpty();
	}
	
	/**
	 * 判断MAP是否为空，如果集合没有元素返回真  或都每个元素都是空，
	 * 否则返假
	 * 2008-8-19
	 * @author yaoyuan
	 * @param src
	 * @return
	 */
	public static boolean isEmpty(Map src){
		if(src==null)
			return true;
		return src.isEmpty();
	}
	
	/**
	 * 与isEmpty(Map)相反
	 * 2008-8-19
	 * @author yaoyuan
	 * @param src
	 * @return
	 */
	public static boolean isNotEmpty(Map src){
		return !isEmpty(src);
	}
	
	/**
	 * 集合是否至少有一个非空元�?
	 * 2008-8-19
	 * @author yaoyuan
	 * @param src
	 * @return
	 */
	public static boolean hasNotNullElement(Collection src){
		if(isEmpty(src))
			return false;
		for (Object object : src) {
			if(object!=null)
				return true;
		}
		return false;
	}
	
	
	/**
	 * 是否�?��元素为空或没有元素或集合为空
	 * 2008-8-19
	 * @author yaoyuan
	 * @param src
	 * @return
	 */
	public static boolean isAllElementsNull(Collection src){
		return !hasNotNullElement(src);
	}
	
	
	/**
	 * 与isEmpty方法相反
	 * 2008-8-19
	 * @author yaoyuan
	 * @param src
	 * @return
	 */
	public static boolean isNotEmpty(Collection src){
		return !isEmpty(src);
	}
	
	/**
	 * 只要有一个非空项即返回FALSE
	 * 2008-10-24
	 * @author yaoyuan
	 * @param src
	 * @return
	 */
	public static boolean isEmpty(Object[] src){
		if(src==null)
			return true;
		if(src.length==0)
			return true;
		for (int i=0; i<src.length;i++) {
			if(src[i]!=null)
				return false;
		}
		return true;
	}
	
	public static boolean isNotEmpty(Object[] src){
		return !isEmpty(src);
	}
	
	/**
	 * 将数组的内容分解
	 * 2008-10-09
	 * @author 黄豆�?
	 * @param obj
	 * @return Object
	 */
	public static Object fromArrayToObject(Object obj){
		StringBuffer stringBuffer=new StringBuffer();
		if(obj != null){
			if(obj instanceof Object[]){
				if(isNotEmpty(((Object[])obj))){
					for(Object object:(Object[])obj){
						stringBuffer.append(object);
						}
					return stringBuffer;
				}
			}
		}
		return obj;
	}

	/**
	 * 复制集合数据
	 * 2008-12-3
	 * @author yaoyuan
	 * @param src 原集�?
	 * @param from �?
	 * @param to �?
	 * @return
	 */
	public static List copySetSelection(Set src, int from, int to) {
		if(src==null){
			return null;
		}
		List result = new ArrayList(to-from);
		int i = 0;
		for (Object object : src) {
			if(i>=from && i<to){
				result.add(object);
			}
			if(i>=to){
				return result;
			}
			i++;
		}
		return result;
	}
	
	
	/**
	* 只要有一个非空与不等于空白项即返回FALSE
	* Dec 6, 2008
	* @author 黄豆�?
	* @param src
	* @return
	*/
	public static boolean isBlank(Object[] src) {
		if (src == null)
			return true;
		if (src.length == 0)
			return true;
		for (int i = 0; i < src.length; i++) {
			if (src[i] != null && src[i] != "")
				return false;
		}
		return true;
	}

	/**
	 * 判断字符串数组是否至少有�?��不为空并且不是空字符�?
	 * 2009-1-4
	 * @author yaoyuan
	 * @param propValue
	 * @return
	 */
	public static boolean isStringArrayEmpty(String[] propValue) {
		if(propValue==null)
			return true;
		if(propValue.length==0)
			return true;
		for (int i = 0; i < propValue.length; i++) {
			if(StringUtils.isNotEmpty(propValue[i])){
				return false;
			}
		}
		return true;
	}

}
