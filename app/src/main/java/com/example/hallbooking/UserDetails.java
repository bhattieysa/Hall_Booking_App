package com.example.hallbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetails extends AppCompatActivity {

    ImageView imageView;
    TextView name,fname,cnic,address,phone,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);



        imageView = findViewById(R.id.user_imageview);
        name = findViewById(R.id.nametxtv);
        fname = findViewById(R.id.fname_textview);
        cnic = findViewById(R.id.cnictxt);
        address = findViewById(R.id.addressdtxt);
        phone = findViewById(R.id.phone_textview);
        email = findViewById(R.id.email_textview);


        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        Glide.with(this).load(image).placeholder(R.drawable.camera).into(imageView);
        name.setText(intent.getStringExtra("name"));
        fname.setText(intent.getStringExtra("fname"));
        cnic.setText(intent.getStringExtra("cnic"));
        address.setText(intent.getStringExtra("addrress"));
        phone.setText(intent.getStringExtra("phone"));
        email.setText(intent.getStringExtra("email"));



    }
}