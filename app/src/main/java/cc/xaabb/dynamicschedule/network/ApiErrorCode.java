package cc.xaabb.dynamicschedule.network;

/**
 * Created by caspar on 16-10-25.
 */

public interface ApiErrorCode {
    /** hash码错误*/
    int ERROR_BAD_HASH = -1;
    /** 密码错误*/
    int ERROR_PASSWORD = 1;
    /** 请求超时*/
    int ERROR_REQUEST_TIME_OUT = 2;
    /** 教务系统超时*/
    int ERROR_JWXT_TIME_OUT = 3;
    /** 未知错误*/
    int ERROR_UNKNOW = 4;
    /** 无此学生*/
    int ERROR_NO_SUCH_STUDENT = 5;
    /** 已续借*/
    int ERROR_ACTIONED = 6;
    /** 无此图书号*/
    int ERROR_BOOK_ID = 7;
    /** 无剩余节假日*/
    int ERROR_NO_HOLIDAY = 8;
    /** 缺少参数*/
    int ERROR_NO_PARAM = 65535;
    /** 没有网络*/
    int ERROR_NO_INTERNET = -2;

}
