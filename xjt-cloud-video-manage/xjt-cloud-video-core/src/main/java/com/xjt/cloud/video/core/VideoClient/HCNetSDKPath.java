package com.xjt.cloud.video.core.VideoClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class HCNetSDKPath {
	public static String DLL_PATH;
	  static {
	    String path = (HCNetSDKPath.class.getResource("/").getPath()).substring(1).replace("/",
	        "\\");
	    try {
	      DLL_PATH = URLDecoder.decode(path, "utf-8");
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	  }
}
