package cc.xaabb.dynamicschedule.widget.course;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.model.Course;


/**
 * Created by Caspar on 2016/12/24.
 */

public class CourseItemView {
    public static LinearLayout create(Context mContext,
                                      ViewGroup layoutCourseContent,
                                      int defaultWidth, int defaultHeight,
                                      int weekDay, int mSectionStart, int mSectionEnd,
                                      String mCourseName, String location, String teacher, String mWeekString) {

        LinearLayout item_course = (LinearLayout) ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_course, layoutCourseContent, false);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) item_course.getLayoutParams();
        params.height = defaultHeight * (mSectionEnd - mSectionStart);
        params.width = defaultWidth;
        params.topMargin = (mSectionStart - 1) * defaultHeight;
        params.leftMargin = (weekDay - 1) * defaultWidth;
        Log.i("TAG", "topMargin:" + params.topMargin + " leftMargin:" + params.leftMargin + " weekday:" + weekDay + " defaultWidth:" + defaultWidth);
        item_course.setLayoutParams(params);
        TextView mTextView = (TextView) item_course.getChildAt(0);
        mTextView.setText(mCourseName + "\n@" + location + "\n" + teacher + "\n" + mWeekString);
        return item_course;
    }

    public static LinearLayout create(Context mContext, ViewGroup layoutCourseContent, int defaultWidth, int defaultHeight, Course mCourse) {
        return create(mContext, layoutCourseContent, defaultWidth, defaultHeight,
                mCourse.getWeekDay(), mCourse.getSectionStart(), mCourse.getSectionEnd(), mCourse.getCourse(), mCourse.getLocation(), mCourse.getTeacher(), mCourse.getWeekString());
    }
}
