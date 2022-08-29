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
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.ArrayList;
import java.util.List;

public class NewHalls extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
     Spinner spinner,spinner2,spinner3;
     EditText name,addres,phone,indoor,outdoor,capacity,desc;
     String stringname,stringaddress,stringphone,stringindoor,stringout,stringcap,stringdesc,stringpav,stringrent,stringdec;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    TextView register;
    Uri filepath;
    Bitmap bitmap;
    FirebaseDatabase firebaseDatabase;
    ImageView imageView;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_new_halls);
        getSupportActionBar().hide();
         progressDialog = new ProgressDialog(NewHalls.this);
         progressDialog.setMessage("PLease Wait...");
         progressDialog.setTitle("Registering");
         progressDialog.setCanceledOnTouchOutside(false);


         spinner = (Spinner) findViewById(R.id.spinner);
         spinner2 = (Spinner) findViewById(R.id.spinnerrent);
         spinner3 = (Spinner) findViewById(R.id.spinnerdecor);
         register = findViewById(R.id.registerbtn);
         imageView = findViewById(R.id.hallimage);
         name = findViewById(R.id.hallname);
         addres = findViewById(R.id.halladdress);
         phone = findViewById(R.id.hallphone);
         indoor = findViewById(R.id.indoor);
         outdoor = findViewById(R.id.outdoor);
         capacity = findViewById(R.id.hallcap);
         desc = findViewById(R.id.fooditems);

         spinner.setOnItemSelectedListener(NewHalls.this);
         spinner2.setOnItemSelectedListener(NewHalls.this);
         spinner3.setOnItemSelectedListener(NewHalls.this);


         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference("Jobs");


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

         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Dexter.withActivity(NewHalls.this)
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



                 FirebaseStorage storage = FirebaseStorage.getInstance();
                 final StorageReference uploader = storage.getReference("Halls")
                         .child(filepath.getLastPathSegment());;

                 uploader.putFile(filepath)
                         .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                             @Override
                             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                 uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                     @Override
                                     public void onSuccess(Uri uri) {

                                         progressDialog.dismiss();
                                         FirebaseDatabase db = FirebaseDatabase.getInstance();
                                         DatabaseReference root = db.getReference("Halls");

                                         HallRegistrationModel registrationModel = new HallRegistrationModel(
                                               stringname,stringaddress,stringphone,stringindoor,
                                                 stringout,stringcap,stringdesc,stringrent,stringpav,stringdec,
                                                 uri.toString(),"Not Booked");


                                         root.child(name.getText().toString()).setValue(registrationModel);

                                        // root.push().setValue(registrationModel);

                                         name.setText("");
                                         addres.setText("");
                                         phone.setText("");
                                         indoor.setText("");
                                         outdoor.setText("");
                                         capacity.setText("");
                                         desc.setText("");
                                         imageView.setImageResource(R.drawable.camera);
                                         Toast.makeText(getApplicationContext(), "Hall Registered Successfully", Toast.LENGTH_LONG).show();
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