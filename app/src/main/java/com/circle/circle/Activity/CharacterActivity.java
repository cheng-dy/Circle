package com.circle.circle.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.circle.circle.R;

public class CharacterActivity extends AppCompatActivity {
    private Button master;
    private Button teacher;
    private Button parent;
    private String phone;
    private String password;
    private String character;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_character);
        iniTitle();
        iniView();
        Toast.makeText(this.getApplicationContext(), phone+password,
                Toast.LENGTH_SHORT).show();
        master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                character="master";
                Intent intent = new Intent(CharacterActivity.this, JoinActivity.class);
                putExtras(intent,character);
                startActivity(intent);
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                character="teacher";
                Intent intent = new Intent(CharacterActivity.this, JoinActivity.class);
                putExtras(intent,character);
                startActivity(intent);
            }
        });
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                character="parent";
                Intent intent = new Intent(CharacterActivity.this, JoinActivity.class);
                putExtras(intent,character);
                startActivity(intent);
            }
        });
    }

    void putExtras(Intent intent,String character){
        intent.putExtra("phone",phone);
        intent.putExtra("password",password);
        intent.putExtra("character",character);
    }

    void iniView() {
        master = findViewById(R.id.master);
        teacher = findViewById(R.id.teacher);
        parent = findViewById(R.id.parent);
        phone=getIntent().getStringExtra("phone");
        password=getIntent().getStringExtra("password");
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
        title_text.setText("身份确认");

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }
}