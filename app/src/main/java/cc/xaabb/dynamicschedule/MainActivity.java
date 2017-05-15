package cc.xaabb.dynamicschedule;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.model.UserModel;
import cc.xaabb.dynamicschedule.module.home.HomeFragment;
import cc.xaabb.dynamicschedule.module.search.SearchFragment;
import cc.xaabb.dynamicschedule.module.user.LoginFragment;
import cc.xaabb.dynamicschedule.module.user.LoginView;
import cc.xaabb.dynamicschedule.module.user.MeFragment;
import cc.xaabb.dynamicschedule.module.user.UserFragment;
import cc.xaabb.dynamicschedule.utils.ACache;
import cc.xaabb.dynamicschedule.utils.LocationUtil;

public class MainActivity extends AppCompatActivity implements LoginView {

    public static final String CUR_COURSE_LIST = "CurCourseList";
    private static List<Course> mCurCourseList;

    @Bind(R.id.navigation)
    BottomNavigationView mNavigation;
    private String TAG = "MainActivity";
    private DSApplication app;
    private Context mContext;
    private Resources mResources;
    private static ACache aCache;
    private String[] spinnerData;


    private SearchFragment mSearchFragment;
    private HomeFragment mHomeFragment;
    private MeFragment mMeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mResources = getResources();
        app = (DSApplication) getApplication();
        aCache = ACache.get(this);
        LocationUtil locationUtil = new LocationUtil(this, this);
        locationUtil.getLocation();
        //app.getLocation();
        initView();
        initData();
    }

    private void initData() {
        getCourseListFromCache();
        Gson gson = new Gson();
        UserModel userModel = gson.fromJson(aCache.getAsString("user"), UserModel.class);
        app.setUserModel(userModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHomeFragment!=null) {
            mHomeFragment.refreshCourse();
        }
    }

    private void initView() {

        mNavigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        setNav(item);
                        return true;
                    }
                });
        mNavigation.getMenu().getItem(1).setChecked(true);
        setNav(mNavigation.getMenu().getItem(1));
    }

    private void setNav(MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        Log.i(TAG, "setNav: "+item);
        switch (item.getItemId()) {
            case R.id.action_item1:
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                    transaction.add(R.id.fragment_layout, mSearchFragment);
                }
                transaction.show(mSearchFragment);
                break;
            case R.id.action_item2:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_layout, mHomeFragment);
                }
                transaction.show(mHomeFragment);
                mHomeFragment.setUserVisibleHint(true);
                break;
            case R.id.action_item3:
                if (app.getUserModel()!=null && app.getUserModel().getUsername()!=null && !app.getUserModel().getUsername().equals("")) {
                    if (mMeFragment == null || (mMeFragment!=null && !(mMeFragment instanceof UserFragment))) {
                        mMeFragment = new UserFragment();
                        transaction.add(R.id.fragment_layout, mMeFragment);
                    }
                } else {
                    if (mMeFragment == null || (mMeFragment!=null && !(mMeFragment instanceof LoginFragment))) {
                        mMeFragment = new LoginFragment();
                        transaction.add(R.id.fragment_layout, mMeFragment);
                    }
                }
                mMeFragment.setLoginListener(this);
                transaction.show(mMeFragment);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(android.support.v4.app.FragmentTransaction transaction){
        if(mSearchFragment!=null){
            transaction.hide(mSearchFragment);
        }
        if(mHomeFragment!=null){
            transaction.hide(mHomeFragment);
        }
        if(mMeFragment!=null){
            transaction.hide(mMeFragment);
        }
    }


    public static List<Course> getmCurCourseList() {
        return mCurCourseList;
    }

    public static Course getmCurCourse(int position) {
        return mCurCourseList.get(position);
    }

    public static void setmCurCourseList(List<Course> mCurCourseList) {
        MainActivity.mCurCourseList = mCurCourseList;
        setCourseListCache();
    }

    public static void addmCurCourseList(Course mCourse) {
        MainActivity.mCurCourseList.add(mCourse);
        setCourseListCache();
    }

    public static void setmCurCourseList(int position, Course mCourse) {
        MainActivity.mCurCourseList.set(position, mCourse);
        setCourseListCache();
    }

    public static void setCourseListCache() {
        Gson gson = new Gson();
        String curCourseList = gson.toJson(mCurCourseList);
        aCache.put(CUR_COURSE_LIST, curCourseList);
    }

    private void getCourseListFromCache() {
        String curCourse = aCache.getAsString(CUR_COURSE_LIST);
        if (curCourse == null) {
            return ;
        }
        Gson gson = new Gson();
        mCurCourseList = gson.fromJson(curCourse, new TypeToken<List<Course>>(){}.getType());
    }


    @Override
    public void loginSuccess(UserModel userModel) {
        setNav(mNavigation.getMenu().getItem(2));
    }

    @Override
    public void loginFail(String msg) {

    }

    @Override
    public void logout() {
        setNav(mNavigation.getMenu().getItem(2));
    }

    @Override
    public void register(@Nullable String msg, @Nullable UserModel userModel) {

    }
}
