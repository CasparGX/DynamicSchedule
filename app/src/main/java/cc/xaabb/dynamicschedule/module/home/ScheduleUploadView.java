package cc.xaabb.dynamicschedule.module.home;

import java.util.List;

import cc.xaabb.dynamicschedule.model.CourseList;

/**
 * Created by 63024 on 2017/4/25 0025.
 */

public interface ScheduleUploadView {
    public void uploadSuccess(String shareCode);
    public void uploadFail(String msg);


}
