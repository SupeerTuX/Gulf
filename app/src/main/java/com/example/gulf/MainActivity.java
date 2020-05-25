package com.example.gulf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gulf.Config.Cfg;
import com.example.gulf.DataModel.AddPoints;
import com.example.gulf.DataModel.Cliente;
import com.example.gulf.DataModel.DataModel;
import com.example.gulf.DataModel.ResponseModel;
import com.example.gulf.DataModel.TicketModel;
import com.example.gulf.DataModel.UserModel;
import com.example.gulf.Network.AddPointsService;
import com.example.gulf.Network.WebService;
import com.example.gulf.Util.DateTimeUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_PERMISOS_CAMARA = 1, CODIGO_INTENT = 2, CODE_TICKET = 3;
    private boolean permisoCamaraConcedido = false, permisoSolicitadoDesdeBoton = false;
    private TextView tvCodigoLeido;
    private TextView tvTicketInfo;
    private TextView tvPuntos;
    private TextView tvUsuario;
    private TextView tvTicket;
    private ImageButton ibScannQR;
    private ImageButton ibTicket;
    private Button btnCancel;
    ProgressDialog progressDialog;

    Cliente cliente; //Modelo de datos para el cliente actual


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificarYPedirPermisosDeCamara();
        ibScannQR = findViewById(R.id.imageButtonScannQR);
        ibTicket = findViewById(R.id.imageButtonScannTicket);
        tvCodigoLeido = findViewById(R.id.textView3);
        tvUsuario = findViewById(R.id.textView5);
        tvTicketInfo = findViewById(R.id.textView4);
        tvPuntos = findViewById(R.id.textViewPuntos);
        tvTicket =findViewById(R.id.textViewTicket);
        btnCancel = findViewById(R.id.buttonCancel);


        //Evento de Boton escanear codigo QR
        ibScannQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!permisoCamaraConcedido){
                    Toast.makeText(MainActivity.this, "Por favor permite que la app acceda a la cámara", Toast.LENGTH_SHORT).show();
                    permisoSolicitadoDesdeBoton = true;
                    verificarYPedirPermisosDeCamara();
                    return;
                }
                escanear();

            }
        });

        //Evento: Boton escanear ticket
        ibTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nuevo Scannde ticket
                //httpGetRequest();
                //Escan Ticket
                escanearTicket();
            }
        });

        //Evento BotonCancelar
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reestablecemos los controles a su estado unicial
                tvCodigoLeido.setText("Resultado QR");
                tvUsuario.setText("");
                tvPuntos.setText("");
                tvTicketInfo.setVisibility(View.INVISIBLE);
                tvTicket.setText("");
                tvTicket.setVisibility(View.INVISIBLE);
                ibTicket.setVisibility(View.INVISIBLE);
                ibScannQR.setEnabled(true);

            }
        });
    }


    /*
    private void httpGetRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.235/apigulf/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService servicio = retrofit.create(WebService.class);
        Call<DataModel> dataModelCall = servicio.getCliente(.getUserID());

        dataModelCall.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                DataModel dataModel = response.body();
                tvPuntos.setVisibility(View.VISIBLE);
                tvPuntos.setText("Puntos: " + dataModel.getCliente().get(0).getPuntos());
                Log.d("DEBUG", "Code: "+ response.code() + " Puntos: "+ dataModel.getCliente().get(0).getPuntos());
            }
            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error De Conexion", Toast.LENGTH_SHORT).show();
                tvPuntos.setVisibility(View.VISIBLE);
                tvPuntos.setText("Error: no se pudo conectar con el servidor");
            }
        });
    }

*/

    private void escanear() {
        Intent i = new Intent(MainActivity.this, ActivityEscanear.class);
        startActivityForResult(i, CODIGO_INTENT);
    }

    private void escanearTicket() {
        Intent i = new Intent(MainActivity.this, ActivityEscanear.class);
        startActivityForResult(i, CODE_TICKET);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Verificamos el codigo del intent
        if (requestCode == CODIGO_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String jsonString = data.getStringExtra("codigo");
                    //Parse json string
                        final UserModel usuarioActual = new Gson().fromJson(jsonString, UserModel.class);

                        tvCodigoLeido.setText("UserID: "+ usuarioActual.getUserID());
                        //Mostramos el nombre del usuario
                        tvUsuario.setText("Nombre: "+usuarioActual.getNombre());

                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Validando Usuario"); // Setting Message
                        progressDialog.setTitle("Procesando"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    //httpGetRequest();
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://192.168.100.112/apigulf/public/api/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    WebService servicio = retrofit.create(WebService.class);
                                    Call<Cliente> dataModelCall = servicio.getCliente(usuarioActual.getUserID());

                                    dataModelCall.enqueue(new Callback<Cliente>() {
                                        @Override
                                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                                            if (response.code() == 200){
                                                cliente = response.body();
                                                tvPuntos.setVisibility(View.VISIBLE);
                                                tvPuntos.setText("Puntos: " + cliente.getPuntos());
                                                //Mostramos texto "Escanee el codigo de ticket"
                                                tvTicketInfo.setText("Escanea El Ticket");
                                                //Deshabilitamos el boton
                                                ibScannQR.setEnabled(false);
                                                //Habilitamos el botono del scaner ticket
                                                ibTicket.setEnabled(true);
                                                ibTicket.setVisibility(View.VISIBLE);
                                                Log.d("DEBUG", "Code: "+ response.code() + " Puntos Acumulados: ");
                                            } else if (response.code() == 204){
                                                Log.d("DEBUG", "Code: "+ response.code() + " No existe el usuario ");
                                            }

                                            //Log.d("DEBUG", "Code: "+ response.code() + " Puntos Acumulados: "+ dataModel.getMessage().get(0).getPuntos());
                                            progressDialog.dismiss();
                                        }
                                        @Override
                                        public void onFailure(Call<Cliente> call, Throwable t) {
                                            progressDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Error De Conexion", Toast.LENGTH_SHORT).show();
                                            tvPuntos.setVisibility(View.VISIBLE);
                                            tvPuntos.setText("Error: no se pudo conectar con el servidor, intentelo de nuevo");
                                            //Habilitamos el boton scann QR
                                            ibScannQR.setEnabled(true);
                                        }
                                    });
                                    Thread.sleep(60000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();
                }
            }
            //Verificamos el codigo del intent
        }

        //Respuesta de lectura de ticket
        if(requestCode == CODE_TICKET){
            if(resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String jsonString = data.getStringExtra("codigo");
                    tvTicket.setVisibility(View.VISIBLE);
                    //+ Convertir el json String
                    TicketModel ticketObject = new Gson().fromJson(jsonString, TicketModel.class);
                    if (ticketObject.getMonto() == null || ticketObject.getTicketID() == null){
                        Toast.makeText(this, "Codigo Leido Invalido, Intentelo con un ticket valido", Toast.LENGTH_LONG).show();
                        return;
                    }
                    double puntos = CalculoDePuntos(ticketObject.getMonto());
                    //Validamos que el resultado sea valido
                    if (puntos > 0) {
                        //agregar puntos
                        //Preparando datos
                        String dateTime = DateTimeUtil.getCurrentDate();
                        int id = Integer.parseInt(cliente.getIDCliente());
                        final AddPoints addPoints = new AddPoints(id, dateTime, puntos, ticketObject.getTicketID());
                        //Post
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Validando Usuario"); // Setting Message
                        progressDialog.setTitle("Procesando"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    //httpGetRequest();
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://192.168.100.112/apigulf/public/api/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    AddPointsService servicio = retrofit.create(AddPointsService.class);
                                    Call<ResponseModel> dataModelCall = servicio.addPoints(addPoints);

                                    dataModelCall.enqueue(new Callback<ResponseModel>() {
                                        @Override
                                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                            if (response.code() == 200) {
                                                //Puntos agregados correctamente
                                                Log.d("Debug", "onResponse: Puntos agregados correctamente");
                                                progressDialog.dismiss();
                                            }
                                            if (response.code() == 203) {
                                                //El ticket ya ha sido registrado
                                                Log.d("Debug", "onResponse: El ticket ya ha sido usado");
                                                progressDialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                                            Log.d("Debug", "onResponse: Fallo la peticion");
                                        }
                                    });
                                    Thread.sleep(60000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();
                    }
                } else {
                    Toast.makeText(this, "Codigo Leido Invalido, Intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void agregarPuntos(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISOS_CAMARA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Escanear directamten solo si fue pedido desde el botón
                    if (permisoSolicitadoDesdeBoton) {
                        escanear();
                    }
                    permisoCamaraConcedido = true;
                } else {
                    permisoDeCamaraDenegado();
                }
                break;
        }
    }

    private void verificarYPedirPermisosDeCamara() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoCamaraConcedido = true;
        } else {
            // Si no, pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    CODIGO_PERMISOS_CAMARA);
        }
    }

    private void permisoDeCamaraDenegado() {
        // Esto se llama cuando el usuario hace click en "Denegar" o
        // cuando lo denegó anteriormente
        Toast.makeText(MainActivity.this, "No puedes escanear si no das permiso", Toast.LENGTH_SHORT).show();
    }

    //Calculo de puntos
    private double CalculoDePuntos(String monto){
        double nMonto = Double.parseDouble(monto);
        if (nMonto > 0){
            double puntos = nMonto * Cfg.PORCENTAGE;
            return puntos;
        }
        else return -1;
    }
}
