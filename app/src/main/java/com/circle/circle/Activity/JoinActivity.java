package com.circle.circle.Activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.Bean.User;
import com.circle.circle.R;
import com.circle.circle.Utils.JsonUtil;
import com.google.gson.Gson;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class JoinActivity extends AppCompatActivity {
    private String phone;
    private String password;
    private String character;
    private Button join;
    private Thread thread;
    private TextView first;
    private TextView second;
    private EditText name;
    private EditText first_value;
    private EditText second_value;
    private MyHandler mHandler = new MyHandler(this,this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);
        iniTitle();
        iniView();
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(character.equals("parent")){
                    List list=new ArrayList();
                    list.add(new BasicNameValuePair("user.username", first_value.getText().toString()+"的家长"));
                    list.add(new BasicNameValuePair("user.phoneNumber", phone));
                    list.add(new BasicNameValuePair("user.password", password));
                    list.add(new BasicNameValuePair("user.characters", character));
                    list.add(new BasicNameValuePair("user.childName", first_value.getText().toString()));
                    list.add(new BasicNameValuePair("user.classID", second_value.getText().toString()));
                    thread=new Thread(new JoinRunnable(list,"user_register_p"));
                    thread.start();
                }
                else if(character.equals("teacher")){
                    List list=new ArrayList();
                    list.add(new BasicNameValuePair("user.username", first_value.getText().toString()+"老师"));
                    list.add(new BasicNameValuePair("user.phoneNumber", phone));
                    list.add(new BasicNameValuePair("user.password", password));
                    list.add(new BasicNameValuePair("user.characters", character));
                    list.add(new BasicNameValuePair("user.classID", second_value.getText().toString()));
                    thread=new Thread(new JoinRunnable(list,"user_register_t"));
                    thread.start();
                }
                else {
                    List list=new ArrayList();
                    list.add(new BasicNameValuePair("user.username", "班主任老师"));
                    list.add(new BasicNameValuePair("user.phoneNumber", phone));
                    list.add(new BasicNameValuePair("user.password", password));
                    list.add(new BasicNameValuePair("user.characters", character));
                    list.add(new BasicNameValuePair("aclass.school", first_value.getText().toString()));
                    list.add(new BasicNameValuePair("aclass.className", second_value.getText().toString()));
                    thread=new Thread(new JoinRunnable(list,"user_register_m"));
                    thread.start();
                }
                /*Intent intent = new Intent(AppActivity.this, PublishActivity.class);
                startActivity(intent);*/

                //thread = new Thread(new loginRunnable());
                //thread.start();
            }
        });
    }

    void iniView() {
        name = findViewById(R.id.name);
        first = findViewById(R.id.first);
        first_value = findViewById(R.id.first_value);
        second=findViewById(R.id.second);
        second_value=findViewById(R.id.second_value);
        join=findViewById(R.id.join);

        phone=getIntent().getStringExtra("phone");
        password=getIntent().getStringExtra("password");
        character=getIntent().getStringExtra("character");
        mHandler.setCharacter(character);

        if(character.equals("master")){
            first.setText("学校");
            first_value.setHint("学校名称");
            second.setText("班级");
            second_value.setHint("班级名称");
            join.setText("创建班级圈");
        }
        else if(character.equals("teacher")){
            first.setText("任教科目");
            first_value.setHint("任教科目");
        }
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
        title_text.setText("详细信息");

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    private static class MyHandler extends Handler {
        Context mContext;
        String character;
        private final WeakReference<JoinActivity> mActivity;

        public MyHandler(JoinActivity activity, Context context) {
            mActivity = new WeakReference<JoinActivity>(activity);
            mContext=context;
        }
        public void setCharacter(String character){
            this.character=character;
        }

        @Override
        public void handleMessage(Message msg) {
            JoinActivity activity = mActivity.get();
            if (activity != null) {
                Gson gson=new Gson();
                JSONObject JO = (JSONObject) msg.obj;
                try {
                    String json = JO.getString("result");
                    if (json=="null") {
                        Toast.makeText(mContext, "注册失败",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        User user=gson.fromJson(json,User.class);
                        if(character.equals("master")){
                            Intent intent = new Intent(mContext,ShowCodeActivity.class);
                            intent.putExtra("user", user);
                            mContext.startActivity(intent);
                        }else {
                            Intent intent = new Intent(mContext, CircleActivity.class);
                            intent.putExtra("user", user);
                            mContext.startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class JoinRunnable implements Runnable {
        List mlist;
        String maction;
        public JoinRunnable(List list,String action){
            mlist=list;
            maction=action;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //get
            // Object object= HttpReq.toGetData(name, pasd);
            JSONObject JO = JsonUtil.doPost(maction, mlist);

            //发送数据到 handler
            Message message = mHandler.obtainMessage();
            message.obj = JO;
            mHandler.sendMessage(message);
        }
    }
}