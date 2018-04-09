package com.circle.circle.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.Adapter.NoticeAdapter;
import com.circle.circle.Adapter.UserAdapter;
import com.circle.circle.Bean.Notice;
import com.circle.circle.Bean.User;
import com.circle.circle.R;
import com.circle.circle.Utils.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private User user;
    private Thread thread;
    List<User> users;
    ListView list_view;
    private final MyHandler mHandler = new MyHandler(this,this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);
        iniView();
        iniTitle();
        thread=new Thread(new NoticeRunnable());
        thread.start();
    }

    private void iniTitle(){
        TextView back=findViewById(R.id.back);
        back.setText("< ");
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        TextView title_text=findViewById(R.id.title_text);
        title_text.setText("所有人员");
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    void iniView() {
        user=(User)getIntent().getSerializableExtra("user");
        list_view=findViewById(R.id.list_view);
    }

    private  class MyHandler extends Handler {
        Context mContext;
        private final WeakReference<UserActivity> mActivity;

        public MyHandler(UserActivity activity, Context context) {
            mActivity = new WeakReference<UserActivity>(activity);
            mContext=context;
        }

        @Override
        public void handleMessage(Message msg) {
            UserActivity activity = mActivity.get();
            if (activity != null) {
                Gson gson=new Gson();
                JSONObject JO = (JSONObject) msg.obj;
                try {
                    String json = JO.getString("results");
                    if (json=="null") {
                        Toast.makeText(mContext, "暂无人员",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        users=gson.fromJson(json,new TypeToken<List<User>>(){}.getType());
                        UserAdapter userAdapter=new UserAdapter(mContext,users);
                        list_view.setAdapter(userAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class NoticeRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            //get
            // Object object= HttpReq.toGetData(name, pasd);

            List list = new ArrayList();
            list.add(new BasicNameValuePair("aclass.code", user.getClassID()+""));
            JSONObject JO = JsonUtil.doPost("user_getAllUsers", list);

            //发送数据到 handler
            Message message = mHandler.obtainMessage();
            message.obj = JO;
            mHandler.sendMessage(message);
        }
    }
}