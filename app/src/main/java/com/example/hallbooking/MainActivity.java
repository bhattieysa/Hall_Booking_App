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

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.et2);
        password = findViewById(R.id.et3);
        imageView = findViewById(R.id.passwordtogg);
        login = findViewById(R.id.loginbtn);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Please Wait....");
        fAuth = FirebaseAuth.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowHidePass();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emails = email.getText().toString().trim();
                passwords = password.getText().toString().trim();


                if (TextUtils.isEmpty(emails)) {
                    email.setError("Email Required");
                    return;
                }

                if (TextUtils.isEmpty(passwords)) {
                    email.setError("Password Required");
                    return;
                }

                progressDialog.show();
                if (emails.equals("adminuser@gmail.com")) {

                    fAuth.signInWithEmailAndPassword(emails, passwords)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Login Succesfull", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = fAuth.getCurrentUser();
                                        updateUI(user);
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                                        intent.putExtra("email", user.getEmail());
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Try Again! Login Failed", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "You Are Not An Admin", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(MainActivity.this, AdminHome.class);
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