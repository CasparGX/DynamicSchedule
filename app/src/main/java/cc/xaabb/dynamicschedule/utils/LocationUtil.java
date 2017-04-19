package cc.xaabb.dynamicschedule.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cc.xaabb.dynamicschedule.app.DSApplication;
import io.reactivex.functions.Consumer;

import cc.xaabb.dynamicschedule.R;

/**
 * Created by 63024 on 2017/4/19 0019.
 */

public class LocationUtil {
    LocationManager myLocationManager;
    Activity activity;

    public LocationUtil(Activity activity) {
        this.activity = activity;
    }

    public void getLocation() {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {

                        DSApplication app = (DSApplication) activity.getApplication();
                        String serviceName = Context.LOCATION_SERVICE;
                        //实例化一个LocationManager对象
                        myLocationManager = (LocationManager)activity.getSystemService(serviceName);
                        if (granted) { // Always true pre-M
                            // I can control the camera now

                            //获取位置管理服务

                            //查找服务信息
                            Criteria criteria = new Criteria();
                            criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
                            criteria.setAltitudeRequired(false); //海拔信息：不需要
                            criteria.setBearingRequired(false); //方位信息: 不需要
                            criteria.setCostAllowed(true);  //是否允许付费
                            criteria.setPowerRequirement(Criteria.POWER_LOW); //耗电量: 低功耗
//        String provider = myLocationManager.getBestProvider(criteria, true); //获取GPS信息
//        myLocationManager.requestLocationUpdates(provider,2000,5,locationListener);
//        Log.e("provider", provider);
//        List<String> list = myLocationManager.getAllProviders();
//        Log.e("provider", list.toString());
                            Location gpsLocation = null;
                            Location netLocation = null;
                            GpsStatus.Listener myListener;
                            //myLocationManager.addGpsStatusListener(myListener);
                            if (netWorkIsOpen()) {
                                //2000代表每2000毫秒更新一次，5代表每5秒更新一次
                                myLocationManager.requestLocationUpdates("network", 2000, 5, locationListener);
                                netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            }

                            if (gpsIsOpen()) {
                                myLocationManager.requestLocationUpdates("gps", 2000, 5, locationListener);
                                gpsLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            }

                            if (gpsLocation == null && netLocation == null) {
                                app.setLocation(null);
                                //return null;
                            }
                            if (gpsLocation != null && netLocation != null) {
                                if (gpsLocation.getTime() < netLocation.getTime()) {
                                    gpsLocation = null;
                                    app.setLocation(netLocation);
//                                    return netLocation;
                                } else {
                                    netLocation = null;
                                    app.setLocation(gpsLocation);
//                                    return gpsLocation;
                                }
                            }
                            if (gpsLocation == null) {
                                app.setLocation(netLocation);
                                //return netLocation;
                            } else {
                                app.setLocation(gpsLocation);
                                //return gpsLocation;
                            }
                        } else {
                            // Oups permission denied
                            app.setLocation(null);
                        }
                    }
                });
//
    }

    // 获取地址信息
    private List<Address> getAddress(Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //监听GPS位置改变后得到新的经纬度
    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e("location", location.toString() + "....");
            // TODO Auto-generated method stub
            if (location != null) {
                //获取国家，省份，城市的名称
                Log.e("location", location.toString());
                List<Address> m_list = getAddress(location);
                Log.e("str", m_list.toString());
                String city = "";
                if (m_list != null && m_list.size() > 0) {
                    city = m_list.get(0).getLocality();//获取城市
                }
                Log.e("location","location:" + m_list.toString() + "\n" + "城市:" + city + "\n精度:" + location.getLongitude() + "\n纬度:" + location.getLatitude() + "\n定位方式:" + location.getProvider());
            } else {
                Log.e("location", "获取不到数据");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

    private boolean gpsIsOpen() {
        boolean isOpen = true;
        if (!myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//没有开启GPS
            isOpen = false;
        }
        return isOpen;
    }

    private boolean netWorkIsOpen() {
        boolean netIsOpen = true;
        if (!myLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {//没有开启网络定位
            netIsOpen = false;
        }
        return netIsOpen;
    }
}
