package cc.xaabb.dynamicschedule.model;

/**
 * Created by 63024 on 2017/5/9 0009.
 */

public class Schedule {

    /**
     * id : 153
     * uid : 27
     * cid : 160
     * shareCode : 27Thu May 04 17:49:25 CST 2017
     * city : 上海市
     */

    private int id;
    private int uid;
    private int cid;
    private String shareCode;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
