package com.example.userlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    EditText etPhoneNo;
    Button btnVerifyOTP;
    String otpId;
    FirebaseAuth auth;

    String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        Objects.requireNonNull(getSupportActionBar()).setTitle("OTP Verification");

        etPhoneNo = findViewById(R.id.etPhoneNo);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);

        auth=FirebaseAuth.getInstance();


            phoneNo = this.getIntent().getStringExtra("number");

            initiateOTP();

            btnVerifyOTP.setOnClickListener((View view) -> {

                String otp = etPhoneNo.getText().toString().trim();

                if (TextUtils.isEmpty(otp)) {
                    Toast.makeText(OTPActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else if (otp.length() != 6) {
                    Toast.makeText(OTPActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpId, otp);
                    signInWithPhoneAuthCredential(credential);
                }
            });
        }

    public void initiateOTP(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        otpId=s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OTPActivity.this, "OTP has not been sent.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Intent goToProfile=new Intent(OTPActivity.this,UserProfile.class);
                        startActivity(goToProfile);
                        finish();

                    } else {
                        Toast.makeText(OTPActivity.this, "Sign in error", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}