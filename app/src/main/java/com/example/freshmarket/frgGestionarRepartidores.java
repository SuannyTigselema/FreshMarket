package com.example.freshmarket;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.freshmarket.adaptadores.adpPopularesA;
import com.example.freshmarket.adaptadores.adpProductos;
import com.example.freshmarket.adaptadores.adpRepartidores;
import com.example.freshmarket.objetos.persona;
import com.example.freshmarket.objetos.producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgGestionarRepartidores#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgGestionarRepartidores extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<persona> lstPersona;
    RecyclerView rclRepartidores;
    ProgressDialog progress;

    public frgGestionarRepartidores() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgGestionarRepartidores.
     */
    // TODO: Rename and change types and number of parameters
    public static frgGestionarRepartidores newInstance(String param1, String param2) {
        frgGestionarRepartidores fragment = new frgGestionarRepartidores();
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
        View view = inflater.inflate(R.layout.fragment_frg_gestionar_repartidores, container, false);

        lstPersona=new ArrayList<>();
        rclRepartidores = (RecyclerView) view.findViewById(R.id.rclRepartidores);
        rclRepartidores.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rclRepartidores.setHasFixedSize(true);
        // rclRepartidores.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        getRepartidores();
        return view;


    }

    private void getRepartidores() {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();
        String url="http://192.168.0.21:81/persona";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        persona p=null;
                        try {
                            for (int i=0;i<response.length();i++){
                                p=new persona();

                                JSONObject obj = response.getJSONObject(i);
                                p.setNombre(obj.getString("nombre"));
                                p.setApellido(obj.getString("apellido"));
                                p.setCorreo(obj.getString("correo"));
                                p.setTelefono(obj.getString("telefono"));
                                p.setFoto(obj.getString("foto"));
                                lstPersona.add(p);
                            }
                            progress.hide();
                            adpRepartidores adapter=new adpRepartidores(getContext(),lstPersona);
                            rclRepartidores.setAdapter(adapter);

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