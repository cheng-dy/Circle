package com.circle.circle.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.circle.circle.Bean.User;
import com.circle.circle.R;
import com.circle.circle.Utils.JsonUtil;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateClassActivity extends AppCompatActivity {
    private User user;
    private Button confirm;
    private EditText new_code;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_class);
        iniTitle();
        iniView();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=new_code.getText().toString();
                user.setClassID(Long.parseLong(code));
                Thread thread=new Thread(new UpdateRunable());
                thread.start();
                Intent intent=new Intent(UpdateClassActivity.this,CircleActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }

    private void iniView(){
        user=(User)getIntent().getSerializableExtra("user");
        confirm=findViewById(R.id.confirm);
        new_code=findViewById(R.id.new_code);
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
        title_text.setText("更换班级");

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }
    class UpdateRunable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String code=new_code.getText().toString();
            List list = new ArrayList();
            list.add(new BasicNameValuePair("user.userID", user.getUserID()+""));
            list.add(new BasicNameValuePair("user.classID", code));
            JsonUtil.doPost("user_updateClass", list);
        }
    }
}
