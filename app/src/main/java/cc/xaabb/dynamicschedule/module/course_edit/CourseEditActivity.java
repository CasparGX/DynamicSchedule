package cc.xaabb.dynamicschedule.module.course_edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.model.ParcelableMap;

public class CourseEditActivity extends AppCompatActivity {

    @Bind(R.id.btn_back)
    ImageView mBtnBack;
    @Bind(R.id.txt_title)
    TextView mTxtTitle;
    @Bind(R.id.btn_action)
    ImageView mBtnAction;
    @Bind(R.id.recycler_course_list)
    RecyclerView mRecyclerCourseList;

    private List<Course> mCourseList;
    private CourseEditListAdapter mCourseEditListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerCourseList.setLayoutManager(new LinearLayoutManager(this));
        mCourseEditListAdapter = new CourseEditListAdapter(this);
        mRecyclerCourseList.setAdapter(mCourseEditListAdapter);
        mCourseEditListAdapter.setOnItemClickListener(new CourseEditListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Log.i("OnItemClick", "onItemClick: "+data);
            }
        });
    }

    private void initData() {
        Intent mIntent = getIntent();

        mCourseList = mIntent.getParcelableArrayListExtra("courseList");
        mCourseEditListAdapter.setCourseList(mCourseList);

        Bundle mBundle = mIntent.getExtras();
        mCourseEditListAdapter.setColorMap(((ParcelableMap)mBundle.getParcelable("colorMap")).map);

    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        mTxtTitle.setText(title);
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack() {
        finish();
    }
}
