package cc.xaabb.dynamicschedule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.course.CourseItemView;
import cc.xaabb.dynamicschedule.utils.ScreenUtils;
import cc.xaabb.dynamicschedule.utils.SizeUtils;
import cc.xaabb.dynamicschedule.utils.TimeUtils;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.layout_course_content)
    RelativeLayout layoutCourseContent;
    @Bind(R.id.layout_course_allcourse)
    LinearLayout layoutCourseAllcourse;
    @Bind(R.id.scroll_course_allcourse)
    ScrollView scrollCourseAllcourse;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.layout_course_header)
    LinearLayout layoutCourseHeader;
    @Bind(R.id.layout_course_left)
    LinearLayout layoutCourseLeft;

    private String TAG = "MainActivity";
    private Context context;
    private Resources mResources;
    private int screenWidth;
    int defaultHeight;
    int defaultWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        mResources = getResources();
        screenWidth = ScreenUtils.getScreenWidth(getApplicationContext());
        initHeaderDate();
        initCourseSize();
    }

    /**
     * 初始化课表布局
     */
    private void initCourseSize() {
        defaultHeight = SizeUtils.dp2px(context.getApplicationContext(), 65);
        LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) layoutCourseLeft.getLayoutParams();
        mParams.height = defaultHeight * 11;
    }

    /**
     * 初始化课表头部的日期
     */
    private void initHeaderDate() {
        Date weekBegin = TimeUtils.getNowWeekBegin();
        int mMonth = weekBegin.getMonth() + 1;
        String mCurDate = TimeUtils.getCurTimeString(new SimpleDateFormat("dd"));
        Log.i(TAG, "mMonth: " + mMonth);
        Log.i(TAG, "mCurDate: " + mCurDate);
        ((TextView) ((LinearLayout) layoutCourseHeader.getChildAt(0)).getChildAt(0)).setText(mMonth + "");
        for (int i = 1; i < 8; i++) {
            int mTempDate = weekBegin.getDate() + i - 1;
            TextView mTxtDate = (TextView) ((LinearLayout) layoutCourseHeader.getChildAt(i)).getChildAt(0);
            mTxtDate.setText(mTempDate + "");
            if (mCurDate.equals(mTempDate + "")) { // 当日
                mTxtDate.setTextColor(mResources.getColor(R.color.colorAlphaOringe));
                ((TextView) ((LinearLayout) mTxtDate.getParent()).getChildAt(1)).setTextColor(mResources.getColor(R.color.colorAlphaOringe));
            }
        }
    }

    @OnClick(R.id.layout_course_header)
    void onClickLayoutCourseHeader() {

    }

    @OnClick(R.id.layout_course_content)
    void onClickLayoutCourseContent() {
        int layoutCourseLeftWidth = layoutCourseLeft.getWidth();
        defaultWidth = (screenWidth - layoutCourseLeftWidth) / 7;
        String[] allColors = mResources.getStringArray(R.array.colorItemCourseList);
        for (int i = 0; i < 7; i++) {
            LinearLayout item_course = CourseItemView.create(context, layoutCourseContent, defaultWidth, defaultHeight, i + 1, i + 1, i + 3, "放牛与捡粪", "一田", "刘大毛");

            GradientDrawable mGradientDrawable = (GradientDrawable) item_course.getBackground();
            mGradientDrawable.setColor(Color.parseColor(allColors[i]));
            layoutCourseContent.addView(item_course);
        }
    }
}
