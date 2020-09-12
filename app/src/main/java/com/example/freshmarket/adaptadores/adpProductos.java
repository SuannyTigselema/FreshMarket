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
import com.example.freshmarket.objetos.producto;

import java.util.ArrayList;

public class adpProductos extends RecyclerView.Adapter<adpProductos.MyViewHolder>
        implements View.OnClickListener {
    private static final int TYPE_HEADER=0;
    private static final int TYPE_LIST=0;


    private Context mContext;

    //Lista de productos q va a llegar al adaptador
    private ArrayList<producto> mLista;

    public TextView lblDescripcion;
    public TextView lblPrecio;
    public TextView lblUnidad;
    public TextView txtNombre;
    public TextView lblStock;
    public ImageView imgFoto;
    private View.OnClickListener listener;

    public adpProductos(Context context, ArrayList<producto> lista) {
        mContext = context;
        mLista=lista;
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
  private String[] datos;

   /* public adpProductos(){this.datos = new String[]{"a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a"};}*/

    //Métodos propios del RecyclerdView

    //infla los items al recyclerd
    @NonNull
    @Override
    public adpProductos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
           view= LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.admin_item_producto,null,false);
            view.setOnClickListener(this);
           return new MyViewHolder(view);

    }

    //enlaza cada vista del viewholder con los datos de la Lista de productos
    @Override
    public void onBindViewHolder(@NonNull adpProductos.MyViewHolder holder, int position) {
        try {
            txtNombre.setText(mLista.get(position).getNombre());
            lblDescripcion.setText(mLista.get(position).getDescripcion());
            lblStock.setText(mLista.get(position).getCantidad());
            lblPrecio.setText(mLista.get(position).getPrecio());
            Glide.with(mContext)
                    .load(mLista.get(position).getUrl())
                    .into(imgFoto);
         /*   holder.asignar_datos(datos[position]);*/
        }catch (Exception e){}


    }

    //Devuelve la cantidad del elementos del recyclerd
    @Override
    public int getItemCount() {
            return mLista.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null)
        {
            listener.onClick(view);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        int view_type;
        //Obtener los elementos q irán en cada item
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre=(TextView) itemView.findViewById(R.id.lblNombre);
            lblDescripcion=(TextView) itemView.findViewById(R.id.lblDescripcion);
            lblPrecio=(TextView) itemView.findViewById(R.id.lblPrecio);
            lblStock=(TextView) itemView.findViewById(R.id.lblStock);
            imgFoto=(ImageView) itemView.findViewById(R.id.imageView);

        }
        public void asignar_datos(String valor){
            txtNombre.setText("Tomate");
            lblDescripcion.setText("Riñón");
            lblPrecio.setText("$0,35");
            lblStock.setText("12");
        }
    }

}
