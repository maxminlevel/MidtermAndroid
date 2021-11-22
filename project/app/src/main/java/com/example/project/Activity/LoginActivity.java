package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Domain.FoodDomain;
import com.example.project.Domain.UserDomain;
import com.example.project.Helper.FirebaseHelper;
import com.example.project.Helper.Vatidation;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.io.Console;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextPassword, editTextEmail;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_pref_account";
    private static final String userName = "user_name";
    private static final String email = "email";
    private static final String password = "password";
    UserDomain userDomain;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        TextView registerBtn = findViewById(R.id.registerBtn);
        CircularProgressButton btnLogin = findViewById(R.id.cirLoginButton);
        btnLogin.setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if(Vatidation.checkFormLogin(editTextEmail, editTextPassword)) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                CollectionReference userColl = db.collection("user");
                                Task<QuerySnapshot> query = userColl.whereEqualTo("email",email).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            List<DocumentSnapshot> list_user = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot d : list_user) {
                                                Log.i("I", "onSuccessUser: ");
                                                 userDomain = new UserDomain(
                                                        d.getId(),
                                                        d.getString("email"),
                                                        d.getString("fullName"),
                                                        d.getString("password"),
                                                        d.getString("tel"),
                                                        d.getString("birthday"),
                                                        d.getString("gender"),
                                                        d.getString("avatar")
                                                );


                                            }

                                        }

                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("fullName", userDomain.getFullName());
                                        editor.putString("email", userDomain.getEmail());
                                        editor.putString("avatar", userDomain.getAvatar());
                                        editor.putString("birthday", userDomain.getBirthday());
                                        editor.apply();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        Toast.makeText(LoginActivity .this,"Đăng nhập thành công!",Toast.LENGTH_LONG).show();
                                }});
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}