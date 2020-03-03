package com.prueba.baseremota.ui.consultarListaUrlImagen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prueba.baseremota.Entidades.Usuario;
import com.prueba.baseremota.Entidades.VolleySinglenton;
import com.prueba.baseremota.R;
import com.prueba.baseremota.adapter.UsuariosAdapter;
import com.prueba.baseremota.adapter.UsuariosImagenAdapter;
import com.prueba.baseremota.adapter.UsuariosImagenUrlAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultarListaUrlFragment extends Fragment {



    RecyclerView recyclerView;
    UsuariosImagenUrlAdapter adapter;
    ArrayList<Usuario> listaUsuarios;
    ImageView imgInternet;

   // RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        imgInternet=root.findViewById(R.id.imgInternet);
        imgInternet.setVisibility(View.GONE);

        recyclerView = root.findViewById(R.id.recyclerView);

        //requestQueue = Volley.newRequestQueue(getContext());

        listaUsuarios = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ConnectivityManager connectivityManager= (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();


        if (networkInfo!=null&&networkInfo.isConnected()){

            cargarWebServices();
            imgInternet.setVisibility(View.GONE);
        }

        else {

            imgInternet.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No hya conexion a internet", Toast.LENGTH_SHORT).show();

        }

        return root;
    }

    private void cargarWebServices() {

        String ip=getString(R.string.ip);

        String url = ip +"/ejemploBDRemota/wsJSONConsultarListaImagenesUrl.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Usuario usuario = null;

                //en donde inicia el array para agarrar los valores
                JSONArray jsonArray = response.optJSONArray("usuario");


                try {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        usuario = new Usuario();
                        JSONObject jsonObject = null;
                        //recorre cada elemento de la lista

                        jsonObject = jsonArray.getJSONObject(i);

                        usuario.setDocumento(jsonObject.optInt("documento"));
                        usuario.setNombre(jsonObject.optString("nombre"));
                        usuario.setProfesion(jsonObject.optString("profesion"));
                        usuario.setRutaImagen(jsonObject.optString("ruta_imagen"));


                        listaUsuarios.add(usuario);


                    }

                    adapter=new UsuariosImagenUrlAdapter(listaUsuarios,getContext());
                    recyclerView.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hubo conexion papu", Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //requestQueue.add(jsonObjectRequest);
        VolleySinglenton.getInstanceVolley(getContext()).addToRequestQueue(jsonObjectRequest);


    }

}