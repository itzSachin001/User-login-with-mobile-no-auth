package com.example.userlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button loginByOtp;
    EditText etEmail, etPassword, etName, etMobileNumber;

    FirebaseDatabase root;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Registration");

        loginByOtp = findViewById(R.id.btnLoginByOTP);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etMobileNumber = findViewById(R.id.etMobileNo);

        root = FirebaseDatabase.getInstance();
        reference = root.getReference().child("User Info");

        loginByOtp.setOnClickListener(view -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phoneNo = etMobileNumber.getText().toString().trim();

            try {
                if (TextUtils.isEmpty(name)) {
                    etName.setError("Please enter your name..");
                }

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Please enter your email..");
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Required field.");
                }

                if (TextUtils.isEmpty(phoneNo)) {
                    etMobileNumber.setError("Please enter your Mobile number..");
                }

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(phoneNo)) {

                    Toast.makeText(MainActivity.this, "Invalid Registration", Toast.LENGTH_SHORT).show();
                    throw new IllegalArgumentException();
                }


                UserInfo user = new UserInfo(name, email, phoneNo);
                reference.setValue(user);

                Intent otp=new Intent(MainActivity.this,OTPActivity.class);
                otp.putExtra("number",phoneNo);
                startActivity(otp);
                finish();

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        });

    }
}