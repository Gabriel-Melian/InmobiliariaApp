package com.lab3.inmobiliariapp.ui.contratos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.models.InmuebleModel;
import com.lab3.inmobiliariapp.request.ApiClient;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder> {

    private List<InmuebleModel> lista;
    private Context context;

    public ContratoAdapter(List<InmuebleModel> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inmueble_contrato, parent, false);
        return new ContratoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ContratoViewHolder holder, int position) {

        InmuebleModel i = lista.get(position);

        holder.tvDireccion.setText(i.getDireccion());
        holder.tvValor.setText("$" + i.getValor());
        holder.tvTipo.setText(i.getTipo());

        Glide.with(context)
                .load(ApiClient.URLBASE + i.getImagen())
                .placeholder(R.drawable.baseline_home_24)
                .into(holder.imgInmueble);

        //Boton o click en la card: Abre el detalle del contrato del inmueble
//        holder.itemView.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putInt("idInmueble", i.getIdInmueble());
//            Navigation.findNavController(v).navigate(R.id.detalleContratoFragment, bundle);
//        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ContratoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDireccion, tvValor, tvTipo;
        private ImageView imgInmueble;
        public ContratoViewHolder(View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvValor = itemView.findViewById(R.id.tvPrecio);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            imgInmueble = itemView.findViewById(R.id.imgInmueble);
        }
    }
}
