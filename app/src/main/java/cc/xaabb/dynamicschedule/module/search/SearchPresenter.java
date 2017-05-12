package cc.xaabb.dynamicschedule.module.search;

import android.util.Log;

import java.util.List;

import cc.xaabb.dynamicschedule.base.BasePresenter;
import cc.xaabb.dynamicschedule.model.CourseList;
import cc.xaabb.dynamicschedule.model.Result;
import cc.xaabb.dynamicschedule.model.Schedule;
import cc.xaabb.dynamicschedule.network.ApiException;
import cc.xaabb.dynamicschedule.network.ApiService;
import cc.xaabb.dynamicschedule.network.BaseSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 63024 on 2017/5/9 0009.
 */

public class SearchPresenter extends BasePresenter {
    private String TAG = "SearchPresenter";
    private ApiService mApiService;
    private SearchView mSearchView;
    public SearchPresenter(SearchView searchView) {
        mApiService = mRetrofit.create(ApiService.class);
        mSearchView = searchView;
    }

    public void searchByCity(String city) {
        mApiService.getScheduleListByCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result<List<Schedule>>>() {

                    @Override
                    public void onNext(Result<List<Schedule>> mResult) {
                        //JSONObject jsonObject = mResult.getData();
                        List<Schedule> mList = mResult.getData();
                        mSearchView.searchSuccess(mList);
                        Log.d(TAG, "课表搜索成功: "+mList);

                    }


                    @Override
                    public void onApiException(ApiException e) {
                        mSearchView.searchFail(e.getMsg());
                        Log.d(TAG, "课表搜索失败: "+e.getMsg());
                    }
                });
    }

    public void searchByShareCode(String shareCode) {
        mApiService.getScheduleListByShareCode(shareCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result<List<Schedule>>>() {

                    @Override
                    public void onNext(Result<List<Schedule>> mResult) {
                        //JSONObject jsonObject = mResult.getData();
                        List<Schedule> mList = mResult.getData();
                        mSearchView.searchSuccess(mList);
                        Log.d(TAG, "课表搜索成功: "+mList);

                    }


                    @Override
                    public void onApiException(ApiException e) {
                        mSearchView.searchFail(e.getMsg());
                        Log.d(TAG, "课表搜索失败: "+e.getMsg());
                    }
                });
    }

    public void getScheduleByShareCode(String shareCode) {
        mApiService.getScheduleByShareCode(shareCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result<CourseList>>() {

                    @Override
                    public void onNext(Result<CourseList> mResult) {
                        //JSONObject jsonObject = mResult.getData();
                        CourseList mList = mResult.getData();
                        mSearchView.getCourseSuccess(mList);
                        Log.d(TAG, "课表获取成功: "+mList);

                    }


                    @Override
                    public void onApiException(ApiException e) {
                        mSearchView.getCourseFail(e.getMsg());
                        Log.d(TAG, "课表获取失败: "+e.getMsg());
                    }
                });
    }

    public void getScheduleByUid(int uid) {
        mApiService.getScheduleListByUid(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result<List<Schedule>>>() {

                    @Override
                    public void onNext(Result<List<Schedule>> mResult) {
                        //JSONObject jsonObject = mResult.getData();
                        List<Schedule> mList = mResult.getData();
                        mSearchView.searchSuccess(mList);
                        Log.d(TAG, "课表获取成功: "+mList);

                    }


                    @Override
                    public void onApiException(ApiException e) {
                        mSearchView.getCourseFail(e.getMsg());
                        Log.d(TAG, "课表获取失败: "+e.getMsg());
                    }
                });
    }
}
