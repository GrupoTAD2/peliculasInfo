package tad.proyecto.pelisinfo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
@Theme("mytheme")
@Title("pelisInfo")
@Widgetset("tad.proyecto.pelisinfo.MyAppWidgetset")
public class Admin extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {
            VerticalLayout v1 = new VerticalLayout();
            v1.setMargin(true);

            final VerticalLayout v2 = new VerticalLayout();
            v2.setMargin(true);

            HorizontalSplitPanel layout = new HorizontalSplitPanel();
            layout.addComponent(v1);
            layout.addComponent(v2);
            layout.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);

            setContent(layout);

            final DAO dao = new DAO();
            dao.abrirConexion();

            final List<Pelicula> listaPeliculas = dao.consultarPeliculas();
            final List<Director> listaDirectores = dao.consultarDirectores();
            final List<Actor> listaActores = dao.consultarActores();

            final BeanItemContainer<Director> bdir = new BeanItemContainer(Director.class, listaDirectores);

            Tree tree1 = new Tree("Administración:");
            String pel = "Peliculas";
            tree1.addItem(pel);
            for (Pelicula p : listaPeliculas) {
                tree1.addItem(p);
                tree1.setParent(p, pel);
                tree1.setChildrenAllowed(p, false);
            }
            Tree tree2 = new Tree("");
            String act = "Actores";
            tree2.addItem(act);
            for (Actor a : listaActores) {
                tree2.addItem(a);
                tree2.setParent(a, act);
                tree2.setItemCaption(a, a.getNombreCompleto());
                tree2.setChildrenAllowed(a, false);
            }
            Tree tree3 = new Tree("");
            String dic = "Directores";
            tree3.addItem(dic);
            for (Director d : listaDirectores) {
                tree3.addItem(d);
                tree3.setParent(d, dic);
                tree3.setItemCaption(d, d.getNombreCompleto());
                tree3.setChildrenAllowed(d, false);
            }
            tree1.setSelectable(true);
            tree1.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    try {
                        v2.removeAllComponents();
                        final Pelicula p = (Pelicula) event.getProperty().getValue();

                        final TextField titulo = new TextField("Titulo", p.getTitulo());
                        titulo.setColumns(25);
                        v2.addComponent(titulo);

                        final TextField pais = new TextField("Pais", p.getPais());
                        v2.addComponent(pais);

                        final TextField anio = new TextField("Año", Integer.toString(p.getAnio()));
                        v2.addComponent(anio);

                        final TextField genero = new TextField("Genero", p.getGenero());
                        v2.addComponent(genero);

                        final TextField duracion = new TextField("Duracion", Integer.toString(p.getDuracion()));
                        v2.addComponent(duracion);

                        final TextArea sinopsis = new TextArea("Sinopsis", p.getSinopsis());
                        sinopsis.setColumns(30);
                        v2.addComponent(sinopsis);

                        final TextField portada = new TextField("Portada", p.getImagen());
                        portada.setColumns(30);
                        v2.addComponent(portada);

                        final TextField trailer = new TextField("Trailer", p.getTrailer());
                        trailer.setColumns(30);
                        v2.addComponent(trailer);

                        //Seleccion de director
                        final ComboBox director = new ComboBox("Director", bdir);
                        director.setItemCaptionPropertyId("nombreCompleto");
                        dao.abrirConexion();
                        director.setInputPrompt(dao.devolverDirector(p.getIdDirector()).getNombreCompleto());
                        v2.addComponent(director);

                        //Seleccion de actores
                        final TwinColSelect actores = new TwinColSelect();
                        List<Actor> actoresPeli = dao.devolverActores(p.getIdPelicula());
                        for (Actor a : listaActores) {
                            actores.addItem(a.getIdActor());
                            for (Actor b : actoresPeli) {
                                if (a.getIdActor() == b.getIdActor()) {
                                    actores.select(a.getIdActor());
                                    break;
                                }
                            }
                            actores.setItemCaption(a.getIdActor(), a.getNombre() + " " + a.getApellidos());
                        }
                        actores.setRows(listaActores.size());
                        actores.setNullSelectionAllowed(true);
                        actores.setMultiSelect(true);
                        actores.setImmediate(true);
                        actores.setLeftColumnCaption("Actores disponibles");
                        actores.setRightColumnCaption("Actores seleccionados");
                        v2.addComponent(actores);

                        Button button = new Button("Guardar");
                        button.addClickListener(new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent event) {
                                try {
                                    dao.abrirConexion();
                                    if (director.getValue() == null) {
                                        Notification.show("Error", "Seleccione el director",
                                                Notification.Type.ERROR_MESSAGE);
                                    } else {
                                        dao.actualizarPelicula(p.getIdPelicula(), director.getValue(), titulo.getValue(), Integer.parseInt(anio.getValue()), pais.getValue(), genero.getValue(), sinopsis.getValue(), Integer.parseInt(duracion.getValue()), portada.getValue(), trailer.getValue());
                                        dao.actualizarActorPelicula((Collection) actores.getValue(), p.getIdPelicula());
                                        Notification.show("Hecho", "La pelicula ha sido actualizada correctamente",
                                                Notification.Type.TRAY_NOTIFICATION);
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        v2.addComponent(button);
                    } catch (SQLException ex) {
                        Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            dao.cerrarConexion();
                        } catch (SQLException ex) {
                            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            tree2.setSelectable(true);
            tree2.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    v2.removeAllComponents();
                    final Actor a = (Actor) event.getProperty().getValue();
                    final TextField nombre = new TextField("Nombre", a.getNombre());
                    nombre.setColumns(25);
                    v2.addComponent(nombre);
                    final TextField apellidos = new TextField("Apellidos", a.getApellidos());
                    apellidos.setColumns(25);
                    v2.addComponent(apellidos);
                    Button button = new Button("Guardar");
                    button.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            dao.abrirConexion();
                            //actualizar actor
                        }
                    });
                    v2.addComponent(button);
                }
            });
            
            tree3.setSelectable(true);
            tree3.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    v2.removeAllComponents();
                    final Director d = (Director) event.getProperty().getValue();
                    final TextField nombre = new TextField("Nombre", d.getNombre());
                    nombre.setColumns(25);
                    v2.addComponent(nombre);
                    final TextField apellidos = new TextField("Apellidos", d.getApellidos());
                    apellidos.setColumns(25);
                    v2.addComponent(apellidos);
                    Button button = new Button("Guardar");
                    button.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            dao.abrirConexion();
                            //actualizar director
                        }
                    });
                    v2.addComponent(button);
                }
            });

            v1.addComponent(tree1);
            v1.addComponent(tree2);
            v1.addComponent(tree3);
            dao.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @WebServlet(urlPatterns = "/Admin/*", name = "Admin", asyncSupported = true)
    @VaadinServletConfiguration(ui = Admin.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
