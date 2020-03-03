package com.prueba.baseremota.ui.consultarUsuario;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prueba.baseremota.Entidades.Usuario;
import com.prueba.baseremota.Entidades.VolleySinglenton;
import com.prueba.baseremota.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ConsultarUsuarioFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText edtDocumento;
    Button btnConsultar;
    TextView txtNombre, txtProfesion;

    ImageView imgUsuario;

    //private RequestQueue requestQueue;

    JsonObjectRequest jsonObjectRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools, container, false);


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


        //requestQueue = Volley.newRequestQueue(getContext());


        return root;
    }

    private void cargarWebServices() {


        String ip=getString(R.string.ip);

        String url = ip +"/ejemploBDRemota/wsJSONConsultarUsuarioImagen.php?documento=" +
                edtDocumento.getText().toString().trim();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
       // requestQueue.add(jsonObjectRequest);
        VolleySinglenton.getInstanceVolley(getContext()).addToRequestQueue(jsonObjectRequest);


    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getContext(), "Mensaje:" + response, Toast.LENGTH_SHORT).show();

        Usuario usuario = new Usuario();

        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            usuario.setNombre(jsonObject.optString("nombre"));
            usuario.setProfesion(jsonObject.optString("profesion"));
            usuario.setDato(jsonObject.optString("imagen"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtNombre.setText("Nombre: " + usuario.getNombre());
        txtProfesion.setText("Profesion: " + usuario.getProfesion());

        if (usuario.getImagen() != null) {
            imgUsuario.setImageBitmap(usuario.getImagen());
        } else {

            imgUsuario.setImageResource(R.drawable.ic_launcher_foreground);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(), "No funciono" + error.toString(), Toast.LENGTH_SHORT).show();

    }


}