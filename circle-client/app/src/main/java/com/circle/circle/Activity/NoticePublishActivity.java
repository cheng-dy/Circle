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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.Adapter.NoticeAdapter;
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
import java.util.ArrayList;
import java.util.List;

public class NoticePublishActivity extends AppCompatActivity {
    private User user;
    private Thread thread;
    private String content;
    private EditText editText;
    private final MyHandler mHandler = new MyHandler(this,this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_notice);
        iniView();
        iniTitle();
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
        title_text.setText("发布通知");
        TextView title_edit=findViewById(R.id.title_edit);
        title_edit.setText("发布");
        title_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content=editText.getText().toString();
                thread=new Thread(new NoticeRunnable());
                thread.start();
                Intent intent=new Intent(NoticePublishActivity.this,NoticeActivity.class);
                intent.putExtra("content",content);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    void iniView() {
        editText=findViewById(R.id.content);
        user=(User)getIntent().getSerializableExtra("user");
    }

    private static class MyHandler extends Handler {
        Context mContext;
        private final WeakReference<NoticePublishActivity> mActivity;

        public MyHandler(NoticePublishActivity activity, Context context) {
            mActivity = new WeakReference<NoticePublishActivity>(activity);
            mContext=context;
        }

        @Override
        public void handleMessage(Message msg) {
            NoticePublishActivity activity = mActivity.get();
            if (activity != null) {
                Gson gson=new Gson();
                JSONObject JO = (JSONObject) msg.obj;
                try {
                    String json = JO.getString("result");
                    if (json=="success") {
                        Toast.makeText(mContext, "发布成功",
                                Toast.LENGTH_SHORT).show();
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
            list.add(new BasicNameValuePair("notice.content", content));
            list.add(new BasicNameValuePair("notice.classID", user.getClassID()+""));
            JSONObject JO = JsonUtil.doPost("user_publishNotice", list);

            //发送数据到 handler
            Message message = mHandler.obtainMessage();
            message.obj = JO;
            mHandler.sendMessage(message);
        }
    }
}