package com.example.cabzee;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chaos.view.PinView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class phoneLogin extends AppCompatActivity {

    private CountryCodePicker ccp;
    private String selected_country_code="+91";
    private ConstraintLayout phonelayout;
    private EditText edit;

    private PinView pin;

    private ImageView image;
    private static final int CREDENTIAL_PICKER_REQUEST =120 ;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private String mVerificationid;
    private PhoneAuthProvider.ForceResendingToken mresenttoken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        ccp= findViewById(R.id.ccp);
        edit = findViewById(R.id.edit);
        pin=findViewById(R.id.pin);
        phonelayout= findViewById(R.id.phonelayout);
        image= findViewById(R.id.imageView1);
        progressBar= findViewById(R.id.bar);
        mAuth=FirebaseAuth.getInstance();

        ccp.setOnCountryChangeListener(() -> selected_country_code=ccp.getSelectedCountryCodeWithPlus());

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==10){
                    sendotp();


                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==6){
                    progressBar.setVisibility(View.VISIBLE);
                    PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(mVerificationid, Objects.requireNonNull(pin.getText()).toString().trim());
                    signInWithAuthCredential(phoneAuthCredential);


                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();


        PendingIntent intent = Credentials.getClient(phoneLogin.this).getHintPickerIntent(hintRequest);
        try
        {
            startIntentSenderForResult(intent.getIntentSender(), CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0,new Bundle());
        }
        catch (IntentSender.SendIntentException e)
        {
            e.printStackTrace();
        }

        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code=phoneAuthCredential.getSmsCode();
                if(code !=null){
                    verifyCode(code);
                }

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(phoneLogin.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                phonelayout.setVisibility(View.VISIBLE);
                pin.setVisibility(View.GONE);
                image.setVisibility(View.GONE);

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,@NonNull PhoneAuthProvider.ForceResendingToken token){
                super.onCodeSent(verificationId,token);

                Toast.makeText(phoneLogin.this,"6 digit otp sent",Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                phonelayout.setVisibility(View.GONE);
                pin.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                mVerificationid=verificationId;


            }

        };


    }

    private void verifyCode(String code) {
        PhoneAuthCredential Credential=PhoneAuthProvider.getCredential(mVerificationid,code);
        signInWithAuthCredential(Credential);
    }


    private void sendotp() {

        progressBar.setVisibility(View.VISIBLE);

        String phonenumber=selected_country_code+edit.getText().toString().trim();
        PhoneAuthOptions options= PhoneAuthOptions.newBuilder(mAuth).
                setTimeout(60L, TimeUnit.SECONDS).
                setActivity(phoneLogin.this).
                setPhoneNumber(phonenumber).
                setCallbacks(callbacks).
                build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK)
        {
            // Obtain the phone number from the result
            Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);
            edit.setText(credentials.getId().substring(3));



        }
        else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == Auth.CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE)
        {
            // *** No phone numbers available ***
            Toast.makeText(phoneLogin.this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }


    }
    private void signInWithAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Intent intent=new Intent(phoneLogin.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(phoneLogin.this,"Logged In Successfully",Toast.LENGTH_SHORT).show();


                    }
                    else{
                        Intent intent=new Intent(phoneLogin.this, Login_Activity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(phoneLogin.this,"LogIn Failed",Toast.LENGTH_SHORT).show();

                    }

                });
    }
//
//    @Override
//    protected  void onStart(){
//        super.onStart();
//        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
//        if(currentuser!=null){
//            startActivity(new Intent(phoneLogin.this,HomeActivity.class));
//            finish();
//        }
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(phoneLogin.this,Login_Activity.class);
        startActivity(intent);
        finish();
    }
}