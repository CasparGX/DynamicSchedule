package cc.xaabb.dynamicschedule.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caspar on 2017/2/3.
 */

public class Course implements Parcelable {

    /**
     * course : 面向对象程序设计 (JAVA)Ⅰ
     * location : 图书馆多媒
     * teacher : 刘新副教授
     * week : [1,2,3,4,5,6,7,8,9,10]
     * weekDay : 1
     * weekString : 1-10(周)
     * sectionLength : 2
     * sectionStart : 3
     * sectionEnd : 4
     */

    private String course;
    private String location;
    private String teacher;
    private int weekDay;
    private String weekString;
    private int sectionLength;
    private int sectionStart;
    private int sectionEnd;
    private List<Integer> week;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public String getWeekString() {
        return weekString;
    }

    public void setWeekString(String weekString) {
        this.weekString = weekString;
    }

    public int getSectionLength() {
        return sectionLength;
    }

    public void setSectionLength(int sectionLength) {
        this.sectionLength = sectionLength;
    }

    public int getSectionStart() {
        return sectionStart;
    }

    public void setSectionStart(int sectionStart) {
        this.sectionStart = sectionStart;
    }

    public int getSectionEnd() {
        return sectionEnd;
    }

    public void setSectionEnd(int sectionEnd) {
        this.sectionEnd = sectionEnd;
    }

    public List<Integer> getWeek() {
        return week;
    }

    public void setWeek(List<Integer> week) {
        this.week = week;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.course);
        dest.writeString(this.location);
        dest.writeString(this.teacher);
        dest.writeInt(this.weekDay);
        dest.writeString(this.weekString);
        dest.writeInt(this.sectionLength);
        dest.writeInt(this.sectionStart);
        dest.writeInt(this.sectionEnd);
        dest.writeList(this.week);
    }

    public Course() {
    }

    protected Course(Parcel in) {
        this.course = in.readString();
        this.location = in.readString();
        this.teacher = in.readString();
        this.weekDay = in.readInt();
        this.weekString = in.readString();
        this.sectionLength = in.readInt();
        this.sectionStart = in.readInt();
        this.sectionEnd = in.readInt();
        this.week = new ArrayList<Integer>();
        in.readList(this.week, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
