package cc.xaabb.dynamicschedule.module.my_course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.base.BaseActivity;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.model.CourseList;
import cc.xaabb.dynamicschedule.model.Schedule;
import cc.xaabb.dynamicschedule.module.course_edit.CourseEditListActivity;
import cc.xaabb.dynamicschedule.module.search.SearchListAdapter;
import cc.xaabb.dynamicschedule.module.search.SearchPresenter;
import cc.xaabb.dynamicschedule.module.search.SearchView;

public class MyCourseActivity extends BaseActivity implements SearchView {

    @Bind(R.id.btn_back)
    ImageView mBtnBack;
    @Bind(R.id.txt_title)
    TextView mTxtTitle;
    @Bind(R.id.list_view)
    ListView mListView;

    private SearchListAdapter mSearchListAdapter;
    private SearchPresenter mSearchPresenter;
    private List<Schedule> mScheduleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTxtTitle.setText("我的课表");
        mSearchListAdapter = new SearchListAdapter(this);
        mListView.setAdapter(mSearchListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSearchPresenter.getScheduleByShareCode(mScheduleList.get(position).getShareCode());
            }
        });
        mSearchPresenter = new SearchPresenter(this);
    }

    private void initData() {
        mSearchPresenter.getScheduleByUid(app.getUserModel().getId());
    }

    @OnClick({R.id.btn_back})
    public void btnClick(View v) {
        switch(v.getId()) {
            case R.id.btn_back:
            finish();
            break;
        }
    }

    @Override
    public void getCourseSuccess(CourseList mList) {
        Intent intent = new Intent(this, CourseEditListActivity.class);
        intent.putParcelableArrayListExtra("courseList", (ArrayList<Course>) mList.getCourseList());
        startActivity(intent);
    }

    @Override
    public void getCourseFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchSuccess(List<Schedule> mList) {
        mScheduleList = mList;
        mSearchListAdapter.setList(mList);
        mSearchListAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
