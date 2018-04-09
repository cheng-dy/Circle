package com.circle.circle.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.circle.circle.Bean.User;
import com.circle.circle.R;

public class ShowCodeActivity extends AppCompatActivity {
    private User user;
    private Button confirm;
    private TextView code;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_code);
        iniTitle();
        iniView();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShowCodeActivity.this,CircleActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }

    private void iniView(){
        user=(User)getIntent().getSerializableExtra("user");
        confirm=findViewById(R.id.confirm);
        code=findViewById(R.id.code);
        code.setText(user.getClassID()+"");
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
        title_text.setText("班级圈");

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }
}
