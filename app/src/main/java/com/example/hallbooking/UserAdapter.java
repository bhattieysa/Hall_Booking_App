package com.example.hallbooking;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{
ArrayList<UserModel> list;
    Context context;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public UserAdapter(ArrayList<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        UserModel model = list.get(position);
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        holder.name.setText(model.getFullName());
        holder.fname.setText(model.getFatherName());
        holder.cnic.setText(model.getCNIC());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());
        holder.passwords.setText(model.getPasswrod());
        holder.address.setText(model.getAddress());
        holder.image = model.getImage();
        Glide.with(context).load(holder.image).placeholder(R.drawable.placeholder).into(holder.imageView);

        databaseReference = firebaseDatabase.getReference("Users").child(firebaseUser.getUid());


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UserDetails.class);
                intent.putExtra("name",holder.name.getText().toString());
                intent.putExtra("fname",holder.fname.getText().toString());
                intent.putExtra("cnic",holder.cnic.getText().toString());
                intent.putExtra("phone",holder.phone.getText().toString());
                intent.putExtra("email",holder.email.getText().toString());
                intent.putExtra("addrress",holder.address.getText().toString());
                intent.putExtra("image",model.getImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    public void filterlist(ArrayList<UserModel> arrayList) {
        this.list = arrayList;

    }

    public void deleteItem(int position) {

        list.remove(position);
        notifyItemRemoved(position);

        databaseReference.removeValue();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,fname,cnic,address,email,passwords,phone,btn;
        String image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.appCompatImageView);
            name = itemView.findViewById(R.id.nametv);
            fname = itemView.findViewById(R.id.fathername);
            cnic = itemView.findViewById(R.id.cnic);
            address = itemView.findViewById(R.id.address);
            email = itemView.findViewById(R.id.emailstv);
            passwords = itemView.findViewById(R.id.passwords);
            phone = itemView.findViewById(R.id.phonetv);
            btn = itemView.findViewById(R.id.btndetails);
        }
    }
}












