package com.example.gulf.DataModel;

// Modelo de datos para del usuario,
// leiodo del codigo QR del auto
public class UserModel {
    private String userID;
    private String nombre;

    public UserModel() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
