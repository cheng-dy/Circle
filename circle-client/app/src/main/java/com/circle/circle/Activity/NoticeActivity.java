package com.circle.circle.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private User user;
    private Thread thread;
    List<Notice> notices;
    ListView list_view;
    public static final int TO_PUBLISH_NOTICE= 3;
    private final MyHandler mHandler = new MyHandler(this,this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notices);
        iniView();
        iniTitle();
        thread=new Thread(new NoticeRunnable());
        thread.start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK&&requestCode==TO_PUBLISH_NOTICE)
        {
            String content=data.getStringExtra("content");
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String formatTime=df.format(new Date());
            Notice notice=new Notice();
            notice.setContent(content);
            notice.setFormatTime(formatTime);
            notices.add(0,notice);
            NoticeAdapter noticeAdapter=new NoticeAdapter(this,notices);
            list_view.setAdapter(noticeAdapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        title_text.setText("通知");
        TextView title_edit=findViewById(R.id.title_edit);
        if(!user.getCharacters().equals("parent")){
            title_edit.setText("发布通知");
            title_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(NoticeActivity.this,NoticePublishActivity.class);
                    intent.putExtra("user",user);
                    startActivityForResult(intent,TO_PUBLISH_NOTICE);
                }
            });
        }
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
        private final WeakReference<NoticeActivity> mActivity;

        public MyHandler(NoticeActivity activity, Context context) {
            mActivity = new WeakReference<NoticeActivity>(activity);
            mContext=context;
        }

        @Override
        public void handleMessage(Message msg) {
            NoticeActivity activity = mActivity.get();
            if (activity != null) {
                Gson gson=new Gson();
                JSONObject JO = (JSONObject) msg.obj;
                try {
                    String json = JO.getString("results");
                    if (json=="null") {
                        Toast.makeText(mContext, "无通知",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        notices=gson.fromJson(json,new TypeToken<List<Notice>>(){}.getType());
                        NoticeAdapter noticeAdapter=new NoticeAdapter(mContext,notices);
                        list_view.setAdapter(noticeAdapter);
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
            JSONObject JO = JsonUtil.doPost("user_getNotices", list);

            //发送数据到 handler
            Message message = mHandler.obtainMessage();
            message.obj = JO;
            mHandler.sendMessage(message);
        }
    }
}