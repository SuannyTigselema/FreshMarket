package com.example.freshmarket.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.freshmarket.R;
import com.example.freshmarket.objetos.persona;
import com.example.freshmarket.objetos.producto;

import java.util.ArrayList;

public class adpRepartidores extends RecyclerView.Adapter<adpRepartidores.MyViewHolder> {
    private static final int TYPE_HEADER=0;
    private static final int TYPE_LIST=0;


    private Context mContext;

    //Lista de productos q va a llegar al adaptador
    private ArrayList<persona> mLista;

    public TextView lblNombre;
    public TextView lblApellido;
    public TextView lblCorreo;
    public TextView lblTelefono;
    public ImageView imgFoto;

    public adpRepartidores(Context context, ArrayList<persona> lista) {
        mContext = context;
        mLista=lista;
    }

    @NonNull
    @Override
    public adpRepartidores.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
           view= LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.itemrepart,null,false);
           return new MyViewHolder(view);
    }

    //enlaza cada vista del viewholder con los datos de la Lista de productos
    @Override
    public void onBindViewHolder(@NonNull adpRepartidores.MyViewHolder holder, int position) {
        try {
            lblNombre.setText(mLista.get(position).getNombre() +" "+mLista.get(position).getApellido());
            lblCorreo.setText(mLista.get(position).getCorreo());
            lblTelefono.setText(mLista.get(position).getTelefono());
            Glide.with(mContext)
                    .load(mLista.get(position).getFoto())
                    .into(imgFoto);
        }catch (Exception e){

        }
    }

    //Devuelve la cantidad del elementos del recyclerd
    @Override
    public int getItemCount() {
            return mLista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        int view_type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNombre=(TextView) itemView.findViewById(R.id.txtNombre);
            lblCorreo=(TextView) itemView.findViewById(R.id.txtCorreo);
            lblTelefono=(TextView) itemView.findViewById(R.id.txtTelefono);
            imgFoto=(ImageView) itemView.findViewById(R.id.img_foto);
        }
    }

}
