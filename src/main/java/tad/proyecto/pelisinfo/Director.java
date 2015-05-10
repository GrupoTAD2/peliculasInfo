/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.proyecto.pelisinfo;

/**
 *
 * @author Patricia
 */
public class Director {
    private int idDirector;
    private String nombre;
    private String apellidos;
    private String nombreCompleto;

    public Director(int idDirector, String nombre, String apellidos) {
        this.idDirector = idDirector;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreCompleto=nombre+" "+apellidos;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return ""+idDirector;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
}
