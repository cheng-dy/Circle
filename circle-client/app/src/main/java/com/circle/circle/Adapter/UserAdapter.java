package com.circle.circle.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.Bean.Comment;
import com.circle.circle.Bean.User;
import com.circle.circle.R;
import com.circle.circle.Utils.JsonUtil;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {
    private List<User> mList;
    private Context mContext;
    private UserAdapter userAdapter=this;

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public User getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public UserAdapter(Context _context, List<User> _list) {
        this.mContext = _context;
        this.mList=_list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.user_item, parent, false);
        }
        final User user = getItem(position);
        TextView user_name = convertView.findViewById(R.id.user_name);
        user_name.setText(user.getUsername());
        ImageView delete=convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread=new Thread(new DelUserRunable(user.getUserID()+""));
                thread.start();
                mList.remove(position);
                userAdapter.notifyDataSetChanged();
                Toast.makeText(mContext,"人员删除成功",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
    class DelUserRunable implements Runnable {
        String userID;
        public DelUserRunable(String userID){
            this.userID=userID;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //get
            // Object object= HttpReq.toGetData(name, pasd);

            List list = new ArrayList();
            list.add(new BasicNameValuePair("user.userID", userID));
            JSONObject JO = JsonUtil.doPost("user_delUser", list);
        }
    }
}
