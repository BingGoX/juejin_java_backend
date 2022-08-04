package com.tsinghualei.juejin.common.util;

public class StringUtil {
    public static boolean isNullOrEmpty(String s){
        return s == null || s.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String s){
        return s != null && !s.isEmpty();
    }
}
