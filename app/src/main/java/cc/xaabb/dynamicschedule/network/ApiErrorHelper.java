package cc.xaabb.dynamicschedule.network;

import android.content.Context;

import java.io.IOException;

import cc.xaabb.dynamicschedule.utils.Debug;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by caspar on 16-10-25.
 */

public class ApiErrorHelper {

    public static void handleCommonError(Context context, Throwable e, BaseSubscriber baseSubscriber) {
        if (e instanceof HttpException) {
            //Toast.makeText(context, "服务暂不可用", Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
            //Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ApiException) {
            //ApiException处理
            Debug.e("ApiException",e.getMessage());
            baseSubscriber.onApiException((ApiException)e);
        } else {
            //Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
            Debug.e("ApiErrorHelper", e.getMessage()+" ");
        }
    }

}
