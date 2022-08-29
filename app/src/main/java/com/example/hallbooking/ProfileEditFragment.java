package com.example.hallbooking;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileEditFragment extends Fragment {
TextView name,fname,cnic,email,update;
EditText address,phone,password;
ProgressDialog progressDialog;
String stringname, stringfname, stringcnic, stringemail, stringaddress, stringphone, stringpassword;
DatabaseReference reference;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_edit, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Updating Your Profile");
        progressDialog.setMessage("Please Wait....");

        name = view.findViewById(R.id.usernametxt);
        fname = view.findViewById(R.id.fathername);
        cnic = view.findViewById(R.id.cnictv);
        address = view.findViewById(R.id.addresstv);
        password = view.findViewById(R.id.passtv);
        phone = view.findViewById(R.id.phonetvx);
        email = view.findViewById(R.id.email);
        update = view.findViewById(R.id.upbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


        Bundle bundle = this.getArguments();

        stringname = bundle.getString("name");
        name.setText(stringname);

        stringfname = bundle.getString("fname");
        fname.setText(stringfname);

        stringcnic = bundle.getString("cnic");
        cnic.setText(stringcnic);

        stringaddress = bundle.getString("address");
        address.setText(stringaddress);

        stringemail = bundle.getString("email");
        email.setText(stringemail);

        stringphone = bundle.getString("phone");
        phone.setText(stringphone);

        stringpassword = bundle.getString("pass");
        password.setText(stringpassword);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("address").setValue(address.getText().toString());
                reference.child("passwrod").setValue(password.getText().toString());
                reference.child("phone").setValue(phone.getText().toString());


                Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT).show();


            }
        });


        return view;
    }
}