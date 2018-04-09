package com.circle.circle.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.circle.circle.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editText1;
    private String phone;
    private String password;
    private Button register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        iniTitle();
        iniView();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone=editText.getText().toString();
                password=editText1.getText().toString();
                Intent intent = new Intent(RegisterActivity.this, CharacterActivity.class);
                intent.putExtra("phone",phone);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
    }

    void iniView() {
        editText = findViewById(R.id.phone);
        editText1 = findViewById(R.id.password);
        register = findViewById(R.id.register);
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
        title_text.setText("注册");

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }
}