package com.imlianai.zjdoll.app.modules.publics.qiniu.pili;


@SuppressWarnings("serial")
public class PiliException extends Exception {
    private String message;
    private  int code;


    public PiliException(String msg) {
        super(msg);
        this.message = msg;
    }

    public PiliException(Exception e) {
        super(e);
        this.message= null;
    }

    public int code() {
        return code == 0 ? -1 : code;
    }

    public boolean isDuplicate() {
        return code() == 614;
    }

    public boolean isNotFound() {
        return code() == 612;
    }

    public boolean isNotInLive() {
        return code() == 619;
    }

    public String getMessage() {
        return message;
    }

    class ErrorResp {
        String error;
    }

}
