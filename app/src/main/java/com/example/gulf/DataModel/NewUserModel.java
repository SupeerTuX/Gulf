package com.example.gulf.DataModel;
//Modelo de datos para la peticion POST para registrar nuevo usuario
public class NewUserModel {
    String NombreCliente;
    String NoCelular;
    double Puntos;

    public NewUserModel() {
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public String getNoCelular() {
        return NoCelular;
    }

    public void setNoCelular(String noCelular) {
        NoCelular = noCelular;
    }

    public double getPuntos() {
        return Puntos;
    }

    public void setPuntos(double puntos) {
        Puntos = puntos;
    }
}
