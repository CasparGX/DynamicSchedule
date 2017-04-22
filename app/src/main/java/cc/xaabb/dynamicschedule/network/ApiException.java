package cc.xaabb.dynamicschedule.network;

/**
 * Created by caspar on 16-10-25.
 */

public class ApiException extends RuntimeException {
    private int errorCode;
    private String msg;

    public ApiException(int code, String msg) {
        super(msg);
        this.errorCode = code;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }
    public String getMsg() {
        return msg;
    }

}
