package com.tsinghualei.juejin.controller;

public class BaseUtil {
    public static String generateCode(int count) {
        String code = (Math.random() + "").substring(2, 2 + count);
        return code;
    }

}
