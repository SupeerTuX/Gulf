package com.example.gulf.DataModel;
//Modelo de datos complementario de la clase DataModel
public class Cliente {
    private String IDCliente;
    private String NombreCliente;
    private String Puntos;

    public Cliente(String nombreCliente, String puntos, String id) {
        NombreCliente = nombreCliente;
        Puntos = puntos;
        IDCliente = id;

    }

    public String getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(String IDCliente) {
        this.IDCliente = IDCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public String getPuntos() {
        return Puntos;
    }

    public void setPuntos(String puntos) {
        Puntos = puntos;
    }
}
