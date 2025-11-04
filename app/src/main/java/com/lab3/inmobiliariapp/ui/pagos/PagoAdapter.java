package com.lab3.inmobiliariapp.ui.pagos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.models.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {

    private List<Pago> lista;

    public PagoAdapter(List<Pago> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pago_card, parent, false);
        return new PagoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago p = lista.get(position);
        holder.tvFechaPago.setText("Fecha: " + p.getFechaPago());
        holder.tvMonto.setText("Monto: $" + p.getMonto());
        holder.tvDetalle.setText("Detalle: " + p.getDetalle());
        holder.tvEstado.setText("Estado: " + (p.isEstado() ? "Pagado" : "Pendiente"));
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    static class PagoViewHolder extends RecyclerView.ViewHolder {
        TextView tvFechaPago, tvMonto, tvDetalle, tvEstado;

        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvMonto = itemView.findViewById(R.id.tvMontoPago);
            tvDetalle = itemView.findViewById(R.id.tvDetallePago);
            tvEstado = itemView.findViewById(R.id.tvEstadoPago);
        }
    }
}