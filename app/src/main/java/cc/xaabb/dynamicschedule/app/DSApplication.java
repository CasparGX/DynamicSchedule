package cc.xaabb.dynamicschedule.app;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import cc.xaabb.dynamicschedule.utils.ScreenUtils;

/**
 * Created by zhouenxu on 2017/3/16.
 */

public class DSApplication extends Application {

    private void initData() {
        ScreenUtils.density = getResources().getDisplayMetrics().density;
        ScreenUtils.screenHeight = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        ScreenUtils.screenWidth = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }
}
