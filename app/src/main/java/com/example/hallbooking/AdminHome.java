package com.example.hallbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.cardview.widget.CardView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminHome extends AppCompatActivity {
    CardView alluserlist, newhalls, halls, booked;
    FloatingActionButton logout;
    AlertDialog.Builder builder;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getSupportActionBar().hide();
        builder = new AlertDialog.Builder(AdminHome.this);
        fAuth = FirebaseAuth.getInstance();


        logout = findViewById(R.id.logouts);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Are You Sure You Want To LogOut");

                builder.setTitle("LogOut");


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

//                        FirebaseAuth.getInstance().signOut();
//                        Intent intent = new Intent(AdminHome.this,SelectUser.class);
//                        startActivity(intent);
//                        finish();
                        FirebaseUser user = fAuth.getCurrentUser();
                        if (user != null){
                            fAuth.signOut();
                            Intent intent = new Intent(AdminHome.this,SelectUser.class);
                          //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(AdminHome.this, "You aren't login Yet!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                builder.show();
            }
        });



        alluserlist = findViewById(R.id.alluserlist);
        alluserlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AllUsers.class));
            }
        });



        newhalls = findViewById(R.id.registerstudents);
        newhalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewHalls.class));
            }
        });

        halls = findViewById(R.id.industrylist);
        halls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TotalHalls.class));
            }
        });


        booked = findViewById(R.id.bookedlist);
        booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BookedHalls.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}