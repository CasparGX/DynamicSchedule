package cc.xaabb.dynamicschedule.widget.course;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.utils.ImageUtils;
import cc.xaabb.dynamicschedule.utils.ScreenUtils;
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

    private Integer mCurWeek = 3;
    private List<Course> mCourseList;

    private Map<String, String> mColorMap;

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
        initData();
        initCourseLayoutView();
        initHeaderDate();
        initCourseSize();
        addView(view);
    }

    private void initData() {
        screenWidth = ScreenUtils.screenWidth;
        defaultHeight = SizeUtils.dp2px(mContext.getApplicationContext(), 65);
    }

    private void initCourseLayoutView() {

        layoutCourseLeft = mCourseLayout.mLayoutCourseLeft;
        layoutCourseHeader = mCourseLayout.mLayoutCourseHeader;
        layoutCourseContent = mCourseLayout.mLayoutCourseContent;

        // 获取 layoutCourseContent 宽度
        ViewTreeObserver vto = layoutCourseContent.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutCourseContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                defaultWidth = layoutCourseContent.getWidth() / 7;
            }
        });

        layoutCourseContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Course> mCourses = new ArrayList<Course>();
                for (int i = 0; i < 7; i++) {

                    Course mCourse = new Course();
                    mCourse.setCourse("放牛与捡粪");
                    mCourse.setLocation("一田");
                    mCourse.setSectionStart(i + 1);
                    mCourse.setSectionEnd(i + 3);
                    mCourse.setSectionLength(2);
                    mCourse.setTeacher("刘大毛");
                    List<Integer> mIntegerList = new ArrayList<Integer>();
                    mIntegerList.add(1);
                    mIntegerList.add(i + 1);
                    mCourse.setWeek(mIntegerList);
                    mCourse.setWeekString("1-3周");
                    mCourse.setWeekDay(i + 1);
                    mCourses.add(mCourse);
                }
                setCourseList(null);
            }
        });
    }

    /**
     * 初始化课表布局
     */
    private void initCourseSize() {
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
                mTxtDate.setTextColor(mResources.getColor(R.color.colorAlphaGreen));
                ((TextView) ((LinearLayout) mTxtDate.getParent()).getChildAt(1)).setTextColor(mResources.getColor(R.color.colorAlphaGreen));
            }
        }
    }

    /**
     * 设置课表
     * 将课表内容区域的子 view 清空, 然后添加新的 CourseItemView
     */
    public void setCourseList(@Nullable List<Course> mCourseList) {
        if (mCourseList == null) {
            mCourseList = this.mCourseList;
        } else {
            this.mCourseList = mCourseList;
        }

        layoutCourseContent.removeAllViews();

        String[] allColors = mResources.getStringArray(R.array.colorItemCourseList);
        List<String> mTempList = Arrays.asList(allColors);
        final LinkedList<String> mColorList = new LinkedList<>(mTempList);
        mColorMap = new ArrayMap<>();

        for (int i = 0; i < mCourseList.size(); i++) {
            String color;
            final Course mCourse = mCourseList.get(i);
            if (!mCourse.getWeek().contains(mCurWeek)) {
                continue;
            }
            LinearLayout item_course = CourseItemView.create(mContext, layoutCourseContent, defaultWidth, defaultHeight, mCourse);

            if (mColorMap.containsKey(mCourse.getCourse())) {
                color = mColorMap.get(mCourse.getCourse());
            } else {
                color = mColorList.pop();
                mColorMap.put(mCourse.getCourse(), color);
            }

            GradientDrawable mGradientDrawable = (GradientDrawable) item_course.getBackground();
            mGradientDrawable.setColor(Color.parseColor(color));
            item_course.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                    mBuilder.setTitle(mCourse.getCourse());
                    String content = String.format("教师: %s \n教室: %s \n\n%s\t第%s-%s节", mCourse.getTeacher(), mCourse.getLocation(), mCourse.getWeekString(), mCourse.getSectionStart(), mCourse.getSectionEnd());
                    mBuilder.setMessage(content);
                    mBuilder.create().show();
                }
            });
            layoutCourseContent.addView(item_course);
        }
    }

    /**
     * 获取课表
     * */
    public List<Course> getCourseList() {
        return this.mCourseList;
    }

    /**
     * 获取课表截图
     * */
    public Bitmap getCourseScreenShot() {
        Bitmap mHeaderBitmap = ImageUtils.getBitmapByView(mCourseLayout.mLayoutCourseHeader, mResources.getColor(R.color.pink_fff1f1));
        Bitmap mBitmapByScrollView = ImageUtils.getBitmapByScrollView(mCourseLayout.mScrollCourseAllcourse, mResources.getColor(R.color.colorCourseBg));
        Bitmap mCourseBitmap = ImageUtils.mergeBitmap(mHeaderBitmap, mBitmapByScrollView);
//        return ImageUtils.compressImage(mCourseBitmap);
        return mCourseBitmap;
    }

    /**
     * 获取课表对应色块 map
     * */
    public Map<String, String> getColorMap() {
        return mColorMap;
    }


    /**
     * 设置当前周
     * */
    public void setCurWeek(Integer mCurWeek) {
        this.mCurWeek = mCurWeek;
        setCourseList(null);
    }



    static class ViewHolder {
        @Bind(R.id.layout_course_header)
        LinearLayout mLayoutCourseHeader;
        @Bind(R.id.layout_course_left)
        LinearLayout mLayoutCourseLeft;
        @Bind(R.id.layout_course_content)
        RelativeLayout mLayoutCourseContent;
        @Bind(R.id.layout_course_allcourse)
        LinearLayout mLayoutCourseAllcourse;
        @Bind(R.id.scroll_course_allcourse)
        NestedScrollView mScrollCourseAllcourse;
        /*@Bind(R.id.fab_1)
        FloatingActionButton mFab1;
        @Bind(R.id.fab_2)
        FloatingActionButton mFab2;
        @Bind(R.id.fab_3)
        FloatingActionButton mFab3;
        @Bind(R.id.fab_menu)
        FloatingActionsMenu mFabMenu;*/

        /*@OnClick(R.id.fab_3)
        void onClickFab3(){
            Log.i("viewholder", "onClickFab3: ");
        }*/
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
