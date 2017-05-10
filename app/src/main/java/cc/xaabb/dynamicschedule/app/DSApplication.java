package cc.xaabb.dynamicschedule.app;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.view.WindowManager;

import com.tencent.map.geolocation.TencentLocation;

import cc.xaabb.dynamicschedule.model.UserModel;
import cc.xaabb.dynamicschedule.utils.ScreenUtils;

/**
 * Created by zhouenxu on 2017/3/16.
 */

public class DSApplication extends Application {

    private TencentLocation location;
    private UserModel userModel;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        ScreenUtils.density = getResources().getDisplayMetrics().density;
        ScreenUtils.screenHeight = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        ScreenUtils.screenWidth = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public TencentLocation getLocation() {
        return location;
    }

    public void setLocation(TencentLocation location) {
        this.location = location;
    }

    public boolean isLogin() {
        boolean isLogin = false;
        if (userModel!=null && userModel.getId()!=null) {
            isLogin = true;
        }
        return isLogin;
    }
}
