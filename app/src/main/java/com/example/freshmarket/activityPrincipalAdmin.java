package com.example.freshmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.freshmarket.WebService.Asynchtask;
import com.example.freshmarket.WebService.WebService;
import com.example.freshmarket.adaptadores.adpPopularesA;
import com.example.freshmarket.objetos.producto;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activityPrincipalAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Asynchtask  {
    NavigationView navView;
    DrawerLayout drawerLayout;

    ArrayList<String> listDatos;
    RecyclerView recyclerview=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView = (NavigationView)findViewById(R.id.nav_viewAdmin);
        navView.setNavigationItemSelectedListener(this);
        boolean transactionFragment = false;


        Fragment fragment = new frgInicioAdmin();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        boolean transactionFragment = false;


        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.mnu_inicioAdmin:
                fragment = new frgInicioAdmin();
                transactionFragment = true;
                break;

            case R.id.mnu_miinfoAdmin:
                fragment = new frgMiInformacion();
                transactionFragment = true;
                break;

            case R.id.mnu_pedidosAdmin:
                fragment = new frgGestionarPedidos();
                transactionFragment = true;
                break;

            case R.id.mnu_repartidoresAdmin:
                fragment = new frgGestionarRepartidores();
                transactionFragment = true;
                break;

            case R.id.mnu_cerrarAdmin:
                this.finish();
                break;

        }

        if(transactionFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            menuItem.setChecked(true);
            getSupportActionBar().setTitle(menuItem.getTitle());
        }
        drawerLayout.closeDrawers();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.mnutoolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONArray JSONlistaProductos = new JSONArray(result);
        ArrayList<producto> lstProductos=new ArrayList<producto>();

        //Invoco al metodo de la clase productos que es para el parseo de datos-me devuelve un arraylist de producto
//        lstProductos = producto.JsonObjectsBuild(JSONlistaProductos);

        //HASTA AQUÍ SÍ RECIBE LOS 11 PRODUCTOS
        //Envío la lista de productos a l
      /*  adpPopularesA adapatorHortalizas = new adpPopularesA(this, lstProductos);
        recyclerview.setAdapter(adapatorHortalizas);*/

    }
}