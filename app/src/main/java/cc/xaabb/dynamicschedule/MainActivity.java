package cc.xaabb.dynamicschedule;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
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
import cc.xaabb.dynamicschedule.utils.LocationUtil;

public class MainActivity extends AppCompatActivity {

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
    private Fragment mMeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mResources = getResources();
        LocationUtil locationUtil = new LocationUtil(this);
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
        //mNavigation.performClick();
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
                if (mMeFragment == null) {
                    mMeFragment = new LoginFragment();
                    transaction.add(R.id.fragment_layout, mMeFragment);
                }
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


}
