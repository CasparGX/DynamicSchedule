package cc.xaabb.dynamicschedule.module.user;

import android.content.Context;
import android.util.Log;

import cc.xaabb.dynamicschedule.base.BasePresenter;
import cc.xaabb.dynamicschedule.model.Result;
import cc.xaabb.dynamicschedule.model.UserModel;
import cc.xaabb.dynamicschedule.network.ApiException;
import cc.xaabb.dynamicschedule.network.ApiService;
import cc.xaabb.dynamicschedule.network.BaseObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        UserModel mUserModel = new UserModel();
        mUserModel.setUsername(username);
        mUserModel.setPassword(password);
        mUserModel.setId(110);
        mUserModel.setNickname("nickname");
        mApiService.postUserRegister(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Result<UserModel>>() {

                    @Override
                    public void onNext(Result<UserModel> mResult) {
                        UserModel mUserModel = mResult.getData();
                        Log.d(TAG, "onNext: "+mUserModel);

                    }


                    @Override
                    public void onApiException(ApiException e) {
                        mLoginView.login(e.getMsg());

                        Log.d(TAG, "onApiException: "+e.getMsg());
                    }
                });
    }
}
