package com.example.projetsmb116;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.Company.setText(model.getCompany());
        holder.Address.setText(model.getAddress());
        holder.ProductName.setText(model.getProductName());
        holder.ProductQuantity.setText(model.getProductQuantity());

        //Boutton itineraire
        holder.btnItineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String company = model.getCompany();
                String adresse = model.getAddress();


                //Aller vers une autre activity afin d'afficher la map
                Intent intent = new Intent(holder.itemView.getContext(), ProductLocation.class);
                intent.putExtra("Company", company);
                intent.putExtra("Adresse", adresse);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // Ajoutez l'indicateur FLAG_IMMUTABLE ou FLAG_MUTABLE
                PendingIntent pendingIntent = PendingIntent.getActivity(holder.itemView.getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                try {
                    // Utilisez le PendingIntent pour démarrer l'activité
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                ////////////////////////////////////////////////Rajouter les information a envoyer vers l'activity LocationActivity///////////////////////////////////////////////////
                //holder.itemView.getContext().startActivity(intent);

            }
        });

        //Boutton Receptionner
        holder.btnRéceptionner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Company.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Avez-vous bien récupéré le don ?");

                builder.setPositiveButton("Je confirme !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("professionalUser")
                                .child(getRef(holder.getBindingAdapterPosition()).getKey()).removeValue(); //Supprime la valeur par rapport a la cle unique du professionnel
                                ///////////////////////////////////////////////////////////

                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.Company.getContext(), "Annuler !", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.show(); //Permet d'afficher la boite de dialogue


            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        //Company
        TextView Address;
        TextView City;
        TextView Company;
        TextView FirstName;
        TextView Phone;
        TextView UserName;

        //Product
        TextView ProductCategory;
        TextView ProductName;
        TextView ProductQuantity;

        //Button
        Button btnItineraire;
        Button btnRéceptionner;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Company = (TextView)itemView.findViewById(R.id.Companytext);
            Address = (TextView)itemView.findViewById(R.id.Addresstext);
            ProductName = (TextView)itemView.findViewById(R.id.ProductName);
            ProductQuantity = (TextView)itemView.findViewById(R.id.ProductQuantity);

            btnItineraire = (Button)itemView.findViewById(R.id.btnItineraire);
            btnRéceptionner = (Button)itemView.findViewById(R.id.btnReceptionner);




        }
    }


}
