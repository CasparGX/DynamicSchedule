//package cc.xaabb.dynamicschedule.network;
//
//import android.content.Context;
//
//import io.reactivex.Observer;
//import io.reactivex.disposables.Disposable;
//
///**
// * Created by Caspar on 2017/4/23.
// */
//
//public abstract class BaseObserver<T> implements Observer<T> {
//    private Context mContext;
//    public BaseObserver(Context context) {
//        mContext = context;
//    }
//    public BaseObserver() {
//    }
//    @Override
//    public void onSubscribe(Disposable d) {
//
//    }
//
//    @Override
//    public void onNext(T value) {
//
//    }
//
//    @Override
//    public void onError(Throwable e) {
//        ApiErrorHelper.handleCommonError(mContext, e, this);
//    }
//
//    @Override
//    public void onComplete() {
//
//    }
//
//    public abstract void onApiException(ApiException e);
//}
