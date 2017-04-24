package cc.xaabb.dynamicschedule.module.user;

import android.support.annotation.Nullable;

import cc.xaabb.dynamicschedule.model.UserModel;

/**
 * Created by 63024 on 2017/4/21 0021.
 */

public interface LoginView {
    void loginSuccess(UserModel userModel);
    void loginFail(String msg);
    void logout();
    void register(@Nullable String msg, @Nullable UserModel userModel);
}
