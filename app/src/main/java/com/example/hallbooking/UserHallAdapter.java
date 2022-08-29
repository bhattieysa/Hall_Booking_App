package com.example.hallbooking;

import static android.content.Context.MODE_PRIVATE;
import static com.example.hallbooking.R.drawable.fav_image;
import static com.example.hallbooking.R.drawable.fav_image_fill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserHallAdapter extends RecyclerView.Adapter<UserHallAdapter.myViewHolder> {
    ArrayList<HallRegistrationModel> list;
    Context ctx;
    boolean isFavourite;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView number;

    public UserHallAdapter(ArrayList<HallRegistrationModel> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public UserHallAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userhall_row,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHallAdapter.myViewHolder holder, int position) {
        HallRegistrationModel model = list.get(position);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Favorites");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        holder.image = model.getImage();
        Glide.with(ctx).load(holder.image).placeholder(R.drawable.animated_progress).into(holder.hallimg);

        holder.hname.setText(model.getHall_Name());
        holder.hcap.setText(model.getHAll_Capacity());
        holder.hcontact.setText(model.getHall_phone());
        holder.desc = model.getHall_Desc();
        holder.in = model.getIndoor_Halls();
        holder.out = model.getOutdoor_Halls();
        holder.dec = model.getExtra_Decoration();
        holder.pav = model.getPhoto_Video();
        holder.rent = model.getRent_Car();
        holder.address = model.getHall_address();

        holder.favimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.favimg.setBackgroundResource(fav_image_fill);
                //storing user data to firebase realtime database
                HallRegistrationModel haalmodel = new HallRegistrationModel
                        (holder.hname.getText().toString(),holder.address,
                                holder.hcontact.getText().toString(), holder.in, holder.out,
                                holder.hcap.getText().toString(), holder.desc, holder.rent,
                                holder.pav, holder.dec, holder.image, "");
                FirebaseDatabase.getInstance().getReference("Favorites")
                        .child(firebaseUser.getUid()).child(holder.hname.getText().toString()).setValue(haalmodel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ctx, "Added To Favorites", Toast.LENGTH_SHORT).show();

                            }
                        });





            }
        });


        holder.seedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,DetailsFragment.class);
                intent.putExtra("name",holder.hname.getText().toString());
                intent.putExtra("cap",holder.hcap.getText().toString());
                intent.putExtra("contact",holder.hcontact.getText().toString());
                intent.putExtra("desc",holder.desc);
                intent.putExtra("in",holder.in);
                intent.putExtra("out",holder.out);
                intent.putExtra("dec",holder.dec);
                intent.putExtra("pav",holder.pav);
                intent.putExtra("rent",holder.rent);
                intent.putExtra("address",holder.address);
                intent.putExtra("image",holder.image);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView hallimg, favimg;
        TextView hname, hcap, hcontact, seedetails;
        String image, address, desc, in, out, rent, dec, pav;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            hallimg = itemView.findViewById(R.id.profile_image);
            favimg = itemView.findViewById(R.id.favBtn);
            hname = itemView.findViewById(R.id.hallname);
            hcap = itemView.findViewById(R.id.hallcapacity);
            hcontact = itemView.findViewById(R.id.hallcntct);
            seedetails = itemView.findViewById(R.id.seedet);
        }
    }
}




