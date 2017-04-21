package cc.xaabb.dynamicschedule.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.Locale;

import cc.xaabb.dynamicschedule.app.DSApplication;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
        listener = new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
                if (TencentLocation.ERROR_OK == error) {
                    // 定位成功
                    Log.d(TAG, "定位成功："+tencentLocation.toString());
                    DSApplication app = (DSApplication) activity.getApplication();
                    app.setLocation(tencentLocation);
                    locationManager.removeUpdates(listener);
                } else {
                    // 定位失败
                    Log.d(TAG, "定位失败: "+s);
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

    public void getLocation() {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        Context context = LocationUtil.this.context;
                        TencentLocationRequest request = TencentLocationRequest.create();
                        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
                        locationManager = TencentLocationManager.getInstance(context);
                        int error = locationManager.requestLocationUpdates(request, listener);
                        Log.d(TAG, "accept: location req code："+error);
                    }
                });
    }
}
