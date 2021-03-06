package com.prueba.baseremota.ui.consultarLista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultarListaFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<Usuario> listaUsuarios;
    UsuariosAdapter adapter;

   // RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        //requestQueue = Volley.newRequestQueue(getContext());

        listaUsuarios = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        cargarWebServices();


        return root;
    }

    private void cargarWebServices() {

        String ip=getString(R.string.ip);

        String url = ip +"/ejemploBDRemota/wsJSONConsultarLista.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
                        usuario.setDato(jsonObject.optString("imagen"));


                        listaUsuarios.add(usuario);


                    }

                    adapter=new UsuariosAdapter(listaUsuarios);
                    recyclerView.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hubo conexion papu", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "No funciono" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e( "no",error.toString());


            }
        });
       // requestQueue.add(jsonObjectRequest);

        VolleySinglenton.getInstanceVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


}