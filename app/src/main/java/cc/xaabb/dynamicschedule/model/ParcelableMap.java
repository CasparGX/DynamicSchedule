package cc.xaabb.dynamicschedule.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;

import java.util.Map;

/**
 * Created by zhouenxu on 2017/3/23.
 */

public class ParcelableMap implements Parcelable {
    public ArrayMap<String,String> map = new ArrayMap<String,String>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.map.size());
        for (Map.Entry<String, String> entry : this.map.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    public ParcelableMap() {
    }

    protected ParcelableMap(Parcel in) {
        int mapSize = in.readInt();
        this.map = new ArrayMap<>(mapSize);
        for (int i = 0; i < mapSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.map.put(key, value);
        }
    }

    public static final Creator<ParcelableMap> CREATOR = new Creator<ParcelableMap>() {
        @Override
        public ParcelableMap createFromParcel(Parcel source) {
            return new ParcelableMap(source);
        }

        @Override
        public ParcelableMap[] newArray(int size) {
            return new ParcelableMap[size];
        }
    };
}
