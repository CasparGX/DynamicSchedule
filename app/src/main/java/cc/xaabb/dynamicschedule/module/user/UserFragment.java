package cc.xaabb.dynamicschedule.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.model.UserModel;
import cc.xaabb.dynamicschedule.module.my_course.MyCourseActivity;
import cc.xaabb.dynamicschedule.module.setup.SetupActivity;
import cc.xaabb.dynamicschedule.utils.ACache;

public class UserFragment extends MeFragment {

    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.item_my_course)
    LinearLayout itemMyCourse;
    @Bind(R.id.item_setup)
    LinearLayout itemSetup;
    @Bind(R.id.item_logout)
    LinearLayout itemLogout;
    @Bind(R.id.item_about)
    LinearLayout itemAbout;

    private DSApplication app;
    private LoginView loginView;
    private ACache mACache;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        app = (DSApplication) getActivity().getApplication();
        mACache = ACache.get(getContext());
        return view;
    }

    @OnClick({R.id.item_my_course,R.id.item_setup,R.id.item_logout,R.id.item_about})
    public void btnClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            case R.id.item_my_course:
                mIntent = new Intent(getContext(), MyCourseActivity.class);
                startActivity(mIntent);
                break;
            case R.id.item_setup:
                mIntent = new Intent(getContext(), SetupActivity.class);
                startActivity(mIntent);
                break;

            case R.id.item_logout:
                app.setUserModel(new UserModel());
                mACache.remove("user");
                loginView.logout();
                break;
            case R.id.item_about:

                break;
        }
    }

    @Override
    public void setLoginListener(LoginView loginListener) {
        loginView = loginListener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
