package com.prueba.baseremota.ui.consultarUsuarioUrl;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prueba.baseremota.Entidades.Usuario;
import com.prueba.baseremota.Entidades.VolleySinglenton;
import com.prueba.baseremota.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultarUsuarioUrlFragment extends Fragment {


    EditText edtDocumento;
    Button btnConsultar;
    TextView txtNombre, txtProfesion;

    ImageView imgUsuario;

    //private RequestQueue requestQueue;

    JsonObjectRequest jsonObjectRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_share);

        edtDocumento = root.findViewById(R.id.edtDocumento);


        txtNombre = root.findViewById(R.id.txtNombre);
        txtProfesion = root.findViewById(R.id.txtProfesion);

        imgUsuario = root.findViewById(R.id.imgUsuario);

        btnConsultar = root.findViewById(R.id.btnConsultar);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarWebServices();

            }
        });


       // requestQueue = Volley.newRequestQueue(getContext());


        return root;
    }

    private void cargarWebServices() {


        String ip=getString(R.string.ip);

        String url = ip +"/ejemploBDRemota/wsJSONConsultarUsuarioUrl.php?documento=" +
                edtDocumento.getText().toString().trim();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Usuario usuario = new Usuario();

                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject = null;

                try {
                    jsonObject = json.getJSONObject(0);

                    usuario.setNombre(jsonObject.optString("nombre"));
                    usuario.setProfesion(jsonObject.optString("profesion"));
                    usuario.setRutaImagen(jsonObject.optString("ruta_imagen"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                txtNombre.setText("Nombre: " + usuario.getNombre());
                txtProfesion.setText("Profesion: " + usuario.getProfesion());

                String urlImagen="http://192.168.64.2/ejemploBDRemota/"+usuario.getRutaImagen();

                cargarWebServicesImagen(urlImagen);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "No se puede conectar", Toast.LENGTH_SHORT).show();
            }
        });
        //requestQueue.add(jsonObjectRequest);
        VolleySinglenton.getInstanceVolley(getContext()).addToRequestQueue(jsonObjectRequest);


    }

    private void cargarWebServicesImagen(String urlImagen) {

        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest= new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                imgUsuario.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error al cargar imagen", Toast.LENGTH_SHORT).show();
            }
        });

        //requestQueue.add(imageRequest);

        VolleySinglenton.getInstanceVolley(getContext()).addToRequestQueue(imageRequest);


    }
}