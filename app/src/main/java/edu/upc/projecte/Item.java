package edu.upc.projecte;

import java.io.Serializable;

public class Item implements Serializable {
    private String idItem;
    private String nombre;
    private String descripcion;
    private double precio;


    public Item(String idItem,String nombre, String descripcion, double precio) {
        this.idItem = idItem;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public String getIdItem() {
        return idItem;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
