package com.example.hallbooking;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;


public class HallsFragment extends Fragment {
    UserFavoritesAdapter adapter;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    TextView noproducts;
    ArrayList<HallRegistrationModel> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views =  inflater.inflate(R.layout.fragment_halls, container, false);

        recyclerView = views.findViewById(R.id.recfav);
        noproducts = views.findViewById(R.id.noproducts);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("PLease Wait....!!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Favorites").child(firebaseUser.getUid());

        recyclerView.hasFixedSize();

        progressDialog.show();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String Hall_Name = dataSnapshot.child("hall_Name").getValue(String.class);
                    String Hall_address = dataSnapshot.child("hall_address").getValue(String.class);
                    String Hall_phone = dataSnapshot.child("hall_phone").getValue(String.class);
                    String Indoor_Halls = dataSnapshot.child("indoor_Halls").getValue(String.class);
                    String Outdoor_Halls = dataSnapshot.child("outdoor_Halls").getValue(String.class);
                    String HAll_Capacity = dataSnapshot.child("hAll_Capacity").getValue(String.class);
                    String Hall_Desc = dataSnapshot.child("hall_Desc").getValue(String.class);
                    String Rent_Car = dataSnapshot.child("rent_Car").getValue(String.class);
                    String Photo_Video = dataSnapshot.child("photo_Video").getValue(String.class);
                    String Extra_Decoration = dataSnapshot.child("extra_Decoration").getValue(String.class);
                    String Image = dataSnapshot.child("image").getValue(String.class);
                    String Status = dataSnapshot.child("status").getValue(String.class);


                    HallRegistrationModel model = new
                            HallRegistrationModel(Hall_Name,Hall_address,Hall_phone,Indoor_Halls,Outdoor_Halls,
                            HAll_Capacity,Hall_Desc,Rent_Car,Photo_Video,Extra_Decoration,Image,Status);


                    list.add(model);
                }
                if (list.isEmpty()) {
                    progressDialog.dismiss();
                    noproducts.setVisibility(View.VISIBLE);
                } else {
                    adapter = new UserFavoritesAdapter(list, getActivity());
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });
        return views;
    }

}