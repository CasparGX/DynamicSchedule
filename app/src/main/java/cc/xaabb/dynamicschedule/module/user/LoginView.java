package cc.xaabb.dynamicschedule.module.user;

import android.support.annotation.Nullable;

/**
 * Created by 63024 on 2017/4/21 0021.
 */

public interface LoginView {
    void login(@Nullable String msg);
    void logout();
    void register(@Nullable String msg);
}
