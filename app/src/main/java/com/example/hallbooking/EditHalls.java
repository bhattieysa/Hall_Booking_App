package com.example.hallbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class EditHalls extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner,spinner2,spinner3,spinnerstat;
    EditText name,addres,phone,indoor,outdoor,capacity,desc;
    String stringname,stringaddress,stringphone,stringindoor,stringout,stringcap,stringdesc,stringpav,stringrent,stringdec;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    TextView register;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_halls);
        getSupportActionBar().hide();
        spinner = (Spinner) findViewById(R.id.spinnerpav);
        spinner2 = (Spinner) findViewById(R.id.spinnerrace);
        spinner3 = (Spinner) findViewById(R.id.spinnerdecorate);
        spinnerstat = (Spinner) findViewById(R.id.spinnerstatus);
        register = findViewById(R.id.registerbtn);

        name = findViewById(R.id.hallname);
        addres = findViewById(R.id.halladdress);
        phone = findViewById(R.id.hallphone);
        indoor = findViewById(R.id.indoor);
        outdoor = findViewById(R.id.outdoor);
        capacity = findViewById(R.id.hallcap);
        desc = findViewById(R.id.fooditems);

        spinner.setOnItemSelectedListener(EditHalls.this);
        spinner2.setOnItemSelectedListener(EditHalls.this);
        spinner3.setOnItemSelectedListener(EditHalls.this);
        spinnerstat.setOnItemSelectedListener(EditHalls.this);
        Intent intent = getIntent();


        name.setText(intent.getStringExtra("name"));
        addres.setText(intent.getStringExtra("address"));
        phone.setText(intent.getStringExtra("phone"));
        capacity.setText(intent.getStringExtra("cap"));
        indoor.setText(intent.getStringExtra("in"));
        outdoor.setText(intent.getStringExtra("out"));
        desc.setText(intent.getStringExtra("desc"));
        String stats = intent.getStringExtra("stat");



        progressDialog = new ProgressDialog(EditHalls.this);
        progressDialog.setMessage("PLease Wait...");
        progressDialog.setTitle("Updating");
        progressDialog.setCanceledOnTouchOutside(false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Halls").child(intent.getStringExtra("name"));










        List<String> categories = new ArrayList<String>();
        categories.add("YES");
        categories.add("NO");

        //spinner1
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        //spinner2


        List<String> rent = new ArrayList<String>();
        rent.add("YES");
        rent.add("NO");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rent);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);


        //spinner3
        List<String> deco = new ArrayList<String>();
        deco.add("YES");
        deco.add("NO");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deco);
        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner3.setAdapter(dataAdapter3);



        //spinner4
        List<String> stat = new ArrayList<String>();
        stat.add("Booked");
        stat.add("Not Booked");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stat);
        // Drop down layout style - list view with radio button
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerstat.setAdapter(dataAdapter4);


        if (stats.equals("Not Booked")) {
            spinnerstat.setSelection(1);

        }else {
            spinnerstat.setSelection(0);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                stringname = name.getText().toString().trim();
                stringaddress = addres.getText().toString().trim();
                stringindoor = indoor.getText().toString();
                stringout = outdoor.getText().toString();
                stringcap = capacity.getText().toString().trim();
                stringdec = String.valueOf(spinner3.getSelectedItem());
                stringpav = String.valueOf(spinner.getSelectedItem());
                stringphone = phone.getText().toString().trim();
                stringrent = String.valueOf(spinner2.getSelectedItem());
                stringdesc = desc.getText().toString().trim();



                if (TextUtils.isEmpty(stringname)){
                    name.setError("Name is Required");
                    return;
                }

                else if (TextUtils.isEmpty(stringaddress)){
                    addres.setError("Address is Required");
                    return;
                }


                else if (TextUtils.isEmpty(stringphone)){
                    phone.setError("Phone is Required");
                    return;
                }

                else if (TextUtils.isEmpty(stringcap)){
                    capacity.setError("Capacity is Required");
                    return;
                }

                else if (TextUtils.isEmpty(stringindoor)){
                    indoor.setError("Count is Required");
                    return;
                }

                else if (TextUtils.isEmpty(stringout)){
                    outdoor.setError("Count is Required");
                    return;
                }

                else if (TextUtils.isEmpty(stringdesc)){
                    desc.setError("Description is Required");
                    return;
                }

                databaseReference.child("hall_Name").setValue(name.getText().toString());
                databaseReference.child("hall_address").setValue(addres.getText().toString());
                databaseReference.child("hall_phone").setValue(phone.getText().toString());
                databaseReference.child("indoor_Halls").setValue(indoor.getText().toString());
                databaseReference.child("outdoor_Halls").setValue(outdoor.getText().toString());
                databaseReference.child("hall_Capacity").setValue(capacity.getText().toString());
                databaseReference.child("hall_Desc").setValue(desc.getText().toString());
                databaseReference.child("rent_Car").setValue(spinner2.getSelectedItem());
                databaseReference.child("photo_Video").setValue(spinner.getSelectedItem());
                databaseReference.child("extra_Decoration").setValue(spinner3.getSelectedItem());
                databaseReference.child("status").setValue(spinnerstat.getSelectedItem());

                Toast.makeText(EditHalls.this, "updated", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();




            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}