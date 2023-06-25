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

public class LoginActivity extends AppCompatActivity {

    //Variable
    EditText inputEmail;
    EditText inputPassword;
    Button btnlogin;

    TextView forgotPassword;
    TextView btntextViewSignUp;

    FirebaseAuth mAuth;

/*
    //vérifiez si l'utilisateur est actuellement connecté
    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //Si l'utilisateur est enregistrer il est envoyer sur l'activity LoginActivity
            Intent intent = new Intent(LoginActivity.this, ProductActivityProfessional.class);
            startActivities(new Intent[]{intent});
            finish();
        }
    }

 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Lien entre variable et design
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        forgotPassword = findViewById(R.id.forgotPassword);
        btntextViewSignUp = findViewById(R.id.textViewSignUp);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variable
                String email;
                String password;


                //Lecture des différents champs du formulaire
                email = String.valueOf(inputEmail.getText());
                password = String.valueOf(inputPassword.getText());

                //Verification si les champs sont vides
                //Verification email
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Entrer le Email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification password
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Entrer le password !", Toast.LENGTH_SHORT).show();
                    return;
                }

                //SignUp
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Connexion réussie !.",
                                            Toast.LENGTH_SHORT).show();




                                    //Si l'utilisateur est enregistrer il est envoyer sur l'activity LoginActivity
                                    Intent intent = new Intent(LoginActivity.this, ProductActivityProfessional.class);
                                    startActivities(new Intent[]{intent});
                                    //finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, "La connexion a échoué.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });


        //Click depuis login vers Registration
        btntextViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivities(new Intent[]{intent});
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivities(new Intent[]{intent});
            }
        });
    }
}