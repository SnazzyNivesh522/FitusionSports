package com.example.fitusionsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email_login,password_login;
    private Button login;
    private TextView signupredirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        email_login=findViewById(R.id.email_login);
        password_login=findViewById(R.id.password_login);
        login=findViewById(R.id.login);
        signupredirect=findViewById(R.id.signupRedirect);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email=email_login.getText().toString().trim();
                String txt_password=password_login.getText().toString().trim();
                if(!txt_email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()){
                    if (!txt_password.isEmpty()) {
                        auth.signInWithEmailAndPassword(txt_email,txt_password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }else {
                        password_login.setError("Passowrd cannot be empty");
                    }
                } else if (txt_email.isEmpty()) {
                    email_login.setError("Email cannot be empty");
                    
                }else{email_login.setError("Please enter valid email");}

            }
        });
        signupredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}