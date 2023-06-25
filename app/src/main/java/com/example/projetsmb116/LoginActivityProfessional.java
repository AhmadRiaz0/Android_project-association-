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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityProfessional extends AppCompatActivity {

    //Variable
    EditText inputEmailProfessional;
    EditText inputPasswordProfessional;
    Button btnloginProfessional;

    TextView forgotPasswordProfessional;
    TextView btntextViewSignUpProfessional;

    FirebaseAuth mAuthProfessional;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_professional);

        //Firebase
        mAuthProfessional = FirebaseAuth.getInstance();

        //Lien entre variable et design
        inputEmailProfessional = (EditText) findViewById(R.id.inputEmailProfessional);
        inputPasswordProfessional = (EditText) findViewById(R.id.inputPasswordProfessional);
        btnloginProfessional = (Button) findViewById(R.id.btnloginProfessional);
        forgotPasswordProfessional = findViewById(R.id.forgotPasswordProfessional);
        btntextViewSignUpProfessional = findViewById(R.id.textViewSignUpProfessional);

        btnloginProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Variable
                String emailProfessional;
                String passwordProfessional;

                //Lecture des différents champs du formulaire
                emailProfessional = String.valueOf(inputEmailProfessional.getText());
                passwordProfessional = String.valueOf(inputPasswordProfessional.getText());

                //Verification si les champs sont vides
                //Verification email
                if(TextUtils.isEmpty(emailProfessional)){
                    Toast.makeText(LoginActivityProfessional.this, "Entrer le Email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification password
                if(TextUtils.isEmpty(passwordProfessional)){
                    Toast.makeText(LoginActivityProfessional.this, "Entrer le password !", Toast.LENGTH_SHORT).show();
                    return;
                }

                //SignUp
                mAuthProfessional.signInWithEmailAndPassword(emailProfessional, passwordProfessional)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivityProfessional.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();

                                    //Si l'utilisateur est enregistrer il est envoyer sur l'activity ProductActivityProfessional
                                    Intent intent = new Intent(LoginActivityProfessional.this, InformationsActivityProfessional.class);
                                    startActivities(new Intent[]{intent});

                                } else {
                                    Toast.makeText(LoginActivityProfessional.this, "Échec de la connexion !",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });

        //Click depuis login vers Registration
        btntextViewSignUpProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivityProfessional.this, RegisterActivityProfessional.class);
                startActivities(new Intent[]{intent});
            }
        });

        forgotPasswordProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivityProfessional.this, ForgetPasswordProfessional.class);
                startActivities(new Intent[]{intent});

            }
        });

    }
}