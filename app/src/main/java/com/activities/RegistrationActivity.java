package com.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.examole.bestdeal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText name,password,email;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

       // auth.getFirebaseAuthSettings();

//        if(auth.getCurrentUser()!= null) {
//            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
//            finish();
//        }



        auth =FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("fistTime",true);

        if (isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void Signup(View view) {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();


        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Enter Email address", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;

        }
        if(userPassword.length()<6){
            Toast.makeText(this, "Password to short, enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(RegistrationActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(RegistrationActivity.this, "UnSuccessful"+task.getException(), Toast.LENGTH_SHORT).show();
                        }



                    }
                });


     //   startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
    }

    public void Signin(View view) {
        startActivity(new Intent(RegistrationActivity.this, loginActivity.class));

    }
}