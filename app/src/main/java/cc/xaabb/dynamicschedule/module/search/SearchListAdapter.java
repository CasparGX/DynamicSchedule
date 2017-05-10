package cc.xaabb.dynamicschedule.module.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.xaabb.dynamicschedule.R;
import cc.xaabb.dynamicschedule.model.Schedule;

/**
 * Created by 63024 on 2017/5/9 0009.
 */

public class SearchListAdapter extends BaseAdapter {

    private List<Schedule> mList;
    private Context mContext;

    public SearchListAdapter(Context context) {
        mList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtLocation.setText(mList.get(position).getCity());
        viewHolder.txtShareCode.setText(mList.get(position).getShareCode());
        return convertView;
    }

    public List<Schedule> getList() {
        return mList;
    }

    public void setList(List<Schedule> list) {
        mList = list;
    }

    static class ViewHolder {
        @Bind(R.id.txt_share_code)
        TextView txtShareCode;
        @Bind(R.id.txt_location)
        TextView txtLocation;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
