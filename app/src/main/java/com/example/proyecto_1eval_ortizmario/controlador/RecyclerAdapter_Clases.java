package com.example.proyecto_1eval_ortizmario.controlador;
import com.bumptech.glide.Glide;
import com.example.proyecto_1eval_ortizmario.R;
import com.example.proyecto_1eval_ortizmario.modelo.ClaseJuego;


import android.app.Application;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import java.util.ArrayList;

public class RecyclerAdapter_Clases extends RecyclerView.Adapter<RecyclerAdapter_Clases.ClasesHolder> {

    private ArrayList<ClaseJuego> listaClases;
    private View.OnClickListener listener;      //  Listener que se establecerá en la vista. Necesario para el maestro-detalle.

    public void setOnClickListener(View.OnClickListener l){
        this.listener = l;
    }

    public RecyclerAdapter_Clases(ArrayList<ClaseJuego> listaClases){
        this.listaClases = listaClases;
    }



    //  Creación gráfica de las celdas que contienen la información de cada clase.
    @NonNull
    @Override
    public ClasesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  Inserción de la estructura de las celdas creadas en el XML.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_clase, parent, false);
        ClasesHolder holder = new ClasesHolder(view);
        return holder;
    }

    //  Enlazado de datos entre las objetos ClaseJuego y las celdas que muestran la información.
    @Override
    public void onBindViewHolder(@NonNull ClasesHolder holder, int position) {
        holder.cargarImagen(listaClases.get(position).getUrlImagen());
        holder.txtNombreClase.setText(listaClases.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return listaClases.size();
    }
    //  Definición de cómo se debe crear cada ViewHolder (celda del Recycler), asociando cada elemento de la interfaz.
    public class ClasesHolder extends RecyclerView.ViewHolder{
        ImageView imgIconoClase;
        TextView txtNombreClase;

        public ClasesHolder(@NonNull View itemView) {
            super(itemView);
            imgIconoClase = (ImageView) itemView.findViewById(R.id.imgIconoClase);
            txtNombreClase = (TextView) itemView.findViewById(R.id.txtNombreClase);
        }

        //  Este método carga para cada una de las celdas su respectiva imagen mediante Glide.
        public void cargarImagen(String URL){
            Glide.with(itemView.getContext())
                    .load("https://xivapi.com/"+ URL)   //  URL de la imagen.
                    // En otros elementos se usa CircularProgressDrawable. En este contexto no funcionaba bien.
                    .placeholder(R.mipmap.img_placeholder_round)
                    .error(R.mipmap.img_placeholder_round)  //  Si no se encuentra, se mostrará esta imagen.
                    .into(imgIconoClase);   //  Indicamos contendor.
        }
    }

}
