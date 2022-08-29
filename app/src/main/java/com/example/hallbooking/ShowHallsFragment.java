package com.example.hallbooking;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowHallsFragment extends Fragment {

    RecyclerView recyclerView;
    UserHallAdapter adapter;
    TextView noproducts;
    ProgressDialog progressDialog;
    ArrayList<HallRegistrationModel> list;
    DatabaseReference databaseReference;
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_halls, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.rechalluser);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("PLease Wait....!!");
        progressDialog.setCanceledOnTouchOutside(false);
        noproducts = view.findViewById(R.id.noproducts);
        progressDialog.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        database=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Halls");


        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
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
                    adapter = new UserHallAdapter(list, getActivity());
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.searchview);

        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.
                getActionView();
        searchView.setQueryHint("Search By Name");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                processsearch(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void processsearch(String query) {

        ArrayList<HallRegistrationModel> arrayList = new ArrayList<>();
        for(HallRegistrationModel s : list){
            if (s.getHall_Name().toLowerCase().contains(query.toLowerCase())){
                arrayList.add(s);
            }

        }
        HallAdapter adapter = new HallAdapter(arrayList,getActivity());
        adapter.filterlist(arrayList);
        recyclerView.setAdapter(adapter);
        Log.d("erry", "onTextChanged: "+arrayList);



    }



}