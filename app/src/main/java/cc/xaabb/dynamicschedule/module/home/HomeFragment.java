package cc.xaabb.dynamicschedule.module.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.MainActivity;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.model.CourseList;
import cc.xaabb.dynamicschedule.model.ParcelableMap;
import cc.xaabb.dynamicschedule.module.course_edit.CourseEditListActivity;
import cc.xaabb.dynamicschedule.utils.ACache;
import cc.xaabb.dynamicschedule.widget.course.CourseLayout;


public class HomeFragment extends Fragment implements ScheduleUploadView{

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
    private DSApplication app;
    private Context mContext;
    private Resources mResources;
    private ACache mACache;
    private int screenWidth;
    int defaultHeight;
    int defaultWidth;

    private String[] spinnerData;
    private static HomeFragment instance;

    public static HomeFragment newInstance() {
        return instance;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        instance = this;
        mContext = this.getContext();
        app = (DSApplication) getActivity().getApplication();
        mResources = getResources();
        mACache = ACache.get(getContext());
        initHeader();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void refreshCourse() {
        if (mLayoutCourse!=null) {
            mLayoutCourse.setCourseList(MainActivity.getmCurCourseList());
        }
    }

    private void initHeader() {

        List<Course> mCourses = new ArrayList<Course>();
        for (int i = 0; i < 21; i++) {

            Course mCourse = new Course();
            mCourse.setCourse("大学英语" + (i % 7));
            mCourse.setLocation("逸夫楼204");
            mCourse.setSectionStart((i / 7*2) + (i % 7*2) + 1);
            mCourse.setSectionEnd((i / 7*2) + (i % 7*2) + 3);
            mCourse.setSectionLength(2);
            mCourse.setTeacher("王老师");
            List<Integer> mIntegerList = new ArrayList<Integer>();
            mIntegerList.add(1);
            mIntegerList.add(i % 7 + 1);
            mCourse.setWeek(mIntegerList);
            mCourse.setWeekString("1-3周");
            mCourse.setWeekDay(i / 4 + 1);
            mCourses.add(mCourse);
        }
        MainActivity.setmCurCourseList(mCourses);
        mLayoutCourse.setCourseList(MainActivity.getmCurCourseList());
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.item_spinner_course_week2, spinnerData);
        adapter.setDropDownViewResource(R.layout.item_spinner_course_week);
        mCurWeekSpinner.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            String curWeekStr = mACache.getAsString("curWeek");
            int curWeek = curWeekStr==null ? 1 : Integer.parseInt(curWeekStr);
            mCurWeekSpinner.setSelection(curWeek-1);
            mLayoutCourse.setCurWeek(curWeek);
            mLayoutCourse.refreshCourse();
        }
    }

    @OnClick({R.id.fab_3, R.id.fab_2, R.id.fab_1})
    void onClickFAB(View view){
        Intent mIntent;
        switch (view.getId()) {
            case R.id.fab_1:
                // 分享
//                Bitmap mBitmap = mLayoutCourse.getCourseScreenShot();
//                ImageView mImageView = new ImageView(mContext);img_logo
//                mImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                mImageView.setBackground(new BitmapDrawable(mBitmap));
//                mLayoutRoot.addView(mImageView);
                if (!app.isLogin()) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                SchedulePresenter schedulePresenter = new SchedulePresenter();
                CourseList courseList = new CourseList();
                if (app.getLocation()!=null) {
                    courseList.setCity(app.getLocation().getCity());
                } else {
                    courseList.setCity("湘潭市");
                }
                courseList.setUid(app.getUserModel().getId());
                courseList.setCourseList(mLayoutCourse.getCourseList());
                schedulePresenter.upload(this, courseList, app.getUserModel().getToken());
                break;
            case R.id.fab_2:
                //设置

                break;
            case R.id.fab_3:
                //编辑
                mIntent = new Intent();
                mIntent.setClass(mContext, CourseEditListActivity.class);
                Bundle mBundle = new Bundle();
                ParcelableMap mParcelableMap = new ParcelableMap();
                mParcelableMap.map = (ArrayMap<String, String>) mLayoutCourse.getColorMap();
                mBundle.putParcelable("colorMap", mParcelableMap);
                mIntent.putExtras(mBundle);
                //mIntent.putParcelableArrayListExtra("courseList", (ArrayList<Course>) mLayoutCourse.getCourseList());
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void uploadSuccess(String shareCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog alertDialog = builder.setTitle("分享成功")
                .setMessage("分享码：" + shareCode).create();
        alertDialog.show();

    }

    @Override
    public void uploadFail(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
