//package cc.xaabb.dynamicschedule.network;
//
//import android.content.Context;
//
//import rx.Subscriber;
//
///**
// * Created by caspar on 16-10-25.
// */
//
//public abstract class BaseSubscriber<T> extends Subscriber<T> {
//    private Context mContext;
//
//    public BaseSubscriber() {
//    }
//
//    public BaseSubscriber(Context context) {
//        mContext = context;
//    }
//
//    @Override
//    public void onStart() {
//        //请求开始之前，检查是否有网络。无网络直接抛出异常
//        //另外，在你无法确定当前代码运行在什么线程的时候，不要将UI的相关操作放在这里。
//        /*if (!Device.hasInternet()) {
//            this.onError(new ApiException(ApiErrorCode.ERROR_NO_INTERNET, "network interrupt"));
//            return;
//        }*/
//
//    }
//
//
//    @Override
//    public void onCompleted() {
//
//    }
//
//    @Override
//    public void onError(Throwable e) {
//        ApiErrorHelper.handleCommonError(mContext, e, this);
//    }
//
//    @Override
//    public void onNext(T t) {
//
//    }
//
//    public abstract void onApiException(ApiException e);
//}
