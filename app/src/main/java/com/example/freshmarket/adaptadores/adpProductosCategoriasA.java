package com.example.freshmarket.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshmarket.R;
import com.example.freshmarket.objetos.producto;

import java.util.ArrayList;

public class adpProductosCategoriasA extends RecyclerView.Adapter<adpProductosCategoriasA.MyViewHolder> {
    private static final int TYPE_HEADER=0;
    private static final int TYPE_LIST=0;


    private Context mContext;

    //Lista de productos q va a llegar al adaptador
    private ArrayList<producto> mLista;

    public TextView lblDescripcion;
    public TextView lblPrecio;
    public TextView lblUnidad;
    public TextView txtNombre;
    public ImageView imgFoto;

  /*  public adpPopularesA(Context context, ArrayList<producto> lista) {
        mContext = context;
        mLista=lista;
    }*/
  private String[] datos;

    public adpProductosCategoriasA(){this.datos = new String[]{"Frutas","Verduras","Combos"};}

    //Métodos propios del RecyclerdView

    //infla los items al recyclerd
    @NonNull
    @Override
    public adpProductosCategoriasA.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
           view= LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.admin_item_prod_cat,null,false);
           return new MyViewHolder(view);

    }

    //enlaza cada vista del viewholder con los datos de la Lista de productos
    @Override
    public void onBindViewHolder(@NonNull adpProductosCategoriasA.MyViewHolder holder, int position) {
        try {holder.asignar_datos(datos[position]);}catch (Exception e){}


    }

    //Devuelve la cantidad del elementos del recyclerd
    @Override
    public int getItemCount() {
        return datos.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        int view_type;
        //Obtener los elementos q irán en cada item
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                txtNombre=(TextView) itemView.findViewById(R.id.txtNombre);

        }
        public void asignar_datos(String valor){
          txtNombre.setText(valor);
        }
    }

}
