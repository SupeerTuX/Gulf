package com.example.gulf.DataModel;

// Modelo de datos para el ticket, cadena json leida desde el ticket de venta
public class TicketModel {
    private String ticketID;
    private String monto;

    public TicketModel() {
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
