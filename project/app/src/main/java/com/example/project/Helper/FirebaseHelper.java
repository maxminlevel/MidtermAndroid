package com.example.project.Helper;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.project.Activity.LoginActivity;
import com.example.project.Activity.RegisterActivity;
import com.example.project.Domain.CategoryDomain;
import com.example.project.Domain.UserDomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FirebaseHelper
{
    //Instance will be created at load time
    private static FirebaseHelper instance;
    public String string;
    public FirebaseAuth auth;
    public FirebaseFirestore db;

    //Creating private constructor
    private FirebaseHelper()
    {
        string = "Singleton Class Java";
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    //Declaring static method
    public static FirebaseHelper getInstance()
    {
        if (instance == null)
        {
            //synchronized block to remove overhead
            synchronized (FirebaseHelper.class)
            {
                if(instance==null)
                {
                    // if instance is null, initialize
                    instance = new FirebaseHelper();
                }
            }
        }
        return instance;
    }

    public static void main(String args[])
    {
        FirebaseHelper text = FirebaseHelper.getInstance();
        //original string
        System.out.println("Original String:");
        System.out.println(text.string);

        //text in upper case
        System.out.println("String in Upper Case:");
        text.string = (text.string).toUpperCase();
        System.out.println(text.string);
    }

    private boolean register_flag;
    public boolean register(UserDomain user){
        auth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        register_flag = true;
                        CollectionReference userColl = db.collection("user");
                        userColl.add(user);
                    }
                    else{
                        register_flag = false;
                    }
                }
            });
        return  register_flag;
    }

    public static ArrayList<CategoryDomain> getCategoryList(){
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        categoryList.add(new CategoryDomain("Cơm", "com_suon_cat"));
        categoryList.add(new CategoryDomain("Bún Phở", "bun_pho_cat"));
        categoryList.add(new CategoryDomain("Lẩu", "lau_cat"));
        categoryList.add(new CategoryDomain("Ăn Nhanh", "thuc_an_nhanh_cat"));
        categoryList.add(new CategoryDomain("Bún Chả", "bun_cha_cat"));
        CollectionReference category = instance.db.collection("food_category");
        // này chưa xong
        return categoryList;
    }
}