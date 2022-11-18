package com.example.proyecto_1eval_ortizmario.controlador;
import com.example.proyecto_1eval_ortizmario.R;
import com.example.proyecto_1eval_ortizmario.modelo.ClaseJuego;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter_Clases extends RecyclerView.Adapter<RecyclerAdapter_Clases.ClasesHolder> {

    private ArrayList<ClaseJuego> listaClases;

    public RecyclerAdapter_Clases(ArrayList<ClaseJuego> listaClases){
        this.listaClases = listaClases;
    }

    @NonNull
    @Override
    public ClasesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_clase, parent, false);
        ClasesHolder holder = new ClasesHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ClasesHolder holder, int position) {
        holder.txtNombreClase.setText(listaClases.get(position).getNombre());
        holder.txtAbrevClase.setText(listaClases.get(position).getAbreviacion());
    }

    @Override
    public int getItemCount() {
        return listaClases.size();
    }

    public class ClasesHolder extends RecyclerView.ViewHolder{
        ImageView imgIconoClase;
        TextView txtNombreClase;
        TextView txtAbrevClase;

        public ClasesHolder(@NonNull View itemView) {
            super(itemView);
            imgIconoClase = (ImageView) itemView.findViewById(R.id.imgIconoClase);
            txtNombreClase = (TextView) itemView.findViewById(R.id.txtNombreClase);
            txtAbrevClase = (TextView) itemView.findViewById(R.id.txtAbreviacionClase);
        }
    }
}
