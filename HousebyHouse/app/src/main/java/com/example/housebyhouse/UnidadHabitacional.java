package com.example.housebyhouse;

import java.util.Date;

public class UnidadHabitacional {
    private String Tipo;
    private int id;
    private long precio;
    private String direccion;
    private String fechaRecepcion;
    private String EstaArrendada;
    private String NombrePropietario;
    private String Telefono;

    public UnidadHabitacional(int id,String Tipo,long precio,String direccion,String fechaRecepcion,String EstaArrendada,
                              String NombrePropietario,String Telefono){
        this.id=id;
        this.Tipo=Tipo;
        this.precio=precio;
        this.direccion=direccion;
        this.fechaRecepcion=fechaRecepcion;
        this.EstaArrendada=EstaArrendada;
        this.NombrePropietario=NombrePropietario;
        this.Telefono=Telefono;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String    getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getEstaArrendada() {
        return EstaArrendada;
    }

    public void setEstaArrendada(String estaArrendada) {
        EstaArrendada = estaArrendada;
    }

    public String getNombrePropietario() {
        return NombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        NombrePropietario = nombrePropietario;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }
}
