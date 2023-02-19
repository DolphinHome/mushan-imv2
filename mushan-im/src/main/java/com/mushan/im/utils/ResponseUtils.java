package com.mushan.im.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

    public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        obj.put("data", data);
        return obj;
    }

    public static Object ok(String errmsg, Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", errmsg);
        obj.put("data", data);
        return obj;
    }

    public static Object fail() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", -1);
        obj.put("errmsg", "错误");
        return obj;
    }

    public static Object fail(int errno, String errmsg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", errno);
        obj.put("errmsg", errmsg);
        return obj;
    }

    public static Object badArgument() {
        return fail(401, "参数不对");
    }

    public static Object captcha() {
        return fail(402,"验证码错误");
    }

    public static Object username() {
        return fail(403,"用户名或密码错误");
    }

    public static Object badArgumentValue() {
        return fail(402, "参数值不对");
    }
    public static Object unlogin() {
        return fail(501, "请登录");
    }
    public static Object unauthz() {
        return fail(506, "无操作权限");
    }



}
