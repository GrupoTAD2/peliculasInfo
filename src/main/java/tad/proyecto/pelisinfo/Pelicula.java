/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.proyecto.pelisinfo;

/**
 * @autor: Grupo 2
 */

public class Pelicula {

    //Campos de la clase
    private int idPelicula;
    private int idDirector;
    private String titulo;
    private int anio;
    private String pais;
    private String genero;
    private String sinopsis;
    private int duracion;
    private String imagen;
    private String trailer;

    /**
     * Constructor para la clase Pelicula
     * @param idPelicula Identificador de la Pelicula
     * @param idDirector Identificador del Director
     * @param titulo Titulo de la Pelicula
     * @param anio Anio en el que se estreno la Pelicula
     * @param pais Pais en el que se hizo la Pelicula
     * @param genero Genero cinematografico de la Pelicula
     * @param sinopsis Sinopsis de la Pelicula
     * @param duracion Durecion de la Pelicula
     * @param imagen Enlace a la imagen de la portada de la Pelicula
     * @param trailer Enlace del trailer de la Pelicula
     */
    public Pelicula(int idPelicula, int idDirector, String titulo, int anio, String pais, String genero, String sinopsis, int duracion, String imagen, String trailer) {
        this.idPelicula = idPelicula;
        this.idDirector = idDirector;
        this.titulo = titulo;
        this.anio = anio;
        this.pais = pais;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.duracion = duracion;
        this.imagen = imagen;
        this.trailer = trailer;
    }

    /**
     * Método que devuelve el id de la Pelicula
     * @return El identificador de la Pelicula
     */
    public int getIdPelicula() {
        return idPelicula;
    }

    /**
     * Método modificador del id de la Pelicula
     * @param idPelicula Identificador de la Pelicula
     */
    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    /**
     * Método que devuelve el id del director de la Pelicula
     * @return El identificador del director de la Pelicula
     */
    public int getIdDirector() {
        return idDirector;
    }

    /**
     * Método modificador del id de la Pelicula
     * @param idPelicula Identificador de la Pelicula
     */
    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    /**
     * Método que devuelve el titulo de la Pelicula
     * @return El titulo de la Pelicula
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Método modificador del titulo de la Pelicula
     * @param titulo Titulo de la Pelicula
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Método que devuelve el anio de estreno de la Pelicula
     * @return El anio de la Pelicula
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Método modificador del anio de estreno de la Pelicula
     * @param anio Anio de la Pelicula
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Método que devuelve el pais de origen de la Pelicula
     * @return El pais de la Pelicula
     */
    public String getPais() {
        return pais;
    }

    /**
     * Método modificador de orgine de la Pelicula
     * @param pais Pais de la Pelicula
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Método que devuelve el genero cinematografico de la Pelicula
     * @return El genero de la Pelicula
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Método modificador del genero cinematografico de la Pelicula
     * @param genero Genero de la Pelicula
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Método que devuelve la sinopsis de la Pelicula
     * @return La sinopsis de la Pelicula
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Método modificador de la sinopsis de la Pelicula
     * @param sinopsis Sinopsis de la Pelicula
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * Método que devuelve la duracion de la Pelicula
     * @return La duracion de la Pelicula
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Método modificador de la duracion de la Pelicula
     * @param duracion Duracion de la Pelicula
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Método que devuelve el enlace a la portada de la Pelicula
     * @return La imagen de la Pelicula
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Método modificador de la portada de la Pelicula
     * @param imagen Imagen de la Pelicula
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Método que devuelve el enlace al trailer de la Pelicula
     * @return El trailer de la Pelicula
     */
    public String getTrailer() {
        return trailer;
    }

    /**
     * Método modificador del trailer de la Pelicula
     * @param trailer Trailer de la Pelicula
     */
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    /**
     * Método que devuelve una cadena con el titulo de la Pelicula
     * @return Cadena con el titulo de la Pelicula
     */
    @Override
    public String toString() {
        return titulo;
    }

}
