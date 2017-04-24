package cc.xaabb.dynamicschedule.module.user;

import android.content.Context;
import android.util.Log;

import cc.xaabb.dynamicschedule.base.BasePresenter;
import cc.xaabb.dynamicschedule.model.Result;
import cc.xaabb.dynamicschedule.model.UserModel;
import cc.xaabb.dynamicschedule.network.ApiException;
import cc.xaabb.dynamicschedule.network.ApiService;
import cc.xaabb.dynamicschedule.network.BaseSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Caspar on 2017/4/22.
 */

public class LoginPresenter extends BasePresenter {
    private String TAG = "LoginPresenter";
    private ApiService mApiService;
    private LoginView mLoginView;
    private Context mContext;
    public LoginPresenter(LoginView mLoginView) {
        this.mLoginView = mLoginView;
        mApiService = mRetrofit.create(ApiService.class);
    }

    public void register(String username, String password) {
        mApiService.postUserRegister(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result<UserModel>>() {

                    @Override
                    public void onNext(Result<UserModel> mResult) {
                        UserModel mUserModel = mResult.getData();
                        Log.d(TAG, "onNext: "+mUserModel);

                    }


                    @Override
                    public void onApiException(ApiException e) {
                        mLoginView.loginFail(e.getMsg());
//
                        Log.d(TAG, "onApiException: "+e.getMsg());
                    }
                });
    }

    public void login(String username, String password) {

        mApiService.postUserLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result<UserModel>>() {

                    @Override
                    public void onNext(Result<UserModel> mResult) {
                        UserModel mUserModel = mResult.getData();
                        mLoginView.loginSuccess(mUserModel);
                        Log.d(TAG, "onNext: "+mUserModel);

                    }


                    @Override
                    public void onApiException(ApiException e) {
                        mLoginView.loginFail(e.getMsg());
//
                        Log.d(TAG, "onApiException: "+e.getMsg());
                    }
                });
    }

    public void getUser(int id) {
        mApiService.getUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result<UserModel>>() {

                    @Override
                    public void onNext(Result<UserModel> mResult) {
                        UserModel mUserModel = mResult.getData();
                        Log.d(TAG, "onNext: "+mUserModel);

                    }


                    @Override
                    public void onApiException(ApiException e) {
//                        mLoginView.login(e.getMsg());
//
//                        Log.d(TAG, "onApiException: "+e.getMsg());
                    }
                });
    }
}
