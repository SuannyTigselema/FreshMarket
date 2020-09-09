package com.example.freshmarket;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.freshmarket.adaptadores.adpCategoriasA;
import com.example.freshmarket.adaptadores.adpPopularesA;
import com.example.freshmarket.adaptadores.adpProductos;
import com.example.freshmarket.adaptadores.adpProductosCategoriasA;
import com.example.freshmarket.objetos.VolleySingleton;
import com.example.freshmarket.objetos.producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frgInicioAdmin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView rclProductos;
    RecyclerView rclProductosPopulares;
    RecyclerView rclCategorias;
    RecyclerView rclProd_Cat;


    ArrayList<producto> lstProductos;
    ProgressDialog progress;
    JsonArrayRequest jsonObjectRequest;

    Button btnNuevoProducto;
    View view;
    RequestQueue queque;
    TextView txt;
    String url="http://192.168.0.21:81/productoFinal";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frgInicioAdmin() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_inicio_admin, container, false);

        lstProductos=new ArrayList<>();
        rclProductos = (RecyclerView) view.findViewById(R.id.rclProductos);
        rclProductos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rclProductos.setHasFixedSize(true);
        rclProductos.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        rclProductosPopulares = (RecyclerView) view.findViewById(R.id.recyclerPopulares);
        //añadir un Divider a los elementos de la lista->Diseño de la linea de separacion de los items
        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
        linear.setOrientation(LinearLayoutManager.HORIZONTAL);
        rclProductosPopulares.setLayoutManager(linear);

        getProductos();

        btnNuevoProducto=view.findViewById(R.id.btnNuevoProducto);
        btnNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });

        return view;

    }
    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Producto","Combo","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción:");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Combo")){
                    Fragment fragment = new frgGestionarCombo();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else{
                    if (opciones[i].equals("Producto")){
                    //Toast.makeText(getContext(),"Press",Toast.LENGTH_LONG).show();
                        // Crear fragmento de tu clase
                        Fragment fragment = new frgGestionarProducto();
                        // Obtener el administrador de fragmentos a través de la actividad
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        // Definir una transacción
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        // Remplazar el contenido principal por el fragmento
                        fragmentTransaction.replace(R.id.content_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        // Cambiar
                        fragmentTransaction.commit();
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    public void getProductos()
    {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        producto p=null;
                        try {
                            for (int i=0;i<response.length();i++){
                                p=new producto();

                                JSONObject obj = response.getJSONObject(i);
                                p.setNombre(obj.getString("nombre"));
                                p.setDescripcion(obj.getString("descripcion"));
                                p.setCantidad(obj.getString("stock"));
                                p.setPrecio(obj.getString("precio"));
                                p.setUrl(obj.getString("imagen"));
                                lstProductos.add(p);
                            }
                            progress.hide();
                         adpProductos adapter=new adpProductos(getContext(),lstProductos);
                             /*  adpPopularesA adpPopulares=new adpPopularesA(getContext(),lstProductos);
                            rclProductosPopulares.setAdapter(adpPopulares);*/
                            rclProductos.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                                    " "+response, Toast.LENGTH_LONG).show();
                            progress.hide();
                        }
                       }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e("Error: ", volleyError.getMessage());
                        Toast.makeText(getContext(), "No se puede conectar "+volleyError.toString(), Toast.LENGTH_LONG).show();
                        System.out.println();
                        Log.d("ERROR: ", volleyError.toString());
                        progress.hide();
                    }
                });

        queue.add(jobReq);




    }
}