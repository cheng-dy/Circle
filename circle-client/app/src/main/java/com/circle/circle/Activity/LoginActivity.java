package com.circle.circle.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.Bean.DynamicDetail;
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

public class LoginActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editText1;
    private String phone;
    private String password;
    private Button login;
    private Thread thread;
    private TextView register;
    private final MyHandler mHandler = new MyHandler(this,this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        iniView();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(AppActivity.this, PublishActivity.class);
                startActivity(intent);*/
                thread = new Thread(new loginRunnable());
                thread.start();
            }
        });
        register.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void iniView() {
        editText = findViewById(R.id.phone);
        editText1 = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register=findViewById(R.id.register);
    }

    private static class MyHandler extends Handler {
        Context mContext;
        private final WeakReference<LoginActivity> mActivity;

        public MyHandler(LoginActivity activity,Context context) {
            mActivity = new WeakReference<LoginActivity>(activity);
            mContext=context;
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mActivity.get();
            if (activity != null) {
                Gson gson=new Gson();
                JSONObject JO = (JSONObject) msg.obj;
                try {
                    String json = JO.getString("result");
                    if (json=="null") {
                        Toast.makeText(mContext, "用户名或密码错误",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        User user=gson.fromJson(json,User.class);
                        Intent intent = new Intent(mContext,CircleActivity.class);
                        intent.putExtra("user", user);
                        mContext.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class loginRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            phone = editText.getText().toString();
            password = editText1.getText().toString();
            //get
            // Object object= HttpReq.toGetData(name, pasd);

            List list = new ArrayList();
            list.add(new BasicNameValuePair("phone", phone));
            list.add(new BasicNameValuePair("password", password));
            JSONObject JO = JsonUtil.doPost("user_login", list);

            //发送数据到 handler
            Message message = mHandler.obtainMessage();
            message.obj = JO;
            mHandler.sendMessage(message);
        }
    }
}