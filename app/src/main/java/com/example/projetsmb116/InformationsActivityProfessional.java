package com.example.projetsmb116;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class InformationsActivityProfessional extends AppCompatActivity {
    //Variables Company
    EditText inputCompanyProfessionalProduct;
    EditText inputUsernameProfessionalProduct;
    EditText inputUserFirstNameProfessionalProduct;
    EditText inputEmailProfessionalProduct;
    EditText inputPhoneProfessionalProduct;
    EditText inputAddressProfessionalProduct;
    EditText inputCityProfessionalProduct;

    //Variable product
    EditText inputProfessionalProductDonationCategory;
    EditText inputProfessionalProductDonationName;
    EditText inputProfessionalProductDonationQuantity;


    //Boutton
    Button btnConfirmationProfessionalProduct;
    Button btnDeconnexionProfessionalProduct;

    //DataBase
    DatabaseReference rootDatabaseReference;

    FirebaseAuth mAuth; //Authentification Firebase



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations_professional);

        //Lien entre variable et design Company
        inputCompanyProfessionalProduct = (EditText) findViewById(R.id.inputCompanyProfessionalProduct);
        inputUsernameProfessionalProduct = (EditText) findViewById(R.id.inputUsernameProfessionalProduct);
        inputUserFirstNameProfessionalProduct = (EditText) findViewById(R.id.inputUserFirstNameProfessionalProduct);
        inputPhoneProfessionalProduct = (EditText) findViewById(R.id.inputPhoneProfessionalProduct);
        inputAddressProfessionalProduct = (EditText) findViewById(R.id.inputAddressProfessionalProduct);
        inputCityProfessionalProduct = (EditText) findViewById(R.id.inputCityProfessionalProduct);

        //Lien entre variable et design Company
        inputProfessionalProductDonationCategory = (EditText) findViewById(R.id.inputProfessionalProductDonationCategory);
        inputProfessionalProductDonationName = (EditText) findViewById(R.id.inputProfessionalProductDonationName);
        inputProfessionalProductDonationQuantity = (EditText) findViewById(R.id.inputProfessionalProductDonationQuantity);

        //Lien entre variable et design Boutton
        btnConfirmationProfessionalProduct = (Button) findViewById(R.id.btnConfirmationProfessionalProduct);
        btnDeconnexionProfessionalProduct = (Button) findViewById(R.id.btnDeconnexionProfessionalProduct);


        //Database
        rootDatabaseReference = FirebaseDatabase.getInstance().getReference();




        //CLick sur Boutton Confirmation
        btnConfirmationProfessionalProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variable Company
                String professionalCompany;
                String professionalUsername;
                String professionalFirstName;
                String professionalPhone;
                String professionalAddress;
                String professionalCity;

                //Variable Product
                String professionalDonationCategory;
                String professionalDonationName;
                String professionalDonationQuantity;



                //Formulaire vers variables Company
                professionalCompany = String.valueOf(inputCompanyProfessionalProduct.getText());
                professionalUsername = String.valueOf(inputUsernameProfessionalProduct.getText());
                professionalFirstName = String.valueOf(inputUserFirstNameProfessionalProduct.getText());
                professionalPhone = String.valueOf(inputPhoneProfessionalProduct.getText());
                professionalAddress = String.valueOf(inputAddressProfessionalProduct.getText());
                professionalCity = String.valueOf(inputCityProfessionalProduct.getText());

                //Formulaire vers variables Product
                professionalDonationCategory = String.valueOf(inputProfessionalProductDonationCategory.getText());
                professionalDonationName = String.valueOf(inputProfessionalProductDonationName.getText());
                professionalDonationQuantity = String.valueOf(inputProfessionalProductDonationQuantity.getText());



                //Verification si les champs sont vides
                //Verification Company
                if(TextUtils.isEmpty(professionalCompany)){
                    Toast.makeText(InformationsActivityProfessional.this, "Entrer le nom de la boutique !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification userName
                if(TextUtils.isEmpty(professionalUsername)){
                    Toast.makeText(InformationsActivityProfessional.this, "Entrer le nom d'un responsable !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification Address
                if(TextUtils.isEmpty(professionalAddress)){
                    Toast.makeText(InformationsActivityProfessional.this, "Entrer l'adresse de la boutique !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification Aliment
                if(TextUtils.isEmpty(professionalDonationName)){
                    Toast.makeText(InformationsActivityProfessional.this, "Entrer le nom de l'aliment !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Verification la quantite des Aliments
                if(TextUtils.isEmpty(professionalDonationQuantity)){
                    Toast.makeText(InformationsActivityProfessional.this, "Entrer la quantite d'aliments !", Toast.LENGTH_SHORT).show();
                    return;
                }



                //Stocker les données dans la BDD Firebase : Realtime Database
                //Noeud
                DatabaseReference professionalUserDatabaseReference = rootDatabaseReference.child("professionalUser");

                // clé unique pour chaque enregistrement
                String productId = professionalUserDatabaseReference.push().getKey();

                //Rajout des nom de catégorie
                // Créer un objet HashMap pour stocker les valeurs des champs du formulaire
                HashMap<String, Object> professionalUser = new HashMap<>();

                //Valeur Company
                professionalUser.put("Company", professionalCompany);
                professionalUser.put("Username", professionalUsername);
                professionalUser.put("FirstName", professionalFirstName);
                professionalUser.put("Phone", professionalPhone);
                professionalUser.put("Address", professionalAddress);
                professionalUser.put("City", professionalCity);

                //Valeur Product
                professionalUser.put("ProductCategory", professionalDonationCategory);
                professionalUser.put("ProductName", professionalDonationName);
                professionalUser.put("ProductQuantity", professionalDonationQuantity);


                //Stocker les valeurs du produit dans le nœud avec la clé unique
                professionalUserDatabaseReference.child(productId).setValue(professionalUser);


                Toast.makeText(InformationsActivityProfessional.this, "Votre don a bien été enregistré !", Toast.LENGTH_LONG).show();




            }
        });

        btnDeconnexionProfessionalProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(InformationsActivityProfessional.this, LoginActivityProfessional.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        });


    }
}