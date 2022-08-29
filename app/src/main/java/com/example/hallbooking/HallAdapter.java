package com.example.hallbooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HallAdapter extends  RecyclerView.Adapter<HallAdapter.MyViewHolder> {
    ArrayList<HallRegistrationModel> list;
    Context ctx;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    public HallAdapter(ArrayList<HallRegistrationModel> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public HallAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hall_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallAdapter.MyViewHolder holder, int position) {

        HallRegistrationModel model = list.get(position);
        holder.name.setText(model.getHall_Name());
        holder.address.setText(model.getHall_address());
        holder.phone.setText(model.getHall_phone());
        holder.cap.setText(model.getHAll_Capacity());
        holder.in.setText(model.getIndoor_Halls());
        holder.out.setText(model.getOutdoor_Halls());
        holder.desc.setText(model.getHall_Desc());
        holder.rent.setText(model.getRent_Car());
        holder.dec.setText(model.getExtra_Decoration());
        holder.pav.setText(model.getPhoto_Video());
        holder.stat.setText(model.getStatus());
        holder.image = model.getImage();
        Glide.with(ctx).load(holder.image).placeholder(R.drawable.animated_progress).into(holder.imageView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Halls").child(holder.name.getText().toString());
        holder.viewdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,HallDetails.class);
                intent.putExtra("name",holder.name.getText().toString());
                intent.putExtra("address",holder.address.getText().toString());
                intent.putExtra("phone",holder.phone.getText().toString());
                intent.putExtra("cap",holder.cap.getText().toString());
                intent.putExtra("in",holder.in.getText().toString());
                intent.putExtra("out",holder.out.getText().toString());
                intent.putExtra("desc",holder.desc.getText().toString());
                intent.putExtra("rent",holder.rent.getText().toString());
                intent.putExtra("dec",holder.dec.getText().toString());
                intent.putExtra("pav",holder.pav.getText().toString());
                intent.putExtra("image",holder.image);
                intent.putExtra("stat",holder.stat.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filterlist(ArrayList<HallRegistrationModel> arrayList) {
        this.list = arrayList;

    }
    public void deleteItem(int position) {

        list.remove(position);
        notifyItemRemoved(position);

        databaseReference.removeValue();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,phone,in,out,cap,dec,rent,pav,desc,viewdet,stat;
        ImageView imageView;
        String image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.textView5);
            address =itemView.findViewById(R.id.adddhall);
            phone =itemView.findViewById(R.id.phonehall);
            in =itemView.findViewById(R.id.inhall);
            out =itemView.findViewById(R.id.outhall);
            cap =itemView.findViewById(R.id.caphall);
            dec =itemView.findViewById(R.id.dechall);
            rent =itemView.findViewById(R.id.renthall);
            pav =itemView.findViewById(R.id.pavhall);
            desc =itemView.findViewById(R.id.tvdesc);
            viewdet =itemView.findViewById(R.id.btndetails);
            imageView =itemView.findViewById(R.id.hallImageView);
            stat =itemView.findViewById(R.id.tvv1);
        }
    }
}






