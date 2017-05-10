package cc.xaabb.dynamicschedule.module.search;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.model.CourseList;
import cc.xaabb.dynamicschedule.model.Schedule;
import cc.xaabb.dynamicschedule.module.course_edit.CourseEditListActivity;
import cc.xaabb.dynamicschedule.utils.ConstUtils;
import cc.xaabb.dynamicschedule.utils.LocationUtil;

public class SearchFragment extends Fragment implements SearchView {

    @Bind(R.id.edit_search)
    EditText editSearch;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.list_view)
    ListView listView;

    private SearchListAdapter mSearchListAdapter;
    private SearchPresenter mSearchPresenter;
    private List<Schedule> mScheduleList;
    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mSearchListAdapter = new SearchListAdapter(getContext());
        listView.setAdapter(mSearchListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSearchPresenter.getScheduleByShareCode(mScheduleList.get(position).getShareCode());
            }
        });
        mSearchPresenter = new SearchPresenter(this);
    }

    @OnClick({R.id.btn_search})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String str = editSearch.getText().toString();
                if (ConstUtils.isBlank(str)) {
                    getByCity();
                } else {
                    mSearchPresenter.searchByShareCode(str);
                }
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mScheduleList==null) {
//            getByCity();
//        }

    }

    private void getByCity() {
        DSApplication application = (DSApplication) getActivity().getApplication();
        TencentLocation location = application.getLocation();
        if (location==null) {
            LocationUtil locationUtil = new LocationUtil(getContext(), getActivity());
            locationUtil.getLocation();
        } else {
            mSearchPresenter.searchByCity(location.getCity());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void getCourseSuccess(CourseList mList) {
        Intent intent = new Intent(getContext(), CourseEditListActivity.class);
        intent.putParcelableArrayListExtra("courseList", (ArrayList<Course>) mList.getCourseList());
        startActivity(intent);
    }

    @Override
    public void getCourseFail(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchSuccess(List<Schedule> mList) {
        mScheduleList = mList;
        mSearchListAdapter.setList(mList);
        mSearchListAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchFail(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
