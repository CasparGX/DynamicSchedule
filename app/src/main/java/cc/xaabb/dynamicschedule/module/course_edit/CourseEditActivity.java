package cc.xaabb.dynamicschedule.module.course_edit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.MainActivity;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.base.BaseActivity;
import cc.xaabb.dynamicschedule.config.Constants;
import cc.xaabb.dynamicschedule.model.Course;

public class CourseEditActivity extends BaseActivity {

    @Bind(R.id.btn_back)
    ImageView mBtnBack;
    @Bind(R.id.txt_title)
    TextView mTxtTitle;
    @Bind(R.id.btn_action)
    ImageView mBtnAction;
    @Bind(R.id.edit_course)
    EditText mEditCourse;
    @Bind(R.id.edit_teacher)
    EditText mEditTeacher;
    @Bind(R.id.edit_location)
    EditText mEditLocation;
    @Bind(R.id.spinner_weekly)
    AppCompatSpinner mSpinnerWeekly;
    @Bind(R.id.spinner_section_start)
    AppCompatSpinner mSpinnerSectionStart;
    @Bind(R.id.spinner_section_end)
    AppCompatSpinner mSpinnerSectionEnd;
    @Bind(R.id.layout_week_1_5)
    LinearLayout mLayoutWeek15;
    @Bind(R.id.layout_week_6_10)
    LinearLayout mLayoutWeek610;
    @Bind(R.id.layout_week_11_15)
    LinearLayout mLayoutWeek1115;
    @Bind(R.id.layout_week_16_20)
    LinearLayout mLayoutWeek1620;
    @Bind(R.id.btn_commit)
    Button mBtnCommit;

    private int position;
    private Course mCourse = null;
    private List<Integer> mEditWeekList = new ArrayList<>();
    private int mColorSelected;
    private int mColorUnSelected;

    private Context mContext;
    private Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        ButterKnife.bind(this);
        setCanSwipeBack(false);

        mContext = this;
        mResources = getResources();

        initView();
        initData();
        initListener();
    }


    private void initView() {
        setSpinnerWeekly();
        setSpinnerSection();
    }

    @OnClick({R.id.btn_commit})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_commit:
                if (mSpinnerSectionStart.getSelectedItemPosition()>mSpinnerSectionEnd.getSelectedItemPosition()) {
                    Toast.makeText(this, "开始周不能大于结束周", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCourse.setWeek(mEditWeekList);
                mCourse.setCourse(mEditCourse.getText().toString());
                mCourse.setLocation(mEditLocation.getText().toString());
                mCourse.setSectionLength(mSpinnerSectionEnd.getSelectedItemPosition()-mSpinnerSectionStart.getSelectedItemPosition());
                mCourse.setTeacher(mEditTeacher.getText().toString());
                if (position == -1) {
                    MainActivity.mCurCourseList.add(mCourse);
                } else {
                    MainActivity.mCurCourseList.set(position, mCourse);
                }
                finish();
                break;
        }
    }
    private void initData() {

        Intent mIntent = getIntent();
        mColorSelected = mIntent.hasExtra("color") ? Color.parseColor(mIntent.getStringExtra("color")) : getResources().getColor(R.color.colorPrimary);
        mColorUnSelected = getResources().getColor(R.color.colorAlpha999);

        mBtnCommit.setBackgroundColor(mColorSelected);

        if (!mIntent.hasExtra("position")) {
            mCourse = new Course();
            position = -1;
            return;
        }

        position = mIntent.getIntExtra("position", 0);
        mCourse = MainActivity.mCurCourseList.get(position);
        mEditCourse.setText(mCourse.getCourse());
        mEditTeacher.setText(mCourse.getTeacher());
        mEditLocation.setText(mCourse.getLocation());
        mSpinnerWeekly.setSelection(mCourse.getWeekDay()-1);
        mSpinnerSectionStart.setSelection(mCourse.getSectionStart()-1);
        mSpinnerSectionEnd.setSelection(mCourse.getSectionEnd()-1);


    }

    private void initListener() {

        List<Integer> mWeek = mCourse.getWeek();
        for (int i = 0; i < 20; i++) {
            TextView mTxtWeekItem = null;
            if (i < 5) {
                mTxtWeekItem = (TextView) mLayoutWeek15.getChildAt(i%5);
            } else if (i < 10) {
                mTxtWeekItem = (TextView) mLayoutWeek610.getChildAt(i%5);
            } else if (i < 15) {
                mTxtWeekItem = (TextView) mLayoutWeek1115.getChildAt(i%5);
            } else if (i < 20) {
                mTxtWeekItem = (TextView) mLayoutWeek1620.getChildAt(i%5);
            }

            mTxtWeekItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleWeek((TextView) v);
                }
            });

            if (position!=-1 && mWeek.contains(i + 1)) {
                mTxtWeekItem.setBackgroundColor(mColorSelected);
                mTxtWeekItem.setTag("selected");
                mEditWeekList.add(i+1);
            }
        }
    }

    /**
     * 切换上课周选中状态
     * */
    private void toggleWeek(TextView mTextView) {
        Object mTag = mTextView.getTag();
        if (mTag!=null && mTag.equals("selected")) {
            //已选中,切换至未选中
            if (mEditWeekList.contains(Integer.parseInt(mTextView.getText() + ""))) {
                mEditWeekList.remove((Object)Integer.parseInt(mTextView.getText() + ""));
            }
            mTextView.setTag(null);
            mTextView.setBackgroundColor(mColorUnSelected);
        } else {
            //未选中,切换至已选中
            if (!mEditWeekList.contains(Integer.parseInt(mTextView.getText() + ""))) {
                mEditWeekList.add(Integer.parseInt(mTextView.getText() + ""));
            }
            mTextView.setTag("selected");
            mTextView.setBackgroundColor(mColorSelected);
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        mTxtTitle.setText(title);
    }

    @Override
    protected void setCanSwipeBack(boolean mCanSwipeBack) {
        super.setCanSwipeBack(mCanSwipeBack);
    }


    private void setSpinnerWeekly() {
        mSpinnerWeekly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //
                mCourse.setWeekDay(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int alllength = 7;
        String [] mData = new String[alllength];
        int a;
        for (a = 0; a < alllength; a++) {
            mData[a] = mResources.getString(R.string.course_week) + Constants.WEEK_STRING_ARRAY[a];
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.item_spinner_course_week, mData);
        adapter.setDropDownViewResource(R.layout.item_spinner_course_week);
        mSpinnerWeekly.setAdapter(adapter);
    }
    private void setSpinnerSection() {
        mSpinnerSectionEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //
                mCourse.setSectionEnd(i + 1);
                //mCourse.setWeekString(mSpinnerSectionStart.getSelectedItemPosition() + "-" + (i + 1) + "周");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerSectionStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //
                mCourse.setSectionStart(i+1);
                //mCourse.setWeekString(mSpinnerSectionEnd.getSelectedItemPosition()+"-"+(i+1)+"周");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int alllength = 11;
        String [] mData = new String[alllength];
        int a;
        for (a = 0; a < alllength; a++) {
            mData[a] = mResources.getString(R.string.course_no) + (a+1) +"节";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.item_spinner_course_week, mData);
        adapter.setDropDownViewResource(R.layout.item_spinner_course_week);
        mSpinnerSectionEnd.setAdapter(adapter);
        mSpinnerSectionStart.setAdapter(adapter);
    }
}
