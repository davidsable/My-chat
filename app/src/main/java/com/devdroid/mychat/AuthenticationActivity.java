package com.devdroid.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.devdroid.mychat.databinding.ActivityAuthenticationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticationActivity extends AppCompatActivity {
ActivityAuthenticationBinding binding;
String Name,Email,password;
DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAuthenticationBinding.inflate(getLayoutInflater());
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        setContentView(binding.getRoot());
        binding.login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Email= binding.Email.getText().toString();
                password= binding.password.getText().toString();


              login();

            }
        });


        binding.signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Name= binding.Name.getText().toString();
                Email= binding.Email.getText().toString();
                password= binding.password.getText().toString();


                SignUp();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(AuthenticationActivity. this, MainActivity.class));
            finish();
        }
    }

    private void login() {
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(Email.trim(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(AuthenticationActivity. this, MainActivity.class));
                        finish();

                    }
                });

    }
        private void SignUp() {
            FirebaseAuth
                    .getInstance()
                    .createUserWithEmailAndPassword(Email.trim(),password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            UserProfileChangeRequest userProfileChangeRequest= new UserProfileChangeRequest.Builder().setDisplayName(Name).build();
                            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                            firebaseUser.updateProfile(userProfileChangeRequest);
                            Usermodel usermodel=new Usermodel(FirebaseAuth.getInstance().getUid(),Name,Email,password);
                            databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(usermodel);
                            startActivity( new Intent(AuthenticationActivity.this,MainActivity.class));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AuthenticationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

    }
}