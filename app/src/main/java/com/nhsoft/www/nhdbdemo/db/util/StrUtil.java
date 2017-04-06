package com.nhsoft.www.nhdbdemo.db.util;

public class StrUtil {
    /** 
     * 把字符串的指定字母大写 
     */  
    public static String getUpperCharAt(String str, int index) {  
        String result = null;  
        int count = str.length();  
        if(count > index) {  
            String start = str.substring(0, index);  
            String at = (str.charAt(index) + "").toUpperCase();  
            String end = str.substring(index+1);  
            result = start + at + end;  
        }  
        return result;  
    }  
      
    /** 
     * 把字符串中的特殊字符转义符转换回特殊字符 
     */  
    public static String specialFormat(String str) {  
        str = str.replace("\"", "<syh>");  
        str = str.replace("\r\n", "<hhf>");  
        str = str.replace("\\", "<xg>");  
        return str;  
    }  
      
    public static String specialUnFormat(String str) {  
        str = str.replace("<syh>", "\"");  
        str = str.replace("<hhf>", "\r\n");  
        str = str.replace("<xg>", "\\");  
        return str;  
    }  
} 