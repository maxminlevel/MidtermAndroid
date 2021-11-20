package com.example.project.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.project.Domain.UserDomain;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText editTextFullname, editTextEmail, editTextMobile, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CircularProgressButton btnRegister = findViewById(R.id.cirRegisterButton);
        btnRegister.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
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
        editTextMobile = findViewById(R.id.editTextMobile);
    }

    @Override
    public void onClick(View view) {
        String fullName = editTextFullname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullname.setError("Tên người dùng không được để trống");
            editTextFullname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Bạn phải nhập email");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email không hợp lệ. Vui lòng nhập lại");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Mật khẩu không được để trống");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Mật khẩu phải chứa ít nhất 6 ký tự");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Tạo thành công một tài khoản mới!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                            UserDomain user = new UserDomain(email, password, fullName, mobile);
//                            FirebaseDatabase.getInstance().getReference("Users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//
//                                    }
//                                    else{
//                                        Toast.makeText(RegisterActivity.this,"Đăng ký thất bại",Toast.LENGTH_LONG).show();
//
//                                    }
//                                }

//                            }

                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"Đăng ký thất bại",Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }
}