package com.example.hallbooking;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    CircleImageView imageView;
    TextView name,fname,cnic,address,phone,email,profileedit;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    String image;
    String passswords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Users");


        imageView = view.findViewById(R.id.user_imageview);
        name = view.findViewById(R.id.nametxtv);
        fname = view.findViewById(R.id.fname_textview);
        cnic = view.findViewById(R.id.cnictxt);
        address = view.findViewById(R.id.addressdtxt);
        phone = view.findViewById(R.id.phone_textview);
        email = view.findViewById(R.id.email_textview);
        profileedit = view.findViewById(R.id.profileedit);


        progressDialog.show();

        Query query = mDatabase.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String names = ""+ ds.child("fullName").getValue();
                    String fnames = ""+ ds.child("fatherName").getValue();
                    String emails = ""+ ds.child("email").getValue();
                    String addresss = ""+ ds.child("address").getValue();
                    String cnics = ""+ ds.child("cnic").getValue();
                    String phones = ""+ ds.child("phone").getValue();
                    image = ""+ ds.child("image").getValue();
                    passswords = ""+ ds.child("passwrod").getValue();
                    name.setText(names);
                    fname.setText(fnames);
                    cnic.setText(cnics);
                    address.setText(addresss);
                    phone.setText(phones);
                    email.setText(emails);
                    Glide.with(getActivity()).load(image).placeholder(R.drawable.placeholder).into(imageView);
                    imageView.setClipToOutline(true);
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });
        profileedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ProfileEditFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name",name.getText().toString());
                bundle.putString("fname",fname.getText().toString());
                bundle.putString("address",address.getText().toString());
                bundle.putString("cnic",cnic.getText().toString());
                bundle.putString("phone",phone.getText().toString());
                bundle.putString("email",email.getText().toString());
                bundle.putString("pass",passswords);
                bundle.putString("image",image);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.hostprofile, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}