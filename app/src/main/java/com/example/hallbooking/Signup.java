package com.example.hallbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

public class Signup extends AppCompatActivity {
    EditText fullname, fathername, phone, address, cnic, email, password;
    TextView signup;
    String cnames, fnames, emails, passs, phones, addresses, cnics;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth fireAuth;
    ProgressDialog progressDialog;
    Uri filepath;
    Bitmap bitmap;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        fullname = findViewById(R.id.username);
        fathername = findViewById(R.id.userfname);
        phone = findViewById(R.id.userphone);
        address = findViewById(R.id.useraddress);
        cnic = findViewById(R.id.usercnic);
        email = findViewById(R.id.useremail);
        password = findViewById(R.id.userpass);
        imageView = findViewById(R.id.pflimage);
        signup = findViewById(R.id.Indusignupbtn);



        progressDialog = new ProgressDialog(Signup.this);
        progressDialog.setMessage("Loading...Please Wait");

        fireAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(Signup.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image File"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnames = fullname.getText().toString();
                emails = email.getText().toString();
                passs = password.getText().toString();
                phones = phone.getText().toString();
                addresses = address.getText().toString();
                cnics = cnic.getText().toString();
                fnames = fathername.getText().toString();
                

                if (fathername.getText().toString().trim().equalsIgnoreCase("")) {
                    fathername.setError("This field can not be blank");
                    return;
                }

                if (password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError("This field can not be blank");
                    return;
                }
                if (passs.length() < 7) {
                    password.setError("Password Must Be Greater Than 7 Characters");
                }

                if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("This field can not be blank");
                    return;
                }
                if (cnic.getText().toString().trim().equalsIgnoreCase("")) {
                    cnic.setError("This field can not be blank");
                    return;
                }
                if (address.getText().toString().trim().equalsIgnoreCase("")) {
                    address.setError("This field can not be blank");
                    return;
                }
                if (phone.getText().toString().trim().equalsIgnoreCase("")) {
                    phone.setError("This field can not be blank");
                    return;
                }
                if (fullname.getText().toString().trim().equalsIgnoreCase("")) {
                    fullname.setError("This field can not be blank");
                    return;
                }


                progressDialog.show();


                fireAuth.createUserWithEmailAndPassword(emails, passs).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        startActivity(new Intent(getApplicationContext(),UserLogin.class));
                        progressDialog.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Signup.this, "Error !" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                FirebaseStorage storage = FirebaseStorage.getInstance();
                final StorageReference uploader = storage.getReference("Users")
                        .child(filepath.getLastPathSegment());

                uploader.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        progressDialog.dismiss();
                                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                                        DatabaseReference root = db.getReference("Users");

                                        UserModel obj = new UserModel(
                                               cnames,fnames,emails,passs,cnics,addresses,phones,
                                                uri.toString());
                                        root.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(obj);

                                        fullname.setText("");
                                        email.setText("");
                                        fathername.setText("");
                                        cnic.setText("");
                                        phone.setText("");
                                        address.setText("");
                                        password.setText("");
                                        imageView.setImageResource(R.drawable.camera);
                                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded :" + (int) percent + " %");
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

        }