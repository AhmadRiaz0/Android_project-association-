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

public class RegisterActivity extends AppCompatActivity {

    //Variable
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputConfirmPassword;
    Button btnRegister;
    TextView btnAlreadyHaveAccount;
    FirebaseAuth mAuth; //Authentification Firebase




    //vérifiez si l'utilisateur est actuellement connecté
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
        setContentView(R.layout.activity_register);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Lien entre Variable et design
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputConfirmPassword = (EditText) findViewById(R.id.inputConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnAlreadyHaveAccount = (TextView) findViewById(R.id.alreadyHaveAccount);

        //Recuperation du UserName dans la BDD


        //Click pour comfirmer le formulaire
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName;
                String email;
                String password;
                String confirmPassword;

                //Lecture des différents champs du formulaire
                userName = String.valueOf(inputUsername.getText());
                email = String.valueOf(inputEmail.getText());
                password = String.valueOf(inputPassword.getText());
                confirmPassword = String.valueOf(inputConfirmPassword.getText());

                //Verification si les champs sont vides
                //Verification UserName
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "Entrer votre Nom !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification email
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Entrer votre Email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification password
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Entrer votre mots de passe !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification confirmPassword
                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(RegisterActivity.this, "Confirmer votre mots de passe !", Toast.LENGTH_SHORT).show();
                    return;
                }



                //Verifier que Mot de pass == Confirme Password
                if(!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Les mots de passe ne correspondent pas !", Toast.LENGTH_SHORT).show();

                }

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        //Recuperation du UserName
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(userName)
                                                    .build();
                                            user.updateProfile(profileUpdates);
                                        }

                                        Toast.makeText(RegisterActivity.this, "Compte créé !",
                                                Toast.LENGTH_SHORT).show();
                                        //Si l'utilisateur est enregistrer il est envoyer sur l'activity LoginActivity
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivities(new Intent[]{intent});
                                        finish();


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "L'authentification a échoué.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

            }
        });


        //Click depuis Registration vers login
        btnAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivities(new Intent[]{intent});
            }
        });
    }
}