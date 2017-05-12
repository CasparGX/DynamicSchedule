package cc.xaabb.dynamicschedule.module.course_edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.MainActivity;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.base.BaseActivity;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.model.ParcelableMap;

public class CourseEditListActivity extends BaseActivity {

    @Bind(R.id.btn_back)
    ImageView mBtnBack;
    @Bind(R.id.txt_title)
    TextView mTxtTitle;
    @Bind(R.id.btn_action)
    ImageView mBtnAction;
    @Bind(R.id.recycler_course_list)
    RecyclerView mRecyclerCourseList;

    private Context mContext;

    private List<Course> mCourseList;
    private Map<String, String> mColorMap;
    private CourseEditListAdapter mCourseEditListAdapter;
    private boolean isEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit_list);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isEditable) {
            mCourseEditListAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mBtnAction.setVisibility(View.VISIBLE);
        mRecyclerCourseList.setLayoutManager(new LinearLayoutManager(this));
        mCourseEditListAdapter = new CourseEditListAdapter(this);
        mRecyclerCourseList.setAdapter(mCourseEditListAdapter);
    }

    private void initData() {
        Intent mIntent = getIntent();
        if (mIntent.hasExtra("courseList")) {
            mCourseList = mIntent.getParcelableArrayListExtra("courseList");
            isEditable = false;
        } else {
            mCourseList = MainActivity.getmCurCourseList();
            isEditable = true;
            Bundle mBundle = mIntent.getExtras();
            mColorMap = ((ParcelableMap)mBundle.getParcelable("colorMap")).map;
            mCourseEditListAdapter.setColorMap(mColorMap);
        }
        if (isEditable) {
            mBtnAction.setImageResource(R.drawable.ic_add);
        } else {
            mBtnAction.setImageResource(R.drawable.ic_set_current);
        }

        mCourseEditListAdapter.setCourseList(mCourseList);



    }

    private void initListener() {
        // 可编辑模式, 点击 item 可进入编辑页
        if (isEditable) {
            mCourseEditListAdapter.setOnItemClickListener(new CourseEditListAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, Object data) {
                    int position = (int) data;
                    Log.i("OnItemClick", "onItemClick: "+position);
                    Intent mIntent = new Intent();
                    mIntent.setClass(mContext, CourseEditActivity.class);
                    mIntent.putExtra("position", position);
                    mIntent.putExtra("color", mColorMap.get(mCourseList.get(position).getCourse()));
                    startActivity(mIntent);
                }
            });
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        mTxtTitle.setText(title);
    }

    @OnClick({R.id.btn_back, R.id.btn_action})
    void btnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_action:
                if (isEditable) {
                    Intent mIntent = new Intent();
                    mIntent.setClass(mContext, CourseEditActivity.class);
                    startActivity(mIntent);
                } else {
                    MainActivity.setmCurCourseList(mCourseList);
                }
                break;
        }
    }
}
