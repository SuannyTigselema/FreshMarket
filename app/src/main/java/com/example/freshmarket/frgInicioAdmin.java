package com.example.freshmarket;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.freshmarket.adaptadores.adpCategoriasA;
import com.example.freshmarket.adaptadores.adpPopularesA;
import com.example.freshmarket.adaptadores.adpProductosCategoriasA;

public class frgInicioAdmin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView rclProductos;
    RecyclerView rclCategorias;
    RecyclerView rclProd_Cat;
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
        rclProductos = (RecyclerView) view.findViewById(R.id.recyclerPopulares);
        //añadir un Divider a los elementos de la lista->Diseño de la linea de separacion de los items
        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
        linear.setOrientation(LinearLayoutManager.HORIZONTAL);
        rclProductos.setLayoutManager(linear);
        adpPopularesA adt_productos = new adpPopularesA();
        rclProductos.setAdapter(adt_productos);

        rclCategorias = (RecyclerView) view.findViewById(R.id.recyclerCategorias2);
        rclCategorias.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        //añadir un Divider a los elementos de la lista->Diseño de la linea de separacion de los items
        LinearLayoutManager linearV = new LinearLayoutManager(getActivity().getApplicationContext());
        linearV.setOrientation(LinearLayoutManager.VERTICAL);
        rclCategorias.setLayoutManager(linearV);
        final adpCategoriasA adt_categorias = new adpCategoriasA();
        adt_categorias.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View view) {
        int opcselec=rclCategorias.getChildAdapterPosition(view);
        Fragment selectedFragment = null;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

          if(opcselec==0)
             {
             selectedFragment = new com.example.freshmarket.frgProductosAdmin();
              fragmentTransaction.replace(R.id.content_frame,selectedFragment);
               fragmentTransaction.commit();
                }
              Toast.makeText(getActivity(),"item:"+ opcselec,Toast.LENGTH_SHORT).show();
              }
         }

        );
        rclCategorias.setAdapter(adt_categorias);
        return view;
    }
}