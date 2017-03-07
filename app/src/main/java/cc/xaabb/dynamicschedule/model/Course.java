package cc.xaabb.dynamicschedule.model;

import java.util.List;

/**
 * Created by Caspar on 2017/2/3.
 */

public class Course {

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
    private String sectionLength;
    private String sectionStart;
    private String sectionEnd;
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

    public String getSectionLength() {
        return sectionLength;
    }

    public void setSectionLength(String sectionLength) {
        this.sectionLength = sectionLength;
    }

    public String getSectionStart() {
        return sectionStart;
    }

    public void setSectionStart(String sectionStart) {
        this.sectionStart = sectionStart;
    }

    public String getSectionEnd() {
        return sectionEnd;
    }

    public void setSectionEnd(String sectionEnd) {
        this.sectionEnd = sectionEnd;
    }

    public List<Integer> getWeek() {
        return week;
    }

    public void setWeek(List<Integer> week) {
        this.week = week;
    }
}
