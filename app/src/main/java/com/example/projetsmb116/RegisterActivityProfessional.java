package com.example.projetsmb116;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivityProfessional extends AppCompatActivity {

    //Variable
    EditText inputUsernameProfessional;
    EditText inputEmailProfessional;
    EditText inputPasswordProfessional;
    EditText inputConfirmPasswordProfessional;
    Button btnRegisterProfessional;
    TextView btnAlreadyHaveAccountProfessional;
    FirebaseAuth mAuthProfessional; //Authentification Firebase



    //vérifiez si l'utilisateur est actuellement connecté
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuthProfessional.getCurrentUser();
        if(currentUser != null){

            ///////////Envoyer vers une autre activity ////////////////////
            //Si l'utilisateur est enregistrer il est envoyer sur l'activity LoginActivity
            //Intent intent = new Intent(LoginActivity.this, LoginActivity.class); // ajouter destinataire
            //startActivities(new Intent[]{intent});
            //finish();
            ////////////////////////////////////////////////////////////////////


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_professional);

        //Firebase
        mAuthProfessional = FirebaseAuth.getInstance();

        //Lien entre Variable et design
        inputUsernameProfessional = (EditText) findViewById(R.id.inputUsernameProfessional);
        inputEmailProfessional = (EditText) findViewById(R.id.inputEmailProfessional);
        inputPasswordProfessional = (EditText) findViewById(R.id.inputPasswordProfessional);
        inputConfirmPasswordProfessional = (EditText) findViewById(R.id.inputConfirmPasswordProfessional);
        btnRegisterProfessional = (Button) findViewById(R.id.btnRegisterProfessional);
        btnAlreadyHaveAccountProfessional = (TextView) findViewById(R.id.alreadyHaveAccountProfessional);

        //Click pour comfirmer le formulaire
        btnRegisterProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameProfessional;
                String emailProfessional;
                String passwordProfessional;
                String confirmPasswordProfessional;

                //Lecture des différents champs du formulaire
                userNameProfessional = String.valueOf(inputUsernameProfessional.getText());
                emailProfessional = String.valueOf(inputEmailProfessional.getText());
                passwordProfessional = String.valueOf(inputPasswordProfessional.getText());
                confirmPasswordProfessional = String.valueOf(inputConfirmPasswordProfessional.getText());

                //Verification si les champs sont vides
                //Verification UserName
                if(TextUtils.isEmpty(userNameProfessional)){
                    Toast.makeText(RegisterActivityProfessional.this, "Entrer le nom de l'entreprise !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification email
                if(TextUtils.isEmpty(emailProfessional)){
                    Toast.makeText(RegisterActivityProfessional.this, "Entrer l'Email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification password
                if(TextUtils.isEmpty(passwordProfessional)){
                    Toast.makeText(RegisterActivityProfessional.this, "Entrez le mot de passe !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification confirmPassword
                if(TextUtils.isEmpty(confirmPasswordProfessional)){
                    Toast.makeText(RegisterActivityProfessional.this, "Confirmer le mot de passe !", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verifier que Mot de pass == Confirme Password
                if(!passwordProfessional.equals(confirmPasswordProfessional)) {
                    Toast.makeText(RegisterActivityProfessional.this, "Les mots de passe ne correspondent pas !", Toast.LENGTH_SHORT).show();

                }


                mAuthProfessional.createUserWithEmailAndPassword(emailProfessional, passwordProfessional)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    //Recuperation du UserName
                                    FirebaseUser user = mAuthProfessional.getCurrentUser();
                                    if (user != null) {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userNameProfessional)
                                                .build();
                                        user.updateProfile(profileUpdates);
                                    }

                                    Toast.makeText(RegisterActivityProfessional.this, "Compte créé !",
                                            Toast.LENGTH_SHORT).show();
                                    //Si l'utilisateur est enregistrer il est envoyer sur l'activity LoginActivity
                                    Intent intent = new Intent(RegisterActivityProfessional.this, LoginActivityProfessional.class);
                                    startActivities(new Intent[]{intent});
                                    finish();


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivityProfessional.this, "Échec de l'enregitrement !",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });



        //Click depuis Registration vers login
        btnAlreadyHaveAccountProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivityProfessional.this, LoginActivityProfessional.class);
                startActivities(new Intent[]{intent});
            }
        });



    }
}