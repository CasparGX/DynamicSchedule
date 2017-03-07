package cc.xaabb.dynamicschedule.model;

/**
 * Created by caspar on 16-10-25.
 */

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public boolean isOk() {
        return this.code==0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    //...set和get方法
}
