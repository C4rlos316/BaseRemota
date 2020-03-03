package com.prueba.baseremota.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.baseremota.Entidades.Usuario;
import com.prueba.baseremota.R;

import java.util.ArrayList;
import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuarioHolder> {


    List<Usuario> usuarios;

    public UsuariosAdapter(ArrayList<Usuario> listaUsuarios) {

        this.usuarios=listaUsuarios;


    }

    @NonNull
    @Override
    public UsuarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_item,parent,false);
        RecyclerView.LayoutParams layoutParams= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(layoutParams);

        return new UsuarioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioHolder holder, int position) {

        holder.txtDocumento.setText("Documento: "+usuarios.get(position).getDocumento().toString());
        holder.txtNombre.setText("Nombre: "+usuarios.get(position).getNombre());
        holder.txtProfesion.setText("Profesion: "+usuarios.get(position).getProfesion());


    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class UsuarioHolder extends RecyclerView.ViewHolder {

        TextView txtNombre,txtProfesion,txtDocumento;


        public UsuarioHolder(@NonNull View itemView) {
            super(itemView);

            txtDocumento=itemView.findViewById(R.id.txtDocumento);
            txtNombre=itemView.findViewById(R.id.txtNombre);
            txtProfesion=itemView.findViewById(R.id.txtProfesion);


        }
    }
}
