package com.example.fitusionsports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitusionsports.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email_login, password_login;
    private Button login;
    private TextView signupredirect;
    private SignInButton googleSignInButton;
    private View customsigninbutton;
    private ActivityLoginBinding binding;
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        login = findViewById(R.id.login);
        signupredirect = findViewById(R.id.signupRedirect);
        googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setVisibility(View.GONE);
        customsigninbutton = findViewById(R.id.custom_sign_in_button);
        customsigninbutton.setVisibility(View.VISIBLE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email_login.getText().toString().trim();
                String txt_password = password_login.getText().toString().trim();
                if (!txt_email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
                    if (!txt_password.isEmpty()) {
                        auth.signInWithEmailAndPassword(txt_email, txt_password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        password_login.setError("Passowrd cannot be empty");
                    }
                } else if (txt_email.isEmpty()) {
                    email_login.setError("Email cannot be empty");

                } else {
                    email_login.setError("Please enter valid email");
                }

            }
        });
        signupredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        checkUser();
        binding.customSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // begin google sign in
                signin();
            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser=auth.getCurrentUser();
        if(firebaseUser!=null){
            Log.d(TAG,"checkUser:Already logged in");
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
            finish();
        }
    }

    private void signin() {
        Log.d(TAG, "onClick:begin Google SignIn");
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogleAcoount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAcoount:begin firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success
                        Log.d(TAG, "onSuccess:Logged In");
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();
                        Log.d(TAG, "onSuccess" + email);
                        Log.d(TAG, "onSuccess" + uid);
                        if (authResult.getAdditionalUserInfo().isNewUser()) {
                            //user is new -Account created
                            Toast.makeText(LoginActivity.this, "Account Created " + email, Toast.LENGTH_SHORT).show();
                        } else {
                            //existing user logged in
                            Log.d(TAG, "onSuccess : Existing user \n" + email);
                            Toast.makeText(LoginActivity.this, " Existing user \n" + email, Toast.LENGTH_SHORT).show();
                        }
                        //start profile activity
                        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure:Login Failed" + e.getMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Log.d(TAG,"onActivityResult:Google Signin intent result");
        }
        Task<GoogleSignInAccount> accountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
        try{
            GoogleSignInAccount account=accountTask.getResult(ApiException.class);
            firebaseAuthWithGoogleAcoount(account);
        }catch (Exception e){
            Log.d(TAG,"onActivityResult"+e.getMessage());
        }
    }
}