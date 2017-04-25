package cc.xaabb.dynamicschedule.module.home;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.base.BasePresenter;
import cc.xaabb.dynamicschedule.model.CourseList;
import cc.xaabb.dynamicschedule.model.Result;
import cc.xaabb.dynamicschedule.model.UserModel;
import cc.xaabb.dynamicschedule.network.ApiException;
import cc.xaabb.dynamicschedule.network.ApiService;
import cc.xaabb.dynamicschedule.network.BaseSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 63024 on 2017/4/25 0025.
 */

public class SchedulePresenter extends BasePresenter {
    private String TAG = "SchedulePresenter";
    private ApiService mApiService;
    public SchedulePresenter() {
        mApiService = mRetrofit.create(ApiService.class);
    }

    public void upload(final ScheduleUploadView scheduleUploadView, CourseList courseList, String token) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("courseList", courseList);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        Log.d(TAG, "upload: "+gson.toJson(jsonObject));
        mApiService.postScheduleUpload(gson.toJson(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Result<JSONObject>>() {

                    @Override
                    public void onNext(Result<JSONObject> mResult) {
                        JSONObject jsonObject = mResult.getData();
                        try {
                            String shareCode = jsonObject.getString("shareCode");
                            scheduleUploadView.uploadSuccess(shareCode);
                            Log.d(TAG, "课表上传成功: "+shareCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onApiException(ApiException e) {
                        scheduleUploadView.uploadFail(e.getMsg());
                        Log.d(TAG, "课表上传失败: "+e.getMsg());
                    }
                });
    }
}
