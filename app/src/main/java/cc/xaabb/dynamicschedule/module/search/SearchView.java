package cc.xaabb.dynamicschedule.module.search;

import java.util.List;

import cc.xaabb.dynamicschedule.model.CourseList;
import cc.xaabb.dynamicschedule.model.Schedule;

/**
 * Created by 63024 on 2017/5/9 0009.
 */

public interface SearchView {
    void getCourseSuccess(CourseList mList);
    void getCourseFail(String msg);
    void searchSuccess(List<Schedule> mList);
    void searchFail(String msg);
}
