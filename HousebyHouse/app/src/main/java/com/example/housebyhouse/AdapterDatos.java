package com.example.housebyhouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos>
implements View.OnClickListener{
    ArrayList<UnidadHabitacional> listDatos;
    private View.OnClickListener listener;
    public AdapterDatos(ArrayList<UnidadHabitacional> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position).getTipo(),listDatos.get(position).getDireccion(),
                listDatos.get(position).getPrecio(),listDatos.get(position).getEstaArrendada());
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null)listener.onClick(v);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView txtTipoItem,txtDireccionItem,txtPrecioItem;
        LinearLayout layout;
        ImageView imageView;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtTipoItem= itemView.findViewById(R.id.txtTipoItem);
            txtDireccionItem= itemView.findViewById(R.id.txtDireccionItem);
            txtPrecioItem= itemView.findViewById(R.id.txtPrecioItem);
            layout= itemView.findViewById(R.id.layoutDisponibilidad);
            imageView=itemView.findViewById(R.id.imgItem);
        }

        public void asignarDatos(String tipo,String direccion,long precio,String disponibilidad) {
            txtTipoItem.setText(tipo);
            txtDireccionItem.setText(direccion);
            txtPrecioItem.setText(precio+"");
            if(disponibilidad.equals("si"))
                layout.setBackgroundColor(itemView.getResources().getColor(R.color.arrendado));
            switch (tipo){
                case ("Casa"):
                    imageView.setImageResource(R.drawable.casapng);
                    break;
                case("Finca"):
                    imageView.setImageResource(R.drawable.cabanapng);
                    break;
                case("Apartamento"):
                    imageView.setImageResource(R.drawable.unidadpng);
                    break;
            }
        }
    }
}
