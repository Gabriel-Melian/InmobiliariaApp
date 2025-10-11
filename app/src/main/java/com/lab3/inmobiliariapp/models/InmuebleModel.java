package com.lab3.inmobiliariapp.models;

public class InmuebleModel {
    private int id;
    private String direccion;
    private String tipo;
    private String uso;
    private int precio;
    private int ambientes;
    private boolean estado;
    private String foto;
    private int idPropietario;

    public InmuebleModel(int id, String direccion, String tipo, String uso, int precio, int ambientes, boolean estado, String foto, int idPropietario) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.uso = uso;
        this.precio = precio;
        this.ambientes = ambientes;
        this.estado = estado;
        this.foto = foto;
        this.idPropietario = idPropietario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }
}
