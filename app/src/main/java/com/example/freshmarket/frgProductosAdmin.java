package com.example.freshmarket;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import android.app.ProgressDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.freshmarket.adaptadores.adpProductos;
import com.example.freshmarket.objetos.VolleySingleton;
import com.example.freshmarket.objetos.producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgProductosAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgProductosAdmin extends Fragment   implements Response.Listener<JSONArray>,Response.ErrorListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rclProductos;
    ArrayList<producto> lstProductos;
    ProgressDialog progress;
    JsonArrayRequest  jsonObjectRequest;

    Button btnNuevoProducto;
    View view;
    RequestQueue queque;
    TextView txt;
    String url="http://192.168.0.21:8080/productoFinal";
    public frgProductosAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgProductosAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static frgProductosAdmin newInstance(String param1, String param2) {
        frgProductosAdmin fragment = new frgProductosAdmin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        View view = inflater.inflate(R.layout.fragment_frg_productos_admin, container, false);

        lstProductos=new ArrayList<>();
        rclProductos = (RecyclerView) view.findViewById(R.id.rclProductos);
        rclProductos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rclProductos.setHasFixedSize(true);
        rclProductos.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        getProductos();

        btnNuevoProducto=view.findViewById(R.id.btnNuevoProducto);
        btnNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Press",Toast.LENGTH_LONG).show();
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
            }
        });

        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
//        txt.setText(error.toString());
        System.out.println();
        Log.d("ERROR: ", error.toString());
        progress.hide();
    }

    public void getProductos()
    {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();

        jsonObjectRequest=new JsonArrayRequest(Request.Method.GET, url, (String) null,this,this);
                //(Request.Method.GET,url, null,this,this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }


    @Override
    public void onResponse(JSONArray response) {
        producto p=null;
        try {
            for (int i=0;i<response.length();i++){
                p=new producto();

                JSONObject obj = response.getJSONObject(i);

                p.setNombre(obj.getString("nombre"));
                p.setDescripcion(obj.getString("descripcion"));
                lstProductos.add(p);
            }
            progress.hide();
            adpProductos adapter=new adpProductos(getContext(),lstProductos);
            rclProductos.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            progress.hide();
        }

    }
}