package com.example.userlogin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    TextView tvName,tvEmail,tvPhone;
    Button btnLogOut;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("User Profile");

        tvName=findViewById(R.id.tvName);
        tvEmail=findViewById(R.id.tvEmail);
        tvPhone=findViewById(R.id.tvPhone);
        btnLogOut=findViewById(R.id.btnLogOut);


        reference=FirebaseDatabase.getInstance().getReference().child("User Info");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    tvName.setText(snapshot.child("name").getValue(String.class));
                    tvEmail.setText(snapshot.child("email").getValue(String.class));
                    tvPhone.setText(snapshot.child("phoneNo").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnLogOut.setOnClickListener(view -> {

            FirebaseAuth.getInstance().signOut();
            finish();
        });

    }
}