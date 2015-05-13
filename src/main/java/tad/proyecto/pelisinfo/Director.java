/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.proyecto.pelisinfo;

/**
 * @autor: Grupo 2
 */

public class Director {
    
    //Campos de la clase
    private int idDirector;
    private String nombre;
    private String apellidos;
    private String nombreCompleto;

    /**
     * Constructor para la clase Director
     * @param idDirector Identificador del Director
     * @param nombre Nombre del Director
     * @param apellidos Apellidos del Director
     */
    public Director(int idDirector, String nombre, String apellidos) {
        this.idDirector = idDirector;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreCompleto=nombre+" "+apellidos;
    }

    /**
     * Método que devuelve el id del Director
     * @return El identificador del Director
     */
    public int getIdDirector() {
        return idDirector;
    }

    /**
     * Método modificador del id del Director
     * @param idDirector Identificador del Director
     */
    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    /**
     * Método que devuelve el nombre del Director
     * @return El nombre del Director
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método modificador del nombre del Director
     * @param nombre Nombre del Director
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve los apellidos del Director
     * @return Los apellidos del Director
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Método modificador del apellido del Director
     * @param apellidos Apellidos del Director
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Método que devuelve una cadena con el id del Director
     * @return Cadena con el identificador del Director
     */
    @Override
    public String toString() {
        return ""+idDirector;
    }
    
    /**
     * Método que devuelve el nombre y apellidos del Director
     * @return Nombre mas apellidos del Director
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }
}
