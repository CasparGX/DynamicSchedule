package cc.xaabb.dynamicschedule.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.app.DSApplication;

public class BaseActivity extends AppCompatActivity {
    protected DSApplication app;
    //手指向右滑动时的最小速度
    private static final int XSPEED_MIN = 200;

    //手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;

    //记录手指按下时的横坐标。
    private float xDown;

    //记录手指移动时的横坐标。
    private float xMove;

    //是否可以滑动返回
    private boolean canSwipeBack = true;

    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        super.onCreate(savedInstanceState);
        // No Titlebar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        app = (DSApplication) getApplication();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slided_left_in, R.anim.slide_right_out);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                //活动的距离
                int distanceX = (int) (xMove - xDown);
                //获取顺时速度
                int xSpeed = getScrollVelocity();
                //当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
                if (canSwipeBack && distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
                    finish();
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 设置是否可以滑动返回
     * */
    protected void setCanSwipeBack(boolean mCanSwipeBack){
        this.canSwipeBack = mCanSwipeBack;
    }

    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * 获取手指在content界面滑动的速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    // 写一个广播的内部类，当收到动作时，结束activity
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(this); // 这句话必须要写要不会报错，不写虽然能关闭，会报一堆错
            ((Activity) context).finish();
        }
    };
}
