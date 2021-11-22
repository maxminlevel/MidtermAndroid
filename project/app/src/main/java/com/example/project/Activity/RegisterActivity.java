package com.example.project.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.project.Domain.UserDomain;
import com.example.project.Helper.ValidationHelper;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextFullname, editTextEmail, editTextTel, editTextPassword, editTextRetypePassword;
    static AlertDialog.Builder builder;
    ImageView backLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        builder = new AlertDialog.Builder(this);

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
        backLoginBtn = findViewById(R.id.backLogin);


        btnRegister.setOnClickListener(this);
        backLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        loginClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


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
        if(ValidationHelper.checkFormRegister(editTextFullname, editTextEmail, editTextTel, editTextPassword, editTextRetypePassword)){
            String fullName = editTextFullname.getText().toString();
            String email = editTextEmail.getText().toString();
            String tel = editTextTel.getText().toString();
            String password = editTextPassword.getText().toString();
            UserDomain user = new UserDomain("",email, fullName, password, tel, "", "", "");

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                showDialog("Đăng ký thành công!","Thật tuyệt vời. Đăng nhập ngay nào!");
                                FirebaseFirestore.getInstance().collection("user").add(user);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                            }else{
                                Toast.makeText(RegisterActivity.this,"Đăng ký thất bại",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public static void showDialog(String title, String mess) {
        builder.setMessage(mess) .setTitle(title);
        AlertDialog alert = builder.create();
        alert.show();
    }
}