package cc.xaabb.dynamicschedule.module.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.model.UserModel;

public class LoginFragment extends MeFragment {
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.img_logo)
    ImageView imgLogo;
    @Bind(R.id.edit_username)
    EditText editUsername;
    @Bind(R.id.edit_password)
    EditText editPassword;
    @Bind(R.id.view_diver)
    View viewDiver;
    @Bind(R.id.edit_password_confirm)
    EditText editPasswordConfirm;
    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.btn_2)
    Button btn2;

    private DSApplication app;
    private LoginView loginView;
    private boolean loginStatus = true; //登录状态， true为登录，false为注册
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        app = (DSApplication) getActivity().getApplication();
        return view;
    }

    @OnClick({R.id.btn_1,R.id.btn_2})
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                if (loginStatus) {
                    editPasswordConfirm.setVisibility(View.VISIBLE);
                    viewDiver.setVisibility(View.VISIBLE);
                    btn1.setText("返回登录");
                    btn2.setText("注册");
                } else {
                    editPasswordConfirm.setVisibility(View.GONE);
                    viewDiver.setVisibility(View.GONE);
                    btn1.setText("注册");
                    btn2.setText("登录");
                }
                loginStatus = !loginStatus;
                break;
            case R.id.btn_2:
                if (loginStatus) {
                    //登录
                    UserModel userModel = new UserModel();
                    userModel.setUsername("123");
                    app.setUserModel(userModel);
                    loginView.login();
                } else {
                    //注册并登录

                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setLoginListener(LoginView loginListener) {
        loginView = loginListener;
    }
}
