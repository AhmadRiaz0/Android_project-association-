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

public class ForgetPasswordProfessional extends AppCompatActivity {


    TextView textViewForgetPasswordProfessional;
    EditText inputEmailForgetPasswordProfessional;
    Button buttonReinitialiserProfessional;
    Button buttonBackProfessional;

    FirebaseAuth mAuth;

    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_professional);

        textViewForgetPasswordProfessional = (TextView) findViewById(R.id.textViewForgetPasswordProfessional);
        inputEmailForgetPasswordProfessional = (EditText) findViewById(R.id.inputEmailForgetPasswordProfessional);
        buttonReinitialiserProfessional = (Button) findViewById(R.id.buttonReinitialiserProfessional);
        buttonBackProfessional = (Button) findViewById(R.id.buttonBackProfessional);

        mAuth = FirebaseAuth.getInstance();

        String textView = "Indiquez l'email de votre compte pour lequel vous souhaitez réinitialiser votre mot de passe. ";

        textViewForgetPasswordProfessional.setText(textView);

        buttonReinitialiserProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = inputEmailForgetPasswordProfessional.getText().toString().trim();
                if(!TextUtils.isEmpty(strEmail)){
                    ResetPassword();

                }else {
                    inputEmailForgetPasswordProfessional.setError("Le champ email doit être renseigné !");

                }
            }
        });

        //Button back
        buttonBackProfessional.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(ForgetPasswordProfessional.this, "Un mail de réinitialisation a été envoyé dans votre boite mail.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgetPasswordProfessional.this, LoginActivityProfessional.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPasswordProfessional.this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}