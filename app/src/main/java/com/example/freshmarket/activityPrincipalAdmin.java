package com.example.freshmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class activityPrincipalAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navView;
    DrawerLayout drawerLayout;
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

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        boolean transactionFragment = false;


        Fragment fragment = null;
        switch (menuItem.getItemId()) {
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
}