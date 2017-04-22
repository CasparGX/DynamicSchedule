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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.model.Course;
import cc.xaabb.dynamicschedule.module.home.HomeFragment;
import cc.xaabb.dynamicschedule.module.search.SearchFragment;
import cc.xaabb.dynamicschedule.module.user.LoginFragment;
import cc.xaabb.dynamicschedule.module.user.LoginView;
import cc.xaabb.dynamicschedule.module.user.MeFragment;
import cc.xaabb.dynamicschedule.module.user.UserFragment;
import cc.xaabb.dynamicschedule.utils.LocationUtil;

public class MainActivity extends AppCompatActivity implements LoginView {

    public static List<Course> mCurCourseList;

    @Bind(R.id.navigation)
    BottomNavigationView mNavigation;
    private String TAG = "MainActivity";
    private DSApplication app;
    private Context mContext;
    private Resources mResources;
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
        LocationUtil locationUtil = new LocationUtil(this, this);
        locationUtil.getLocation();
        //app.getLocation();
        initView();
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


    @Override
    public void login(String msg) {
        setNav(mNavigation.getMenu().getItem(2));
    }

    @Override
    public void logout() {
        setNav(mNavigation.getMenu().getItem(2));
    }

    @Override
    public void register(@Nullable String msg) {

    }
}
