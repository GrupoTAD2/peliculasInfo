/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.proyecto.pelisinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @autor: Grupo 2
 */

public class DAO {

    private Connection conn;

    /**
     * Constructor para la clase DAO
     */
    public DAO() {
        this.conn = null;
    }

    /**
     * Método que devuelve la variable conexion
     * @return Variable conexion
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * Metodo modificador de la variable conexion
     * @param conn Variable conexion
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * Metodo que inicializa la conexion con la base de datos
     */
    public void abrirConexion() throws InstantiationException, IllegalAccessException {
        String login = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/pelisInfo";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.setConn(DriverManager.getConnection(url, login, password));
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Metodo que cierra la conexion con la base de datos
     */
    public void cerrarConexion() throws SQLException {
        this.getConn().close();
    }

    /**
     * Metodo que consulta las peliculas alojadas en la base de datos
     * @return Lista con todas las peliculas de la base de datos
     */
    public List<Pelicula> consultarPeliculas() throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula ORDER BY titulo");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    /**
     * Metodo que consulta los directores alojados en la base de datos
     * @return Lista con todos los directores de la base de datos
     */
    public List<Director> consultarDirectores() throws SQLException {
        final List<Director> listaDirectores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM director ORDER BY nombre");
        while (res.next()) {
            Director d = new Director(Integer.parseInt(res.getString("idDirector")), res.getString("nombre"), res.getString("apellidos"));
            listaDirectores.add(d);
        }
        res.close();
        stmt.close();
        return listaDirectores;
    }

    /**
     * Metodo que consulta los actores alojados en la base de datos
     * @return Lista con todos los actores de la base de datos
     */
    public List<Actor> consultarActores() throws SQLException {
        List<Actor> listaActores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM actor ORDER BY nombre");
        while (res.next()) {
            Actor a = new Actor(Integer.parseInt(res.getString("idActor")), res.getString("nombre"), res.getString("apellidos"));
            listaActores.add(a);
        }
        res.close();
        stmt.close();
        return listaActores;
    }

    /**
     * Metodo que devuelve un Director
     * @param idDirector Identificador del Director
     * @return Director correspondiente al identificador
     */
    public Director devolverDirector(Integer idDirector) throws SQLException {
        Director d = null;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM director WHERE idDirector='" + idDirector + "'");
        while (res.next()) {
            d = new Director(Integer.parseInt(res.getString("idDirector")),
                    res.getString("nombre"),
                    res.getString("apellidos"));
        }
        res.close();
        stmt.close();
        return d;
    }

    /**
     * Metodo que devuelve los actores que tiene una pelicula
     * @param idDirector Identificador de la Pelicula
     * @return Lista de Actores correspondiente al identificador de la Pelicula
     */
    public List<Actor> devolverActores(int idPelicula) throws SQLException {
        List<Actor> listaActores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelisinfo.actorpelicula P join pelisinfo.actor A on P.idActor = A.idActor WHERE P.idPelicula='" + idPelicula + "'");
        while (res.next()) {
            Actor a = new Actor(Integer.parseInt(res.getString("idActor")),
                    res.getString("nombre"),
                    res.getString("apellidos"));
            listaActores.add(a);
        }
        res.close();
        stmt.close();
        return listaActores;
    }

    /**
     * Metodo que devuelve las peliculas que cumplen con el patron de busqueda
     * @param patron Patron de busqueda
     * @return Lista de Peliculas correspondiente al patron de busqueda
     */
    public List<Pelicula> busqueda(String patron) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula WHERE titulo LIKE '%" + patron + "%' or genero LIKE '%" + patron + "%' or pais LIKE '%" + patron + "%'");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    /**
     * Metodo que devuelve una pelicula dado su identificador
     * @param idPelicula Identiciador de la Pelicula
     * @return Pelicula correspondiente a ese identificador
     */
    public Pelicula devolverPelicula(Integer idPelicula) throws SQLException {
        Pelicula p = null;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula WHERE idPelicula='" + idPelicula + "'");
        while (res.next()) {
            p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
        }
        res.close();
        stmt.close();
        return p;
    }

    /**
     * Metodo que devuelve las peliculas que cumplen con los filtrados elegidos
     * @param idDirector Identificador de Director
     * @param idActor Identificador de Actor
     * @return Lista de Peliculas que tiene ese director y actor
     */
    public List<Pelicula> filtradoCompleto(Object idDirector, Object idActor) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelisinfo.pelicula P join pelisinfo.actorpelicula A on P.idPelicula=A.idPelicula and A.idActor LIKE '" + idActor + "' and P.idDirector LIKE '" + idDirector + "'");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    /**
     * Metodo que devuelve las peliculas que cumplen con los filtrados elegidos
     * @param idDirector Identificador de Director
     * @return Lista de Peliculas que tiene ese director
     */
    public List<Pelicula> filtradoDirector(Object idDirector) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula WHERE idDirector LIKE '" + idDirector + "'");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    /**
     * Metodo que devuelve las peliculas que cumplen con los filtrados elegidos
     * @param idActor Identificador de Actor
     * @return Lista de Peliculas que tiene ese actor
     */
    public List<Pelicula> filtradoActor(Object idActor) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelisinfo.pelicula P join pelisinfo.actorpelicula A on P.idPelicula=A.idPelicula and A.idActor LIKE '" + idActor + "'");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    /**
     * Metodo que actualiza una pelicula
     * @param idPelicula Identificador de la Pelicula a editar
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
    public void actualizarPelicula(int idPelicula, Object idDirector, String titulo, int anio, String pais, String genero, String sinopsis, int duracion, String imagen, String trailer) throws SQLException {
        String updateTableSQL = "UPDATE pelicula SET idDirector='" + idDirector + "', titulo='" + titulo + "', anio='" + anio + "', pais ='" + pais + "', genero='" + genero + "', sinopsis='" + sinopsis + "', duracion='" + duracion + "', imagen='" + imagen + "', trailer='" + trailer + "'  WHERE idPelicula='" + idPelicula + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
        int retorno = preparedStatement.executeUpdate();
    }

    /**
     * Metodo que actualiza un actor correspondiente a una pelicula. Borra primero sus relaciones y despues inserta las nuevas.
     * @param idsActores Coleccion de identificadores de actores
     * @param idPelicula Identificador de la Pelicula
     */
    public void actualizarActorPelicula(Collection idsActores, int idPelicula) throws SQLException {
        String deleteSQL = "DELETE FROM actorpelicula WHERE idPelicula='" + idPelicula + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(deleteSQL);
        int retorno = preparedStatement.executeUpdate();

        for (Iterator it = idsActores.iterator(); it.hasNext();) {
            int idActor = (int) it.next();
            String insertTableSQL = "INSERT INTO actorpelicula VALUES (0, '" + idActor + "', '" + idPelicula + "')";
            preparedStatement = this.getConn().prepareStatement(insertTableSQL);
            retorno = preparedStatement.executeUpdate();
        }
    }

    /**
     * Metodo que actualiza un actor.
     * @param idActor Identificador del actor a modificar
     * @param nombre Nombre del actor
     * @param apellidos Apellidos del actor
     */
    public void actualizarActor(int idActor, String nombre, String apellidos) throws SQLException {
        String updateTableSQL = "UPDATE actor SET nombre='" + nombre + "', apellidos='" + apellidos + "' WHERE idActor='" + idActor + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
        int retorno = preparedStatement.executeUpdate();
    }

    /**
     * Metodo que actualiza un director.
     * @param idDirector Identificador del director a modificar
     * @param nombre Nombre del director
     * @param apellidos Apellidos del director
     */
    public void actualizarDirector(int idDirector, String nombre, String apellidos) throws SQLException {
        String updateTableSQL = "UPDATE director SET nombre='" + nombre + "', apellidos='" + apellidos + "' WHERE idDirector = '" + idDirector + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
        int retorno = preparedStatement.executeUpdate();
    }

    /**
     * Metodo que elimina una pelicula. Elimina la pelicula y sus posibles relaciones con actores.
     * @param idPelicula Identificador de la pelicula a eliminar
     */
    public void eliminarPelicula(int idPelicula) throws SQLException {
        String deleteSQL = "DELETE FROM actorpelicula WHERE idPelicula='" + idPelicula + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(deleteSQL);
        int retorno = preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM pelicula WHERE idPelicula='" + idPelicula + "'";
        preparedStatement = this.getConn().prepareStatement(deleteSQL);
        retorno = preparedStatement.executeUpdate();
    }

    /**
     * Metodo que elimina un actor. Elimina el actor y sus posibles relaciones con peliculas.
     * @param idActor Identificador del actor a eliminar
     */
    public void eliminarActor(int idActor) throws SQLException {
        String deleteSQL = "DELETE FROM actorpelicula WHERE idActor='" + idActor + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(deleteSQL);
        int retorno = preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM actor WHERE idActor='" + idActor + "'";
        preparedStatement = this.getConn().prepareStatement(deleteSQL);
        retorno = preparedStatement.executeUpdate();
    }

    /**
     * Metodo que elimina un director. Elimina el director y actualiza su correspondiente pelicula con un director no definido.
     * @param idDirector Identificador del director a eliminar
     */
    public void eliminarDirector(int idDirector) throws SQLException {
        String SQL = "UPDATE pelicula SET idDirector='9' WHERE idDirector='" + idDirector + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(SQL);
        int retorno = preparedStatement.executeUpdate();

        SQL = "DELETE FROM director WHERE idDirector='" + idDirector + "'";
        preparedStatement = this.getConn().prepareStatement(SQL);
        retorno = preparedStatement.executeUpdate();
    }

    /**
     * Metodo que inserta una nueva pelicula
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
    public int insertarPelicula(Object idDirector, String titulo, int anio, String pais, String genero, String sinopsis, int duracion, String imagen, String trailer) throws SQLException {
        String SQL = "INSERT INTO pelicula VALUES (0, '" + idDirector + "', '" + titulo + "', "
                + "'" + anio + "', '" + pais + "', '" + genero + "', '" + sinopsis + "', '" + duracion + "', "
                + "'" + imagen + "', '" + trailer + "')";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        int retorno = preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        int res = 0;
        if (rs.next()) {
            res = rs.getInt(1);
        }
        return res;
    }

    /**
     * Metodo que relaciona unos actores con una pelicula
     * @param idsActores Coleccion de identificadores de actores
     * @param idPelicula Identificador de la pelicula
     */
    public void insertarActorPelicula(Collection idsActores, int idPelicula) throws SQLException {
        for (Iterator it = idsActores.iterator(); it.hasNext();) {
            int idActor = (int) it.next();
            String insertTableSQL = "INSERT INTO actorpelicula VALUES (0, '" + idActor + "', '" + idPelicula + "')";
            PreparedStatement preparedStatement = this.getConn().prepareStatement(insertTableSQL);
            int retorno = preparedStatement.executeUpdate();
        }
    }
    
    /**
     * Metodo que inserta un nuevo actor en la base de datos
     * @param nombre Nombre del actor
     * @param apellidos Apellidos del actor
     */
    public void insertarActor(String nombre, String apellidos) throws SQLException {
        String SQL = "INSERT INTO actor VALUES (0, '" + nombre + "', '" + apellidos + "')";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        int retorno = preparedStatement.executeUpdate();
    }
    
    /**
     * Metodo que inserta un nuevo director en la base de datos
     * @param nombre Nombre del director
     * @param apellidos Apellidos del director
     */
    public void insertarDirector(String nombre, String apellidos) throws SQLException {
        String SQL = "INSERT INTO director VALUES (0, '" + nombre + "', '" + apellidos + "')";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        int retorno = preparedStatement.executeUpdate();
    }
    
    /**
     * Metodo que devuelve el numero de peliculas que tiene un actor dado.
     * @param idActor Identificador del actor
     * @return Numero de peliculas que tiene el actor
     */
    public int numPeliculasA(Integer idActor) throws SQLException {
        int num = 0;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT count(*) FROM actorpelicula WHERE idActor='" + idActor + "'");
        if (res.next()) {
            num=Integer.parseInt(res.getString("count(*)"));
        }
        res.close();
        stmt.close();
        return num;
    }
    
    /**
     * Metodo que devuelve el numero de peliculas que tiene un genero dado.
     * @param genero Genero cinematografico
     * @return Numero de peliculas que posee ese genero
     */
    public int numGeneros(String genero) throws SQLException {
        int num = 0;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT count(*) FROM pelicula WHERE genero='" + genero + "'");
        if (res.next()) {
            num=Integer.parseInt(res.getString("count(*)"));
        }
        res.close();
        stmt.close();
        return num;
    }
    
    /**
     * Metodo que devuelve el numero de peliculas que tiene un director.
     * @param idDirector Identificador del director
     * @return Numero de peliculas que posee ese director
     */
    public int numPeliculasD(Integer idDirector) throws SQLException {
        int num = 0;
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT count(*) FROM pelicula WHERE idDirector='" + idDirector + "'");
        if (res.next()) {
            num=Integer.parseInt(res.getString("count(*)"));
        }
        res.close();
        stmt.close();
        return num;
    }
    
    
}
