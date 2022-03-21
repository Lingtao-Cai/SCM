package com.aowin.scm.pojo;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

//    private Result() {
//    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.setCode(200);
        r.setMsg("成功");
        r.setData(data);
        return r;
    }

    public static Result ok(Object data, String msg) {
        Result r = new Result();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result ok() {
        return ok(null);
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.setCode(500);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }

    public static Result error() {
        return error("服务器错误");
    }


}
