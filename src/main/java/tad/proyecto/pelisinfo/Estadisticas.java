/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.proyecto.pelisinfo;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

/**
 * @autor: Grupo 2
 */

@Theme("mytheme")
@Title("pelisInfo")
@Widgetset("tad.proyecto.pelisinfo.MyAppWidgetset")
public class Estadisticas extends UI {

    /**
     * Metodo de inicializacion de la clase
     * @param vaadinRequest VaadinRequest 
     */
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {
            HorizontalLayout v2h2 = new HorizontalLayout();
            v2h2.setMargin(true);
            
            final VerticalLayout v2h1 = new VerticalLayout();
            v2h1.setMargin(true);

            final VerticalSplitPanel v2 = new VerticalSplitPanel();
            v2.addComponent(v2h2);
            v2.addComponent(v2h1);
            v2.setSplitPosition(10, Unit.PERCENTAGE);
            v2.setLocked(true);
            
            VerticalLayout v1 = new VerticalLayout();
            v1.setMargin(true);

            HorizontalSplitPanel layout = new HorizontalSplitPanel();
            layout.addComponent(v1);
            layout.addComponent(v2);
            layout.setSplitPosition(30, Unit.PERCENTAGE);

            setContent(layout);
            
            Link pri = new Link("Principal", new ExternalResource("/Principal"));
            Link est = new Link("Estadísticas", new ExternalResource("/Estadisticas"));
            Link adm = new Link("Administración", new ExternalResource("/Admin"));
            v2h2.addComponent(pri);
            v2h2.addComponent(new Label(" - "));
            v2h2.addComponent(est);
            v2h2.addComponent(new Label(" - "));
            v2h2.addComponent(adm);

            final DAO dao = new DAO();
            dao.abrirConexion();

            final List<Pelicula> listaPeliculas = dao.consultarPeliculas();
            final List<Director> listaDirectores = dao.consultarDirectores();
            final List<Actor> listaActores = dao.consultarActores();

            Tree tree = new Tree("");
            String a = "Estadisticas";
            tree.addItem(a);
            String aa = "Peliculas según su duración";
            tree.addItem(aa);
            tree.setParent(aa, a);
            tree.setChildrenAllowed(aa, false);
            String ab = "Peliculas según su género";
            tree.addItem(ab);
            tree.setParent(ab, a);
            tree.setChildrenAllowed(ab, false);
            String ac = "Numero de peliculas según actor";
            tree.addItem(ac);
            tree.setParent(ac, a);
            tree.setChildrenAllowed(ac, false);
            String ad = "Numero de peliculas según director";
            tree.addItem(ad);
            tree.setParent(ad, a);
            tree.setChildrenAllowed(ad, false);

            tree.setSelectable(true);
            tree.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    v2h1.removeAllComponents();
                    if (event.getProperty().getValue().toString().contains("duración")) {
                        Chart chart = new Chart(ChartType.PIE);
                        Configuration conf = chart.getConfiguration();
                        conf.setTitle("Peliculas");
                        conf.setSubTitle("Según su duración en minutos");

                        PlotOptionsPie options = new PlotOptionsPie();
                        options.setInnerSize(0);
                        options.setSize("75%");
                        options.setCenter("50%", "50%");
                        conf.setPlotOptions(options);

                        DataSeries series = new DataSeries();
                        for (Pelicula p : listaPeliculas) {
                            series.add(new DataSeriesItem(p.getTitulo(), p.getDuracion()));
                        }
                        conf.addSeries(series);
                        v2h1.addComponent(chart);
                    } else if (event.getProperty().getValue().toString().contains("género")) {
                        Chart chart = new Chart(ChartType.COLUMN);
                        chart.setWidth("400px");
                        chart.setHeight("300px");
                        Configuration conf = chart.getConfiguration();
                        conf.setTitle("Peliculas");
                        conf.setSubTitle("Agrupadas según su género");

                        PlotOptionsLine plotOptions = new PlotOptionsLine();
                        plotOptions.setMarker(new Marker(false));
                        conf.setPlotOptions(plotOptions);

                        ListSeries series = new ListSeries("Numero de peliculas");
                        int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;
                        try {
                            dao.abrirConexion();
                            i1 = dao.numGeneros("Acción");
                            i2 = dao.numGeneros("Ciencia Ficción");
                            i3 = dao.numGeneros("Drama");
                            i4 = dao.numGeneros("Romance");
                            i5 = dao.numGeneros("Novela de Suspense");
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InstantiationException ex) {
                            Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        series.addData(i1);
                        series.addData(i2);
                        series.addData(i3);
                        series.addData(i4);
                        series.addData(i5);

                        conf.addSeries(series);

                        XAxis xaxis = new XAxis();
                        xaxis.setTitle("Género");
                        xaxis.setCategories("Acción", "Ciencia Ficción", "Drama", "Romance", "Novela de suspense");
                        conf.addxAxis(xaxis);

                        YAxis yayis = new YAxis();
                        yayis.setTitle("Número de peliculas");
                        conf.addyAxis(yayis);

                        v2h1.addComponent(chart);
                    } else if (event.getProperty().getValue().toString().contains("actor")) {
                        Chart chart = new Chart(ChartType.PIE);
                        Configuration conf = chart.getConfiguration();
                        conf.setTitle("Actores");
                        conf.setSubTitle("Número de peliculas que tienen");

                        PlotOptionsPie options = new PlotOptionsPie();
                        options.setInnerSize(0);
                        options.setSize("75%");
                        options.setCenter("50%", "50%");
                        conf.setPlotOptions(options);

                        DataSeries series = new DataSeries();
                        for (Actor a : listaActores) {
                            int i = 0;
                            try {
                                dao.abrirConexion();
                                i = dao.numPeliculasA(a.getIdActor());
                            } catch (InstantiationException ex) {
                                Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            series.add(new DataSeriesItem(a.getNombreCompleto(), i));
                        }
                        conf.addSeries(series);

                        v2h1.addComponent(chart);
                    } else if (event.getProperty().getValue().toString().contains("director")) {
                        Chart chart = new Chart(ChartType.PIE);
                        Configuration conf = chart.getConfiguration();
                        conf.setTitle("Directores");
                        conf.setSubTitle("Número de peliculas que tienen");

                        PlotOptionsPie options = new PlotOptionsPie();
                        options.setInnerSize(0);
                        options.setSize("75%");
                        options.setCenter("50%", "50%");
                        conf.setPlotOptions(options);

                        DataSeries series = new DataSeries();
                        for (Director d : listaDirectores) {
                            int i = 0;
                            try {
                                dao.abrirConexion();
                                i = dao.numPeliculasD(d.getIdDirector());
                            } catch (InstantiationException ex) {
                                Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            series.add(new DataSeriesItem(d.getNombreCompleto(), i));
                        }
                        conf.addSeries(series);

                        v2h1.addComponent(chart);
                    }
                }
            });

            v1.addComponent(tree);

            dao.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Estadisticas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @WebServlet(urlPatterns = "/Estadisticas/*", name = "Estadisticas", asyncSupported = true)
    @VaadinServletConfiguration(ui = Estadisticas.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
