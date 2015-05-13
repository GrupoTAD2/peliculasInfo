/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.proyecto.pelisinfo;

/**
 * @autor: Grupo 2
 */

public class Actor {
    
    //Campos de la clase
    private int idActor;
    private String nombre;
    private String apellidos;
    private String nombreCompleto;

    /**
     * Constructor para la clase Actor
     * @param idActor Identificador del actor
     * @param nombre Nombre del actor
     * @param apellidos Apellidos del actor
     */
    public Actor(int idActor, String nombre, String apellidos) {
        this.idActor = idActor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreCompleto=nombre+" "+apellidos;
    }

    /**
     * Método que devuelve el id del Actor
     * @return El identificador del Actor
     */
    public int getIdActor() {
        return idActor;
    }

    /**
     * Método modificador del id del Actor
     * @param idActor Identificador del Actor
     */
    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    /**
     * Método que devuelve el nombre del Actor
     * @return El nombre del Actor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método modificador del nombre del Actor
     * @param nombre Nombre del Actor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve los apellidos del Actor
     * @return Los apellidos del Actor
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Método modificador del apellido del Actor
     * @param apellidos Apellidos del Actor
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Método que devuelve una cadena con el id del Actor
     * @return Cadena con el identificador del Actor
     */
    @Override
    public String toString() {
        return ""+idActor;
    }
    
    /**
     * Método que devuelve el nombre y apellidos del Actor
     * @return Nombre mas apellidos del Actor
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }
}
