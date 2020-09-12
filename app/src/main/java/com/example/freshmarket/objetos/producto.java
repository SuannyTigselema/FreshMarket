package com.example.freshmarket.objetos;

import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class producto {
    public String nombre;
    public String descripcion;
    public String url;
    public String precio;
    public String cantidad;
    public String descuento;
    public String tipo;
    public Integer id;

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    /*  public producto(JSONObject a) throws JSONException{
        this.nombre=a.get("nombre").toString();
        this.descripcion=a.get("descripcion").toString();
      //  this.url=a.getString("imagen").toString();
    }
    public static ArrayList<producto> JsonObjectsBuild(JSONArray datos) throws JSONException
    {
        ArrayList<producto> producto=new ArrayList<>();
        for (int i = 0; i < datos.length() && i<20; i++) {
            producto.add(new producto(datos.getJSONObject(i)));
        }
        return producto;
    }*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
