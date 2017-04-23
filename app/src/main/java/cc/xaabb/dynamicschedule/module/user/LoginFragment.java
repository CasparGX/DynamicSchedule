package cc.xaabb.dynamicschedule.module.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.app.DSApplication;
import cc.xaabb.dynamicschedule.model.UserModel;

import static cc.xaabb.dynamicschedule.utils.ConstUtils.isBlank;

public class LoginFragment extends MeFragment implements LoginView {
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
    private LoginPresenter mLoginPresenter;
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
        mLoginPresenter = new LoginPresenter(this);
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
                    loginView.login("");
                } else {
                    //注册并登录
                    if(isBlank(editUsername.getText().toString())
                            || isBlank(editPassword.getText().toString())
                            || isBlank(editPasswordConfirm.getText().toString())) {
                        Toast.makeText(getContext(), "请输入完整", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!editPassword.getText().toString().equals(editPasswordConfirm.getText().toString())) {
                        Toast.makeText(getContext(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String username = editUsername.getText().toString();
                    String password = editPassword.getText().toString();
                    mLoginPresenter.register(username, password);
//                    mLoginPresenter.getHolidayNext();


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

    @Override
    public void login(String msg) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void register(@Nullable String msg) {

    }
}
