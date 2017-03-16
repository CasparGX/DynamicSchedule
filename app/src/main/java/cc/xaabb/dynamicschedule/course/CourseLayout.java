package cc.xaabb.dynamicschedule.course;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.utils.SizeUtils;
import cc.xaabb.dynamicschedule.utils.TimeUtils;


/**
 * Created by zhouenxu on 2017/3/16.
 */

public class CourseLayout extends FrameLayout {

    private String TAG = "CourseLayout";

    private Context mContext;
    private int screenWidth;
    private int defaultHeight;
    private int defaultWidth;
    private Resources mResources;

    private ViewHolder mCourseLayout;
    private LinearLayout layoutCourseLeft;
    private LinearLayout layoutCourseHeader;
    private RelativeLayout layoutCourseContent;

    public CourseLayout(Context context) {
        this(context, null);
    }

    public CourseLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mResources = context.getResources();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_course, null);
        mCourseLayout = new ViewHolder(view);
        initCourseLayoutView();
        initHeaderDate();
        initCourseSize();
        addView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CourseLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initCourseLayoutView() {
        layoutCourseLeft = mCourseLayout.mLayoutCourseLeft;
        layoutCourseHeader = mCourseLayout.mLayoutCourseHeader;
        layoutCourseContent = mCourseLayout.mLayoutCourseContent;
        layoutCourseContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutCourseLeftWidth = layoutCourseLeft.getWidth();
                defaultWidth = (screenWidth - layoutCourseLeftWidth) / 7;
                String[] allColors = mResources.getStringArray(R.array.colorItemCourseList);
                for (int i = 0; i < 7; i++) {

                    Course mCourse = new Course();
                    mCourse.setCourse("放牛与捡粪");
                    mCourse.setLocation("一田");
                    mCourse.setSectionStart(i + 1);
                    mCourse.setSectionEnd(i + 2);
                    mCourse.setSectionLength(2);
                    mCourse.setTeacher("刘大毛");
                    List<Integer> mIntegerList = new ArrayList<Integer>();
                    mIntegerList.add(1);
                    mIntegerList.add(2);
                    mIntegerList.add(3);
                    mCourse.setWeek(mIntegerList);
                    mCourse.setWeekString("1-3周");
                    mCourse.setWeekDay(i + 1);
                    LinearLayout item_course = CourseItemView.create(mContext, layoutCourseContent, defaultWidth, defaultHeight, mCourse);

                    GradientDrawable mGradientDrawable = (GradientDrawable) item_course.getBackground();
                    mGradientDrawable.setColor(Color.parseColor(allColors[i]));
                    layoutCourseContent.addView(item_course);
                }
            }
        });
    }

    /**
     * 初始化课表布局
     */
    private void initCourseSize() {
        defaultHeight = SizeUtils.dp2px(mContext.getApplicationContext(), 65);
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

    static class ViewHolder{
        @Bind(R.id.layout_course_header)
        LinearLayout mLayoutCourseHeader;
        @Bind(R.id.layout_course_left)
        LinearLayout mLayoutCourseLeft;
        @Bind(R.id.layout_course_content)
        RelativeLayout mLayoutCourseContent;
        @Bind(R.id.layout_course_allcourse)
        LinearLayout mLayoutCourseAllcourse;
        @Bind(R.id.scroll_course_allcourse)
        ScrollView mScrollCourseAllcourse;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
