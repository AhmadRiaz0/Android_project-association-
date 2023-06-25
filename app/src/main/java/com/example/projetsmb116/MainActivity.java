package com.example.projetsmb116;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String textViewAcceuilTexte = "Bienvenue dans notre association de lutte contre le gaspillage alimentaire !";
    String texteDescriptionTexte = "Notre application rassemble des professionnels et des bénévoles pour récupérer les invendus alimentaires et les redistribuer aux personnes dans le besoin. Rejoignez-nous pour construire une communauté solidaire et durable.";

    String texteDescriptionTexte2 = "Ensemble, changeons le monde, une bouchée à la fois.";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textViewAcceuil = findViewById(R.id.textViewAcceuil);
        TextView textViewDescriptions = findViewById(R.id.textViewDescriptions);
        TextView textViewDescriptions2 = findViewById(R.id.textViewDescriptions2);
        Button btnStartProfessional = findViewById(R.id.btnStartProfessional);
        Button btnStartVolunteer = findViewById(R.id.btnStartVolunteer);

        textViewAcceuil.setText(textViewAcceuilTexte);
        textViewDescriptions.setText(texteDescriptionTexte);
        textViewDescriptions2.setText(texteDescriptionTexte2);

        btnStartProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivityProfessional.class);
                startActivities(new Intent[]{intent});
            }
        });

        btnStartVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivities(new Intent[]{intent});

            }
        });





    }
}