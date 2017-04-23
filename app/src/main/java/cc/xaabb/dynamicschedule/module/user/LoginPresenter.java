package cc.xaabb.dynamicschedule.module.user;

import android.content.Context;
import android.util.Log;

import cc.xaabb.dynamicschedule.base.BasePresenter;
import cc.xaabb.dynamicschedule.model.HolidayNextModel;
import cc.xaabb.dynamicschedule.model.Result;
import cc.xaabb.dynamicschedule.model.UserModel;
import cc.xaabb.dynamicschedule.network.ApiErrorCode;
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

    public void getHolidayNext(){
        Log.d(TAG, "getHolidayNext: >>>>>>>>>>>>>>>>>>");
        mApiService.getHolidayNext()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<HolidayNextModel>() {

                    @Override
                    public void onNext(HolidayNextModel holidayNextModel) {
                        HolidayNextModel _holidayNextModel = holidayNextModel;
                        //holidayView.finishGetHolidayNext(_holidayNextModel);
                        Log.d(TAG, "onNext: "+_holidayNextModel.toString());
                    }

                    @Override
                    public void onApiException(ApiException e) {
                        if (e.getErrorCode()== ApiErrorCode.ERROR_NO_HOLIDAY) {
                            e.printStackTrace();
                        }
                    }

                });

        /*Call<HolidayNextModel> call = apiService.getHolidayNext();
        call.enqueue(new Callback<HolidayNextModel>() {
            @Override
            public void onResponse(Response<HolidayNextModel> response, Retrofit retrofit) {
                int code = response.body().getCode();
                if (code == 0) {
                    HolidayNextModel holidayNextModel = response.body();
                    holidayNextModel.setCache();
                    holidayView.finishGetHolidayNext(holidayNextModel);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                holidayView.finishGetHolidayNext(null);
            }
        });*/
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
