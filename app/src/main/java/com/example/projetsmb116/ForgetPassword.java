package com.example.projetsmb116;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    TextView textViewForgetPassword;
    EditText inputEmailForgetPassword;
    Button buttonReinitialiser;
    Button buttonBack;

    FirebaseAuth mAuth;

    String strEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        textViewForgetPassword = (TextView) findViewById(R.id.textViewForgetPassword);
        inputEmailForgetPassword = (EditText) findViewById(R.id.inputEmailForgetPassword);
        buttonReinitialiser = (Button) findViewById(R.id.buttonReinitialiser);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        mAuth = FirebaseAuth.getInstance();

        String textView = "Indiquez l'email de votre compte pour lequel vous souhaitez réinitialiser votre mot de passe. ";

        textViewForgetPassword.setText(textView);

        buttonReinitialiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = inputEmailForgetPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(strEmail)){
                    ResetPassword();

                }else {
                    inputEmailForgetPassword.setError("Le champ email doit être renseigné !");

                }
            }
        });

        //Button back
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void ResetPassword(){
        mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgetPassword.this, "Un mail de réinitialisation a été envoyé dans votre boite mail.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgetPassword.this, LoginActivity.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPassword.this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}