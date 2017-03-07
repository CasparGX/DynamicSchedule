package cc.xaabb.dynamicschedule.course;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.xaabb.dynamicschedule.R;


/**
 * Created by Caspar on 2016/12/24.
 */

public class CourseItemView {
    public static LinearLayout create(Context mContext,
                                      ViewGroup layoutCourseContent,
                                      int defaultWidth, int defaultHeight,
                                      int weekDay, int mSectionStart, int mSectionEnd,
                                      String mCourseName, String location, String teacher) {

        LinearLayout item_course = (LinearLayout) ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_course, layoutCourseContent, false);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) item_course.getLayoutParams();
        params.height = defaultHeight * (mSectionEnd - mSectionStart);
        params.width = defaultWidth;
        params.topMargin = (mSectionStart - 1) * defaultHeight;
        params.leftMargin = (weekDay - 1) * defaultWidth;
        Log.i("TAG", params.topMargin + " " + params.leftMargin);
        item_course.setLayoutParams(params);
        TextView mTextView = (TextView) item_course.getChildAt(0);
        mTextView.setText(mCourseName+"\n@"+location+"\n"+teacher);
        return item_course;
    }
}
