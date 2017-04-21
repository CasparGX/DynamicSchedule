package cc.xaabb.dynamicschedule.module.user;

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
        return view;
    }

    @OnClick({R.id.item_my_course,R.id.item_setup,R.id.item_logout,R.id.item_about})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.item_my_course:

                break;
            case R.id.item_setup:

                break;

            case R.id.item_logout:
                app.setUserModel(new UserModel());
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
