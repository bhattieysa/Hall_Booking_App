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

public class HallDetails extends AppCompatActivity {
ImageView imageView;
TextView name,address,phone,cap,dec,desc,in,out,race,pave;
FloatingActionButton fab;
String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_details);
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
        fab = findViewById(R.id.edthal);





        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        Glide.with(getApplicationContext()).load(image).placeholder(R.drawable.camera).into(imageView);

        name.setText(intent.getStringExtra("name"));
        address.setText(intent.getStringExtra("address"));
        phone.setText(intent.getStringExtra("phone"));
        cap.setText(intent.getStringExtra("cap"));
        in.setText(intent.getStringExtra("in"));
        out.setText(intent.getStringExtra("out"));
        dec.setText(intent.getStringExtra("dec"));
        race.setText(intent.getStringExtra("rent"));
        pave.setText(intent.getStringExtra("pav"));
        desc.setText(intent.getStringExtra("desc"));
        status = intent.getStringExtra("stat");



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditHalls.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("address",address.getText().toString());
                intent.putExtra("phone",phone.getText().toString());
                intent.putExtra("cap",cap.getText().toString());
                intent.putExtra("in",in.getText().toString());
                intent.putExtra("out",out.getText().toString());
                intent.putExtra("desc",desc.getText().toString());
                intent.putExtra("rent",race.getText().toString());
                intent.putExtra("dec",dec.getText().toString());
                intent.putExtra("pav",pave.getText().toString());
                intent.putExtra("image",image);
                intent.putExtra("stat",status);
                startActivity(intent);
            }
        });




    }
}