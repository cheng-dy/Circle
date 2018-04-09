package com.circle.circle.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.circle.circle.R;
import com.circle.circle.Bean.Comment;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private List<Comment> mList;
    private Context mContext;

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Comment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CommentAdapter(Context _context,List<Comment> _list) {
        this.mContext = _context;
        this.mList=_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.comment_item, parent, false);
        }
        Comment comment = getItem(position);
        TextView user_name = convertView.findViewById(R.id.user_name);
        user_name.setText(comment.getUserName());
        TextView content = convertView.findViewById(R.id.content);
        content.setText(comment.getContent());
        return convertView;
    }
}
