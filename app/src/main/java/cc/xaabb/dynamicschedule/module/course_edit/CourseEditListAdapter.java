package cc.xaabb.dynamicschedule.module.course_edit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.model.Course;

import static android.content.ContentValues.TAG;
import static cc.xaabb.dynamicschedule.config.Constants.WEEK_STRING_ARRAY;

/**
 * Created by zhouenxu on 2017/3/22.
 */

public class CourseEditListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private Resources mResources;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private ArrayList<Course> mCourseList = new ArrayList<>();

    private Map<String, String> mColorMap = new ArrayMap<>();

    public CourseEditListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mResources = mContext.getResources();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_edit_list, parent, false);
        ViewHolder blvh = new ViewHolder(v);
        return blvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        Course mCourse = mCourseList.get(position);
        mHolder.mTxtCourseTitle.setText(mCourse.getCourse());
        mHolder.mTxtTeacher.setText(mCourse.getTeacher());
        mHolder.mTxtClassroom.setText(mCourse.getLocation());
        mHolder.mTxtCourseWeek.setText("周"+WEEK_STRING_ARRAY[mCourse.getWeekDay()-1]+" "+mCourse.getSectionStart()+"-"+mCourse.getSectionEnd()+"节");
        List<Integer> mWeek = mCourse.getWeek();
        for (int i = 0,j=0; i < 20; i++) {
            if(i<10) {
                TextView mTxtWeekItem = (TextView) mHolder.mLayoutWeek1To10.getChildAt(i);
                if (mWeek.get(j)-1==i) {
                    mTxtWeekItem.setBackgroundColor(Color.parseColor(mColorMap.get(mCourse.getCourse())));
                    mTxtWeekItem.setTextColor(mResources.getColor(R.color.white));
                    if (j<mWeek.size()-1) {
                        j++;
                    }
                } else {
                    mTxtWeekItem.setBackgroundColor(mResources.getColor(R.color.colorAlpha999));
                    mTxtWeekItem.setTextColor(mResources.getColor(R.color.colorTextBody));
                }
            } else {
                TextView mTxtWeekItem = (TextView) mHolder.mLayoutWeek11To20.getChildAt(i%10);
                if (mWeek.get(j)-1==i) {
                    mTxtWeekItem.setBackgroundColor(Color.parseColor(mColorMap.get(mCourse.getCourse())));
                    mTxtWeekItem.setTextColor(mResources.getColor(R.color.white));
                    if (j<mWeek.size()-1) {
                        j++;
                    }
                } else {
                    mTxtWeekItem.setBackgroundColor(mResources.getColor(R.color.colorAlpha999));
                    mTxtWeekItem.setTextColor(mResources.getColor(R.color.colorTextBody));
                }
            }
        }

        mHolder.mCardView.setTag(position);
        mHolder.mCardView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public void setCourseList(List<Course> mCourseList) {
        this.mCourseList = (ArrayList<Course>) mCourseList;
    }

    public void setColorMap(Map<String, String> mColorMap) {
        this.mColorMap = mColorMap;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_View)
        CardView mCardView;
        @Bind(R.id.txt_course_title)
        TextView mTxtCourseTitle;
        @Bind(R.id.txt_teacher)
        TextView mTxtTeacher;
        @Bind(R.id.txt_classroom)
        TextView mTxtClassroom;
        @Bind(R.id.txt_course_week)
        TextView mTxtCourseWeek;
        @Bind(R.id.layout_week_1_to_10)
        LinearLayout mLayoutWeek1To10;
        @Bind(R.id.layout_week_11_to_20)
        LinearLayout mLayoutWeek11To20;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Object position);
    }
}
