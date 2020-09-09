package com.example.freshmarket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.freshmarket.adaptadores.adpProductos;
import com.example.freshmarket.objetos.VolleySingleton;
import com.example.freshmarket.objetos.producto;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgGestionarProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgGestionarProducto extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    EditText campoNombre,campoDescripcion,campoTipo, campoPrecio, campoCantidad, campoDescuento, campoDocumento;
    Button botonRegistro,btnFoto, btnCancelar;
    ImageView imgFoto;
    ProgressDialog progreso;

    private MagicalPermissions magicalPermissions;
    private final static int RESIZE_PHOTO_PIXELS_PERCENTAGE=50;
    private MagicalCamera magicalCamera;
    private ImageView imageViewFoto;
    private TextView txtRUTA;
    private Spinner mySpinner;
    RelativeLayout layoutRegistrar;//permisos
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;
    JsonArrayRequest arrayRequest;
    public frgGestionarProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgGestionarProducto.
     */
    // TODO: Rename and change types and number of parameters
    public static frgGestionarProducto newInstance(String param1, String param2) {
        frgGestionarProducto fragment = new frgGestionarProducto();
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

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vista=inflater.inflate(R.layout.fragment_frg_gestionar_producto, container, false);
        campoDocumento= (EditText) vista.findViewById(R.id.campoDoc);
        campoNombre= (EditText) vista.findViewById(R.id.txtNombreProducto);
        campoDescripcion= (EditText) vista.findViewById(R.id.txtDescripcionProducto);
       // campoTipo= (EditText) vista.findViewById(R.id.txtTipoProducto);
        campoPrecio= (EditText) vista.findViewById(R.id.txtPrecioProducto);
        campoCantidad= (EditText) vista.findViewById(R.id.txtStockProducto);
        campoDescuento= (EditText) vista.findViewById(R.id.txtDescuentoProducto);
        botonRegistro= (Button) vista.findViewById(R.id.btnRegistrarProducto);
        btnFoto=(Button)vista.findViewById(R.id.btnFoto);
        btnCancelar=(Button)vista.findViewById(R.id.btnCancelar);
        imgFoto=(ImageView)vista.findViewById(R.id.imgFoto);

        mySpinner = (Spinner) vista.findViewById(R.id.spinner);

        String[] arraySpinner = new String[] {
                "Fruta", "Hortaliza"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

       // layoutRegistrar= (RelativeLayout) vista.findViewById(R.id.idLayoutRegistrar);

        // request= Volley.newRequestQueue(getContext());

        //Permisos
        if(solicitaPermisosVersionesSuperiores()){
            btnFoto.setEnabled(true);
        }else{
            btnFoto.setEnabled(false);
        }


        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();

            }
        });
        return vista;  }


    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }
    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    abrirCamara();
                }else{
                    if (opciones[i].equals("Elegir de Galeria")){
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada==true){
            Long consecutivo= System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";

            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

            fileImagen=new File(path);

            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

            ////
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities=getContext().getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent,COD_FOTO);

            ////

        }

    }

    private void cargarWebService() {

            progreso=new ProgressDialog(getContext());
            progreso.setMessage("Cargando...");
            progreso.show();

            //SUBIR IMAGEN
            String urlIMG = "https://fvpaula.000webhostapp.com/insertar_producto.php?";
            final String[] ruta = new String[1];

            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlIMG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progreso.hide();
                        ruta[0] = s;
                        Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(),"no registrado",Toast.LENGTH_SHORT).show();
                        progreso.hide();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen=convertirImgString(bitmap);
                //Obtener el nombre de la imagen
                String nombre=campoDocumento.getText().toString();
                //Creación de parámetros
                Map<String,String> params = new HashMap<String, String>();

                //Agregando de parámetros
                params.put("imagen", imagen);
                params.put("nombre", nombre);

                //Parámetros de retorno
                return params;
            }
        };
        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);

            //INSERT EN LA BASE DE DATOS
//https://fvpaula.000webhostapp.com/subir_imagen.php
        //https://fvpaula.000webhostapp.com/insertar_producto.php?
        //http://192.168.0.21:8080/productoFinal
            String url="http://192.168.0.21:81/productoFinal";
//https://fvpaula.000webhostapp.com/imagenes/iconoFresh.png
        String documento=campoDocumento.getText().toString();
        String nombre=campoNombre.getText().toString();
        String descripcion=campoDescripcion.getText().toString();
        String tipo = mySpinner.getSelectedItem().toString();

       // String tipo=campoTipo.getText().toString();
        String precio=campoPrecio.getText().toString();
        String cantidad=campoCantidad.getText().toString();
        String descuento=campoDescuento.getText().toString();
        String imagen="https://fvpaula.000webhostapp.com/imagenes/"+documento+".jpg";

        JSONObject producto = new JSONObject();
        try {
            producto.put("nombre",nombre);
         producto.put("cantidad",cantidad);
            producto.put("descripcion",descripcion);
            producto.put("tipo",tipo);
            producto.put("precio",precio);
            producto.put("descuento",descuento);
            producto.put("imagen",imagen);
            String s=producto.toString();
          } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, url, producto,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progreso.hide();
                        Toast.makeText(getContext(),"Producto registrado con exito",Toast.LENGTH_SHORT).show();
                        campoCantidad.setText("");
                        campoDescripcion.setText("");
                        campoDescuento.setText("");
                        campoDocumento.setText("");
                        campoNombre.setText("");
                        campoPrecio.setText("");
                        campoTipo.setText("");


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e("Error: ", volleyError.getMessage());
                    }
                });

        queue.add(jobReq);

    }
    private void cargarWebService2(){
    progreso=new ProgressDialog(getContext());
    progreso.setMessage("Cargando...");
    progreso.show();
    String url = "https://fvpaula.000webhostapp.com/insertar_producto.php?";

   StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progreso.hide();
                          Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(),"no registrado",Toast.LENGTH_SHORT).show();
                        progreso.hide();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                  //Convertir bits a cadena
                String imagen=convertirImgString(bitmap);

                //Obtener el nombre de la imagen
                String nombre=campoNombre.getText().toString();

                //Creación de parámetros
                Map<String,String> params = new HashMap<String, String>();

                //Agregando de parámetros
                params.put("imagen", imagen);
                params.put("nombre", nombre);

                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados
        if((getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&getContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
           return true;
        }


        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui

    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                imgFoto.setImageURI(miPath);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);

                break;
        }
        bitmap=redimensionarImagen(bitmap,600,800);
    }
    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MIS_PERMISOS){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//el dos representa los 2 permisos
                Toast.makeText(getContext(),"Permisos aceptados",Toast.LENGTH_SHORT);
                btnFoto.setEnabled(true);
            }
        }else{
            solicitarPermisosManual();
        }
    }
    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getContext().getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

}