package com.prueba.baseremota.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.prueba.baseremota.Entidades.Usuario;
import com.prueba.baseremota.Entidades.VolleySinglenton;
import com.prueba.baseremota.R;

import java.util.ArrayList;
import java.util.List;

public class UsuariosImagenUrlAdapter extends RecyclerView.Adapter<UsuariosImagenUrlAdapter.UsuarioHolder> {


    List<Usuario> usuarios;
   // RequestQueue requestQueue;
    Context context;

    public UsuariosImagenUrlAdapter(ArrayList<Usuario> listaUsuarios,Context context) {

        this.usuarios=listaUsuarios;
        this.context=context;
       // requestQueue= Volley.newRequestQueue(context);


    }

    @NonNull
    @Override
    public UsuariosImagenUrlAdapter.UsuarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_imagen_item,parent,false);
        RecyclerView.LayoutParams layoutParams= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(layoutParams);

        return new UsuariosImagenUrlAdapter.UsuarioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosImagenUrlAdapter.UsuarioHolder holder, int position) {

        holder.txtDocumento.setText("Documento: "+usuarios.get(position).getDocumento().toString());
        holder.txtNombre.setText("Nombre: "+usuarios.get(position).getNombre());
        holder.txtProfesion.setText("Profesion: "+usuarios.get(position).getProfesion());


        if (usuarios.get(position).getRutaImagen()!=null){

            

            
            
            cargarImagen(usuarios.get(position).getRutaImagen(),holder);
            
        }
        else {
            holder.imgUsuario.setImageResource(R.drawable.ic_launcher_background);
        }


    }

    private void cargarImagen(String rutaImagen,final UsuarioHolder holder) {
        
        String urlImagen="http://192.168.64.2/ejemploBDRemota/"+rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                holder.imgUsuario.setImageBitmap(response);


            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error al cargar imagen", Toast.LENGTH_SHORT).show();
                
            }
        });

        //requestQueue.add(imageRequest);

        VolleySinglenton.getInstanceVolley(context).addToRequestQueue(imageRequest);

    }

    
    
    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class UsuarioHolder extends RecyclerView.ViewHolder {

        TextView txtNombre,txtProfesion,txtDocumento;
        ImageView imgUsuario;


        public UsuarioHolder(@NonNull View itemView) {
            super(itemView);

            txtDocumento=itemView.findViewById(R.id.txtDocumento);
            txtNombre=itemView.findViewById(R.id.txtNombre);
            txtProfesion=itemView.findViewById(R.id.txtProfesion);

            imgUsuario=itemView.findViewById(R.id.imgUsuario);



        }
    }
}
