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

public class DAO {

    private Connection conn;

    public DAO() {
        this.conn = null;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

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

    public void cerrarConexion() throws SQLException {
        this.getConn().close();
    }

    public List<Pelicula> consultarPeliculas() throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula");
        while (res.next()) {
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }

    public List<Director> consultarDirectores() throws SQLException {
        final List<Director> listaDirectores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM director");
        while (res.next()) {
            Director d = new Director(Integer.parseInt(res.getString("idDirector")), res.getString("nombre"), res.getString("apellidos"));
            listaDirectores.add(d);
        }
        res.close();
        stmt.close();
        return listaDirectores;
    }

    public List<Actor> consultarActores() throws SQLException {
        List<Actor> listaActores = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM actor");
        while (res.next()) {
            Actor a = new Actor(Integer.parseInt(res.getString("idActor")), res.getString("nombre"), res.getString("apellidos"));
            listaActores.add(a);
        }
        res.close();
        stmt.close();
        return listaActores;
    }

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
    
    public List<Pelicula> busqueda(String patron) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula WHERE titulo LIKE '%"+patron+"%' or genero LIKE '%"+patron+"%' or pais LIKE '%"+patron+"%'");
        while (res.next()) {            
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }
    
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
    
    public List<Pelicula> filtradoCompleto(Object idDirector, Object idActor) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelisinfo.pelicula P join pelisinfo.actorpelicula A on P.idPelicula=A.idPelicula and A.idActor LIKE '"+idActor+"' and P.idDirector LIKE '"+idDirector+"'");
        while (res.next()) {            
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }
    
    public List<Pelicula> filtradoDirector(Object idDirector) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelicula WHERE idDirector LIKE '"+idDirector+"'");
        while (res.next()) {            
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }
    
    public List<Pelicula> filtradoActor(Object idActor) throws SQLException {
        List<Pelicula> listaPeliculas = new ArrayList();
        Statement stmt = this.getConn().createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM pelisinfo.pelicula P join pelisinfo.actorpelicula A on P.idPelicula=A.idPelicula and A.idActor LIKE '"+idActor+"'");
        while (res.next()) {            
            Pelicula p = new Pelicula(Integer.parseInt(res.getString("idPelicula")), Integer.parseInt(res.getString("idDirector")), res.getString("titulo"), Integer.parseInt(res.getString("anio")), res.getString("pais"), res.getString("genero"), res.getString("sinopsis"), Integer.parseInt(res.getString("duracion")), res.getString("imagen"), res.getString("trailer"));
            listaPeliculas.add(p);
        }
        res.close();
        stmt.close();
        return listaPeliculas;
    }
    
    public void actualizarPelicula(int idPelicula, Object idDirector, String titulo, int anio, String pais, String genero, String sinopsis, int duracion, String imagen, String trailer) throws SQLException {
        String updateTableSQL = "UPDATE pelicula SET idDirector='" + idDirector + "', titulo='" + titulo + "', anio='" + anio + "', pais ='" + pais + "', genero='" + genero + "', sinopsis='" + sinopsis + "', duracion='" + duracion + "', imagen='" + imagen + "', trailer='" + trailer + "'  WHERE idPelicula='" + idPelicula + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
        int retorno = preparedStatement.executeUpdate();
    }

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
    
    public void eliminarPelicula(int idPelicula) throws SQLException {
        String deleteTableSQL = "DELETE FROM pelicula WHERE idPelicula='" + idPelicula + "'";
        System.out.print("SQL: " + deleteTableSQL);
        PreparedStatement preparedStatement = this.getConn().prepareStatement(deleteTableSQL);
        int retorno = preparedStatement.executeUpdate();
    }
    
    public void eliminarActor(int idActor) throws SQLException {
        String deleteTableSQL = "DELETE FROM actor WHERE idActor='" + idActor + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(deleteTableSQL);
        int retorno = preparedStatement.executeUpdate();
    }
    
    public void eliminarDirector(int idDirector) throws SQLException {
        String deleteTableSQL = "DELETE FROM director WHERE idDirector='" + idDirector + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(deleteTableSQL);
        int retorno = preparedStatement.executeUpdate();
    }
    
    public void actualizarActor(int idActor, String nombre, String apellidos) throws SQLException {
        String updateTableSQL = "UPDATE actor SET nombre='" + nombre + "', apellidos='" + apellidos + "' WHERE idActor='" + idActor + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
    }
    
    public void actualizarDirector(int idDirector, String nombre, String apellidos) throws SQLException {
        String updateTableSQL = "UPDATE director SET nombre='" + nombre + "', apellidos='" + apellidos + "' WHERE idDirector = '" + idDirector + "'";
        PreparedStatement preparedStatement = this.getConn().prepareStatement(updateTableSQL);
    }
    
}
