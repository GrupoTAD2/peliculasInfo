/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.proyecto.pelisinfo;

public class Pelicula {

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

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return titulo;
    }

}
