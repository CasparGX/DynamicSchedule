package cc.xaabb.dynamicschedule;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.xaabb.dynamicschedule.course.CourseLayout;
import cc.xaabb.dynamicschedule.utils.ScreenUtils;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.layout_course)
    CourseLayout mLayoutCourse;
    @Bind(R.id.activity_main)
    LinearLayout mActivityMain;

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
//        initHeaderDate();
//        initCourseSize();
    }
//
//    /**
//     * 初始化课表布局
//     */
//    private void initCourseSize() {
//        defaultHeight = SizeUtils.dp2px(context.getApplicationContext(), 65);
//        LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) layoutCourseLeft.getLayoutParams();
//        mParams.height = defaultHeight * 11;
//    }
//
//    /**
//     * 初始化课表头部的日期
//     */
//    private void initHeaderDate() {
//        Date weekBegin = TimeUtils.getNowWeekBegin();
//        int mMonth = weekBegin.getMonth() + 1;
//        String mCurDate = TimeUtils.getCurTimeString(new SimpleDateFormat("dd"));
//        Log.i(TAG, "mMonth: " + mMonth);
//        Log.i(TAG, "mCurDate: " + mCurDate);
//        ((TextView) ((LinearLayout) layoutCourseHeader.getChildAt(0)).getChildAt(0)).setText(mMonth + "");
//        for (int i = 1; i < 8; i++) {
//            int mTempDate = weekBegin.getDate() + i - 1;
//            TextView mTxtDate = (TextView) ((LinearLayout) layoutCourseHeader.getChildAt(i)).getChildAt(0);
//            mTxtDate.setText(mTempDate + "");
//            if (mCurDate.equals(mTempDate + "")) { // 当日
//                mTxtDate.setTextColor(mResources.getColor(R.color.colorAlphaOringe));
//                ((TextView) ((LinearLayout) mTxtDate.getParent()).getChildAt(1)).setTextColor(mResources.getColor(R.color.colorAlphaOringe));
//            }
//        }
//    }


//    @OnClick(R.id.layout_course_content)
//    void onClickLayoutCourseContent() {
//        int layoutCourseLeftWidth = layoutCourseLeft.getWidth();
//        defaultWidth = (screenWidth - layoutCourseLeftWidth) / 7;
//        String[] allColors = mResources.getStringArray(R.array.colorItemCourseList);
//        for (int i = 0; i < 7; i++) {
//
//            Course mCourse = new Course();
//            mCourse.setCourse("放牛与捡粪");
//            mCourse.setLocation("一田");
//            mCourse.setSectionStart(1);
//            mCourse.setSectionEnd(2);
//            mCourse.setSectionLength(2);
//            mCourse.setTeacher("刘大毛");
//            List<Integer> mIntegerList = new ArrayList<Integer>();
//            mIntegerList.add(1);
//            mIntegerList.add(2);
//            mIntegerList.add(3);
//            mCourse.setWeek(mIntegerList);
//            mCourse.setWeekString("1-3周");
//            mCourse.setWeekDay(1);
//            LinearLayout item_course = CourseItemView.create(context, layoutCourseContent, defaultWidth, defaultHeight, mCourse);
//
//            GradientDrawable mGradientDrawable = (GradientDrawable) item_course.getBackground();
//            mGradientDrawable.setColor(Color.parseColor(allColors[i]));
//            layoutCourseContent.addView(item_course);
//        }
//    }
}
