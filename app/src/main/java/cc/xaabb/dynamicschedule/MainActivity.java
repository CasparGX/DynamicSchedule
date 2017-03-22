package cc.xaabb.dynamicschedule;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.course.CourseLayout;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.module.course_edit.CourseEditActivity;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.layout_root)
    CoordinatorLayout mLayoutRoot;
    @Bind(R.id.cur_week_spinner)
    Spinner mCurWeekSpinner;
    @Bind(R.id.layout_course)
    CourseLayout mLayoutCourse;
    @Bind(R.id.activity_main)
    LinearLayout mActivityMain;
    @Bind(R.id.fab_1)
    FloatingActionButton mFab1;
    @Bind(R.id.fab_2)
    FloatingActionButton mFab2;
    @Bind(R.id.fab_3)
    FloatingActionButton mFab3;
    @Bind(R.id.fab_menu)
    FloatingActionsMenu mFabMenu;
    private String TAG = "MainActivity";
    private Context mContext;
    private Resources mResources;
    private int screenWidth;
    int defaultHeight;
    int defaultWidth;

    private String[] spinnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mResources = getResources();
        initHeader();
    }

    private void initHeader() {

        List<Course> mCourses = new ArrayList<Course>();
        for (int i = 0; i < 21; i++) {

            Course mCourse = new Course();
            mCourse.setCourse("放牛与捡粪" + (i % 7));
            mCourse.setLocation("一田");
            mCourse.setSectionStart((i / 7*2) + (i % 7*2) + 1);
            mCourse.setSectionEnd((i / 7*2) + (i % 7*2) + 3);
            mCourse.setSectionLength(2);
            mCourse.setTeacher("刘大毛");
            List<Integer> mIntegerList = new ArrayList<Integer>();
            mIntegerList.add(1);
            mIntegerList.add(i % 7 + 1);
            mCourse.setWeek(mIntegerList);
            mCourse.setWeekString("1-3周");
            mCourse.setWeekDay(i / 4 + 1);
            mCourses.add(mCourse);
        }
        mLayoutCourse.setCourseList(mCourses);
        setSpinner(20);
    }

    private void setSpinner(int length) {
        mCurWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mLayoutCourse.setCurWeek(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int alllength = length;
        if (alllength < 20) alllength = 20;
        spinnerData = new String[alllength];
        int a;
        for (a = 0; a < alllength; a++) {
            spinnerData[a] = mResources.getString(R.string.course_no) + (a + 1) + mResources.getString(R.string.course_week);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner_course_week2, spinnerData);
        adapter.setDropDownViewResource(R.layout.item_spinner_course_week);
        mCurWeekSpinner.setAdapter(adapter);
    }

    @OnClick({R.id.fab_3, R.id.fab_2, R.id.fab_1})
    void onClickFAB(View view){
        Intent mIntent;
        switch (view.getId()) {
            case R.id.fab_1:
                // 分享
                Bitmap mBitmap = mLayoutCourse.getCourseScreenShot();
                ImageView mImageView = new ImageView(this);
                mImageView.setBackground(new BitmapDrawable(mBitmap));
                mLayoutRoot.addView(mImageView);
                
                break;
            case R.id.fab_2:
                //设置

                break;
            case R.id.fab_3:
                //编辑
                mIntent = new Intent();
                mIntent.setClass(this, CourseEditActivity.class);
                mIntent.putParcelableArrayListExtra("courseList", (ArrayList<Course>) mLayoutCourse.getCourseList());
                startActivity(mIntent);
                break;
        }
    }
}
