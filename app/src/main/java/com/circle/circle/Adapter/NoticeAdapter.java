package com.circle.circle.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.circle.circle.Bean.Notice;
import com.circle.circle.R;

import java.util.List;

public class NoticeAdapter extends BaseAdapter {
    private List<Notice> mList;
    private Context mContext;

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Notice getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public NoticeAdapter(Context _context, List<Notice> _list) {
        this.mContext = _context;
        this.mList=_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.notice_item, parent, false);
        }
        Notice notice = getItem(position);
        TextView content = convertView.findViewById(R.id.content);
        content.setText("         "+notice.getContent());
        TextView time = convertView.findViewById(R.id.time);
        time.setText(notice.getFormatTime());
        return convertView;
    }
}
