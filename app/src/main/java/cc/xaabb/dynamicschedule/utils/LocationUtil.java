package cc.xaabb.dynamicschedule.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import cc.xaabb.dynamicschedule.app.DSApplication;
import rx.functions.Action1;

/**
 * Created by 63024 on 2017/4/19 0019.
 */

public class LocationUtil {
    private String TAG = "LocationUtil";
    private TencentLocationManager locationManager;
    private TencentLocationListener listener;
    private Activity activity;
    private Context context;
    private int retryTime = 0;

    public LocationUtil(final Context context, final Activity activity) {
        this.activity = activity;
        this.context = context;
        final DSApplication app = (DSApplication) activity.getApplication();
        final ACache aCache = ACache.get(context);
        final Gson gson = new Gson();

        listener = new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
                if (TencentLocation.ERROR_OK == error) {
                    // 定位成功
                    Log.d(TAG, "定位成功："+tencentLocation.toString());
                    app.setLocation(tencentLocation);
                    String location = gson.toJson(tencentLocation);
                    aCache.put("location",location);
                    locationManager.removeUpdates(listener);
                } else {
                    // 定位失败
                    Log.d(TAG, "定位失败: "+s);
                    String location = aCache.getAsString("location");
                    if (location!=null) {
                        Log.d(TAG, "定位失败: 使用缓存城市数据");
                        TencentLocation tencentLocation1 = gson.fromJson(location, TencentLocation.class);
                        app.setLocation(tencentLocation1);
                    }
                }
                retryTime++;
                if (retryTime>5) {
                    locationManager.removeUpdates(listener);
                }
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {

            }
        };
    }

    /**
     * 获取定位信息
     * */
    public void getLocation() {
        //判断动态权限
        RxPermissions.getInstance(context)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean mBoolean) {
                        if (mBoolean) {
                            Context context = LocationUtil.this.context;
                            TencentLocationRequest request = TencentLocationRequest.create();
                            request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
                            locationManager = TencentLocationManager.getInstance(context);
                            int error = locationManager.requestLocationUpdates(request, listener);
                            Log.d(TAG, "accept: location req code："+error);
                        }
                    }

                });
    }
}
