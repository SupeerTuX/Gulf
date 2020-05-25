//Modelo de datos para peticion http post, modelo de datos para agregar puntos

package com.example.gulf.DataModel;

public class AddPoints {
    private int id;
    private String fecha;
    private double puntos;
    private String ticket;

    public AddPoints(int id, String fecha, double puntos, String ticket) {
        this.id = id;
        this.fecha = fecha;
        this.puntos = puntos;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPuntos() {
        return puntos;
    }

    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
