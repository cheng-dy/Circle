package com.circle.circle.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.Adapter.DynamicAdapter;
import com.circle.circle.Bean.DynamicDetail;
import com.circle.circle.Bean.User;
import com.circle.circle.R;
import com.circle.circle.Utils.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CircleActivity extends AppCompatActivity {
    private Thread thread;
    private String classID;
    private User user;
    private FloatingActionButton fab;
    private TextView host;
    private ImageView header;
    private ListView dynamic_list;
    protected static final int TO_PUBLISH_DYNAMIC = 1;

    private MyHandler mHandler = new MyHandler(this,this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        iniView();
        iniTitle();
        Toast.makeText(CircleActivity.this,user.getClassID()+"",
                Toast.LENGTH_SHORT).show();
        thread = new Thread(new CircleActivity.loginRunnable(mHandler));
        thread.start();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CircleActivity.this,PublishActivity.class);
                startActivityForResult(intent, TO_PUBLISH_DYNAMIC);
            }
        });
    }
    private View getheadView() {
        View view = LayoutInflater.from(CircleActivity.this).inflate(
                R.layout.circle_head,dynamic_list, false);
        host=view.findViewById(R.id.host);
        header=view.findViewById(R.id.header);
        host.setText(user.getUsername());
        ImageLoader.getInstance().displayImage(user.getHeaderImg(), header);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK && requestCode == TO_PUBLISH_DYNAMIC)
        {
            /*SharedPreferences preferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
            userID=preferences.getString("userID","");
            data.getStringExtra("");*/
            refresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refresh(){
        Thread thread=new Thread(new CircleActivity.loginRunnable(mHandler));
        thread.start();
    }
    private void iniTitle(){
        TextView back=findViewById(R.id.back);
        back.setText("通知");
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(CircleActivity.this,NoticeActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        TextView title_text=findViewById(R.id.title_text);
        title_text.setText("班级圈");
        if(!user.getCharacters().equals("master")){
            TextView title_edit=findViewById(R.id.title_edit);
            title_edit.setText("   更换班级");
            title_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(CircleActivity.this,UpdateClassActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            });
        }
        else{
            TextView title_edit=findViewById(R.id.title_edit);
            title_edit.setText("   所有人员");
            title_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(CircleActivity.this,UserActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
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
        classID=user.getClassID()+"";
        mHandler.setUser(user);
        fab=findViewById(R.id.fab);
        dynamic_list=findViewById(R.id.dynamic_list);
        dynamic_list.addHeaderView(getheadView());
        SharedPreferences preferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userID", user.getUserID()+"");
        editor.putString("headerImg", user.getHeaderImg()+"");
        editor.putString("classID",user.getClassID()+"");
        editor.putString("character",user.getCharacters());
        editor.commit();
    }

    private class MyHandler extends Handler {
        Context mContext;
        User muser;
        private final WeakReference<CircleActivity> mActivity;

        public MyHandler(CircleActivity activity, Context context) {
            mActivity = new WeakReference<>(activity);
            mContext=context;
        }

        public void setUser(User user){
            muser=user;
        }
        @Override
        public void handleMessage(Message msg) {
            CircleActivity activity = mActivity.get();
            if (activity != null) {
                Gson gson=new Gson();
                JSONObject JO = (JSONObject) msg.obj;
                try {
                    String json = JO.getString("results");
                    List<DynamicDetail> dds=gson.fromJson(json,new TypeToken<List<DynamicDetail>>(){}.getType());
                    Toast.makeText(mContext,"动态数量为：" + dds.size(),
                            Toast.LENGTH_SHORT).show();
                    DynamicAdapter dynamicAdapter=new DynamicAdapter(mContext,muser,activity);
                    dynamicAdapter.setData(dds);
                    dynamic_list.setAdapter(dynamicAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class loginRunnable implements Runnable {
        private MyHandler myHandler;
        public loginRunnable(MyHandler handler){
            myHandler=handler;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //get
            // Object object= HttpReq.toGetData(name, pasd);

            List list = new ArrayList();
            list.add(new BasicNameValuePair("aclass.code", classID));
            JSONObject JO = JsonUtil.doPost("user_getDynamics", list);

            //发送数据到 handler
            Message message = mHandler.obtainMessage();
            message.obj = JO;
            myHandler.sendMessage(message);
        }
    }
}