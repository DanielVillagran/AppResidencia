package com.firebaseloginapp.AccountActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.firebaseloginapp.R;
import com.google.firebase.auth.FirebaseAuth;


import java.util.List;

/**
 * Created by Belal on 4/17/2018.
 */

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {

    private Context mCtx;
    private List<PojoAlumnos> artistList;

    public ArtistsAdapter(Context mCtx, List<PojoAlumnos> artistList) {
        this.mCtx = mCtx;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_artists, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        PojoAlumnos artist = artistList.get(position);

        holder.nombre.setText("Nombre: "+artist.getNombre());
        holder.ncontrol.setText("Ncontrol: " + artist.getNcontrol());
        holder.carrera.setText("Carrera: " + artist.getCarrera());
        holder.semestre.setText("Semestre: " + artist.getSemestre());
        if(artist.getStatus().matches("1")){
            holder.status.setText("Estatus: Activo" );

        }else if(artist.getStatus().matches("0")){
            holder.status.setText("Estatus: Inactivo" );

        }
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, ncontrol, carrera, semestre, status;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            ncontrol = itemView.findViewById(R.id.ncontrol);
            carrera = itemView.findViewById(R.id.carrera);
            semestre = itemView.findViewById(R.id.semestre);
            status = itemView.findViewById(R.id.status);
        }
    }
}
