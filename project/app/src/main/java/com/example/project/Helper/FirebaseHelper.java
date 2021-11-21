package com.example.project.Helper;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Domain.CategoryDomain;
import com.example.project.Domain.UserDomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper
{
    //Instance will be created at load time
    private static FirebaseHelper instance;
    public String string;
    public FirebaseAuth auth;
    public FirebaseFirestore db;

    private CollectionReference userCol;
    private CollectionReference foodCol;
    private CollectionReference foodCategoryCol;
    private CollectionReference storeCol;
    private CollectionReference food_storeCol;
    private CollectionReference connectionCol;

    //Creating private constructor
    private FirebaseHelper()
    {
        string = "Singleton Class Java";
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userCol = db.collection("user");
        foodCol = db.collection("food");
        foodCategoryCol = db.collection("food_category");
        storeCol = db.collection("store");
        food_storeCol = db.collection("food_store");
        //connectionCol = db.collection("connection");
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
}