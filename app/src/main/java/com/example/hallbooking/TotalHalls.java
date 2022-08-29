package com.example.hallbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TotalHalls extends AppCompatActivity {
    RecyclerView recyclerView;
    HallAdapter adapter;
    ConstraintLayout constraintLayout;
    ArrayList<HallRegistrationModel> list;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    TextView noresult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_halls);

        recyclerView = findViewById(R.id.rechall);
        constraintLayout = findViewById(R.id.contlayit);
        noresult = findViewById(R.id.noresults);
        recyclerView.setLayoutManager(new LinearLayoutManager(TotalHalls.this));

        recyclerView.hasFixedSize();

        progressDialog=new ProgressDialog(TotalHalls.this);
        progressDialog.setMessage("Loading...!");
        database=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Halls");
        Toast.makeText(this, "Swipe Left To Delete Any Hall", Toast.LENGTH_LONG).show();

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
                    noresult.setVisibility(View.VISIBLE);
                } else {
                    adapter = new HallAdapter(list, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                    ItemTouchHelper helper = new ItemTouchHelper(callback);
                    helper.attachToRecyclerView(recyclerView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

    }
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Snackbar snackbar = Snackbar.make(constraintLayout,"Item Deleted",Snackbar.LENGTH_LONG);
            snackbar.show();
            adapter.deleteItem(viewHolder.getAdapterPosition());

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
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
        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String query) {

                ArrayList<HallRegistrationModel> arrayList = new ArrayList<>();
                for(HallRegistrationModel s : list){
                    if (s.getHall_Name().toLowerCase().contains(query.toLowerCase())){
                        arrayList.add(s);
                    }

                }
                HallAdapter adapter = new HallAdapter(arrayList,getApplicationContext());
                adapter.filterlist(arrayList);
                recyclerView.setAdapter(adapter);
                Log.d("erry", "onTextChanged: "+arrayList);



    }

}