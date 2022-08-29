package com.example.hallbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserLogin extends AppCompatActivity {
    TextView forgot,signup;
    EditText email, password;
    TextView login, ID;
    String emails, passwords;
    ImageView imageView;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    String admin,id;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        getSupportActionBar().hide();


        forgot = findViewById(R.id.textView10);
        signup = findViewById(R.id.textView9);



        email = findViewById(R.id.et2);
        password = findViewById(R.id.et3);
        imageView = findViewById(R.id.passwordtogg);
        login = findViewById(R.id.loginbtn);
        progressDialog = new ProgressDialog(UserLogin.this);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Please Wait....");
        fAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Signup.class));
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowHidePass();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emails = email.getText().toString().trim();
                passwords = password.getText().toString().trim();


                if (TextUtils.isEmpty(emails) && emails.equals(admin)) {
                    email.setError("Email Required");
                    return;
                }

                if (TextUtils.isEmpty(passwords)) {
                    email.setError("Password Required");
                    return;
                }

                progressDialog.show();


                if (emails.equals("adminuser@gmail.com")) {
                    progressDialog.dismiss();
                    Toast.makeText(UserLogin.this, "Invalid Email Please Try Again", Toast.LENGTH_SHORT).show();


                }else {


                    fAuth.signInWithEmailAndPassword(emails, passwords)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Login Succesfull", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = fAuth.getCurrentUser();
                                        updateUI(user);
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), UserHome.class);
                                        intent.putExtra("email", user.getEmail());
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Try Again! Login Failed", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            });
                }

            }
        });

    }
    //Checking if the user is already signed in or not
    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                Intent intent = new Intent(UserLogin.this, UserHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        fAuth.addAuthStateListener(authStateListener);
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user) {

    }

    private void ShowHidePass() {
        if(imageView.getId()==R.id.passwordtogg){

            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){


                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{

                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
    }
