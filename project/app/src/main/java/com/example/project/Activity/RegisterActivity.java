package com.example.project.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.project.Domain.UserDomain;
import com.example.project.Helper.FirebaseHelper;
import com.example.project.Helper.Vatidation;
import com.example.project.R;


import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseHelper dataHelper;
    private EditText editTextFullname, editTextEmail, editTextTel, editTextPassword, editTextRetypePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CircularProgressButton btnRegister = findViewById(R.id.cirRegisterButton);
        btnRegister.setOnClickListener(this);
        TextView loginClickBtn = (TextView) findViewById(R.id.loginButton);
        loginClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        editTextFullname = findViewById(R.id.editTextName);
        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextPassword =  findViewById(R.id.editTextPassword);
        editTextRetypePassword =  findViewById(R.id.editTextRetypePassword);
        editTextTel = findViewById(R.id.editTextMobile);
        dataHelper = FirebaseHelper.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        editTextFullname.setText("");
        editTextEmail.setText("");
        editTextTel.setText("");
        editTextPassword.setText("");
    }

    @Override
    public void onClick(View view) {
        if(Vatidation.checkFormRegister(editTextFullname, editTextEmail, editTextTel, editTextPassword, editTextRetypePassword)){
            String fullName = editTextFullname.getText().toString();
            String email = editTextEmail.getText().toString();
            String tel = editTextTel.getText().toString();
            String password = editTextPassword.getText().toString();
            UserDomain user = new UserDomain(email, fullName, password, tel, "", "", "");
            if(dataHelper.register(user)) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }else{
                Toast.makeText(RegisterActivity.this,"Đăng ký thất bại",Toast.LENGTH_LONG).show();
            }
        }
    }
}