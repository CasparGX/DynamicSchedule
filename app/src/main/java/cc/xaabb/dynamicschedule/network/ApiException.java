package cc.xaabb.dynamicschedule.network;

/**
 * Created by caspar on 16-10-25.
 */

public class ApiException extends RuntimeException {
    private int errorCode;

    public ApiException(int code, String msg) {
        super(msg);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
