package cc.xaabb.dynamicschedule;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.xaabb.dynamicschedule.course.CourseLayout;
import cc.xaabb.dynamicschedule.model.Course;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.cur_week_spinner)
    Spinner mCurWeekSpinner;
    @Bind(R.id.layout_course)
    CourseLayout mLayoutCourse;
    @Bind(R.id.activity_main)
    LinearLayout mActivityMain;

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
            mIntegerList.add(i+1);
            mCourse.setWeek(mIntegerList);
            mCourse.setWeekString("1-3周");
            mCourse.setWeekDay(i + 1);
            mCourses.add(mCourse);
        }
        mLayoutCourse.setCourseList(mCourses);
        setSpinner(20);
    }

    private void setSpinner(int length){
        mCurWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mLayoutCourse.setCurWeek(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int alllength=length;
        if(alllength<20)alllength=20;
        spinnerData = new String[alllength];
        int a;
        for(a=0;a<alllength;a++){
            spinnerData[a]=mResources.getString(R.string.course_no)+(a+1)+mResources.getString(R.string.course_week);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.item_spinner_course_week2, spinnerData);
        adapter.setDropDownViewResource(R.layout.item_spinner_course_week);
        mCurWeekSpinner.setAdapter(adapter);
    }
}
