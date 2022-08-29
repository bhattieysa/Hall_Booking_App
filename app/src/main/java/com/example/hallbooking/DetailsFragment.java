package com.example.hallbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class DetailsFragment extends AppCompatActivity {
    ImageView imageView;
    TextView name,address,phone,cap,dec,desc,in,out,race,pave;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        getSupportActionBar().hide();

        imageView = findViewById(R.id.prflimageflimage);
        name = findViewById(R.id.ettitle);
        address = findViewById(R.id.etloc);
        phone = findViewById(R.id.etphone);
        cap = findViewById(R.id.ethalcap);
        dec = findViewById(R.id.etextray);
        desc = findViewById(R.id.etdesc);
        in = findViewById(R.id.etin);
        out = findViewById(R.id.etout);
        race = findViewById(R.id.etrace);
        pave = findViewById(R.id.ethalpav);






        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        Glide.with(getApplicationContext()).load(image).placeholder(R.drawable.camera).into(imageView);

        name.setText(intent.getStringExtra("name"));
        address.setText(intent.getStringExtra("address"));
        phone.setText(intent.getStringExtra("contact"));
        cap.setText(intent.getStringExtra("cap"));
        in.setText(intent.getStringExtra("in"));
        out.setText(intent.getStringExtra("out"));
        dec.setText(intent.getStringExtra("dec"));
        race.setText(intent.getStringExtra("rent"));
        pave.setText(intent.getStringExtra("pav"));
        desc.setText(intent.getStringExtra("desc"));








    }
}