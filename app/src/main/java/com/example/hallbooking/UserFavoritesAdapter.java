package com.example.hallbooking;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserFavoritesAdapter extends RecyclerView.Adapter<UserFavoritesAdapter.myViewHolder> {
    ArrayList<HallRegistrationModel> list;
    Context context;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public UserFavoritesAdapter(ArrayList<HallRegistrationModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UserFavoritesAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userhall_row,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFavoritesAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Favorites");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        HallRegistrationModel model = list.get(position);
        holder.hname.setText(model.getHall_Name());
        holder.desc = model.getHall_Desc();
        holder.hcontact.setText(model.getHall_phone());
        holder.image = model.getImage();
        Glide.with(context).load(holder.image).placeholder(R.drawable.animated_progress).into(holder.hallimg);
        holder.pav = model.getPhoto_Video();
        holder.hcontact.setText(model.getHAll_Capacity());
        holder.in = model.getIndoor_Halls();
        holder.out = model.getOutdoor_Halls();
        holder.rent = model.getRent_Car();
        holder.address = model.getHall_address();
        holder.dec = model.getExtra_Decoration();


        holder.favimg.setBackgroundResource(R.drawable.fav_image_fill);
        holder.favimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            list.remove(position);
            notifyItemRemoved(position);
            databaseReference.child(firebaseUser.getUid()).child(holder.hname.getText().toString()).removeValue();

            }
        });
        holder.seedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailsFragment.class);
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
                context.startActivity(intent);

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






