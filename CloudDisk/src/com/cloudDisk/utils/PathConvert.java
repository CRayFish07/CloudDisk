package com.cloudDisk.utils;

import org.apache.struts2.ServletActionContext;

public  class PathConvert {
	/**
	 * ����·��תΪ���·��
	 * @param absolutePath
	 * @return
	 * 
	 */
  public static String convertpath(String absolutePath){
	  String fileName=absolutePath.substring(absolutePath.lastIndexOf("\\"));// �õ���/�ļ����� \lession4.pptx

	  absolutePath=absolutePath.substring(0,absolutePath.lastIndexOf("\\"));

	  String user=absolutePath.substring(absolutePath.lastIndexOf("\\")); // �õ���/�û����� \zhangjian

	  return "\\upload"+user+fileName ;
  }


}
