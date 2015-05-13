package tad.proyecto.pelisinfo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Flash;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Theme("mytheme")
@Title("pelisInfo")
@Widgetset("tad.proyecto.pelisinfo.MyAppWidgetset")
public class Principal extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout h1 = new HorizontalLayout();
        h1.setMargin(true);

        final HorizontalLayout h2 = new HorizontalLayout();
        h2.setMargin(true);

        VerticalLayout v1 = new VerticalLayout();
        v1.setMargin(true);

        VerticalSplitPanel v2 = new VerticalSplitPanel();
        v2.addComponent(h1);
        v2.addComponent(h2);
        v2.setSplitPosition(20, Unit.PERCENTAGE);

        HorizontalSplitPanel layout = new HorizontalSplitPanel();
        layout.addComponent(v1);
        layout.addComponent(v2);
        layout.setSplitPosition(17, Unit.PERCENTAGE);

        setContent(layout);

        List<Pelicula> listaPeliculas = new ArrayList();
        List<Director> listaDirectores = new ArrayList();
        List<Actor> listaActores = new ArrayList();

        final DAO dao = new DAO();
        try {
            dao.abrirConexion();
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            listaPeliculas = dao.consultarPeliculas();
            listaDirectores = dao.consultarDirectores();
            listaActores = dao.consultarActores();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dao.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Table table = new Table();

        table.addContainerProperty("Portada", Image.class, null);
        table.addContainerProperty("Titulo", String.class, null);
        table.addContainerProperty("Año", Integer.class, null);
        table.addContainerProperty("Pais", String.class, null);
        table.addContainerProperty("Duracion", Integer.class, null);
        table.addContainerProperty("Trailer", Flash.class, null);

        table.setPageLength(table.size());
        table.setSelectable(true);

        for (Pelicula p : listaPeliculas) {
            Image portada = new Image();
            final ExternalResource externalResource = new ExternalResource(p.getImagen());
            portada.setSource(externalResource);
            portada.setWidth("180");
            Flash trailer = new Flash(null, new ExternalResource(p.getTrailer()));
            trailer.setParameter("allowFullScreen", "true");
            trailer.setWidth("340");
            trailer.setHeight("300");
            table.addItem(new Object[]{portada, p.getTitulo(), p.getAnio(), p.getPais(), p.getDuracion(), trailer}, p.getIdPelicula());
        }

        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    dao.abrirConexion();
                } catch (InstantiationException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                Pelicula p = null;
                Director d = null;
                List<Actor> a = null;
                try {
                    p = dao.devolverPelicula((Integer) event.getProperty().getValue());
                    d = dao.devolverDirector(p.getIdDirector());
                    a = dao.devolverActores((Integer) event.getProperty().getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                final Window window = new Window("Información detallada:");
                window.setWidth(700.0f, Unit.PIXELS);
                final FormLayout content = new FormLayout();
                Label datos = new Label(
                        "<b>Informacion:</b> Pelicula del " + p.getAnio() + "," + p.getDuracion() + "min.," + p.getPais() + "<br>"
                        + "<b>Genero:</b> " + p.getGenero() + "<br>"
                        + "<b>Director:</b> " + d.getNombreCompleto() + "<br>"
                        + "<b>Titulo orginal:</b> " + p.getTitulo() + "<br>"
                        + "<b>Sinopsis:</b> " + p.getSinopsis() + "<br>"
                        + "<b>Protagonistas:</b><br> ");
                datos.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                content.addComponent(datos);
                for (Actor ac : a) {
                    Label datos2 = new Label("- " + ac.getNombreCompleto() + "</br>");
                    datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                    datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                    content.addComponent(datos2);
                }
                content.setMargin(true);
                window.setContent(content);
                window.center();
                window.setModal(true);
                window.setResizable(false);
                window.setClosable(true);
                UI.getCurrent().addWindow(window);
            }
        });

        final TextField buscar = new TextField();
        h1.addComponent(buscar);
        Button button1 = new Button("Buscar");
        button1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                h2.removeAllComponents();
                Table table2 = new Table();

                table2.addContainerProperty("Portada", Image.class, null);
                table2.addContainerProperty("Titulo", String.class, null);
                table2.addContainerProperty("Año", Integer.class, null);
                table2.addContainerProperty("Pais", String.class, null);
                table2.addContainerProperty("Duracion", Integer.class, null);
                table2.addContainerProperty("Trailer", Flash.class, null);

                List<Pelicula> pel = new ArrayList<>();
                try {
                    dao.abrirConexion();
                    pel = dao.busqueda(buscar.getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        dao.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (Pelicula p : pel) {
                    Image portada = new Image();
                    final ExternalResource externalResource = new ExternalResource(p.getImagen());
                    portada.setSource(externalResource);
                    portada.setWidth("180");
                    Flash trailer = new Flash(null, new ExternalResource(p.getTrailer()));
                    trailer.setParameter("allowFullScreen", "true");
                    trailer.setWidth("340");
                    trailer.setHeight("300");
                    table2.addItem(new Object[]{portada, p.getTitulo(), p.getAnio(), p.getPais(), p.getDuracion(), trailer}, p.getIdPelicula());
                }
                table2.setPageLength(table2.size());
                table2.setSelectable(true);
                table2.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {
                        try {
                            dao.abrirConexion();
                        } catch (InstantiationException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Pelicula p = null;
                        Director d = null;
                        List<Actor> a = null;
                        try {
                            p = dao.devolverPelicula((Integer) event.getProperty().getValue());
                            d = dao.devolverDirector(p.getIdDirector());
                            a = dao.devolverActores((Integer) event.getProperty().getValue());
                        } catch (SQLException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        final Window window = new Window("Información detallada:");
                        window.setWidth(700.0f, Unit.PIXELS);
                        final FormLayout content = new FormLayout();
                        Label datos = new Label(
                                "<b>Informacion:</b> Pelicula del " + p.getAnio() + "," + p.getDuracion() + "min.," + p.getPais() + "<br>"
                                + "<b>Genero:</b> " + p.getGenero() + "<br>"
                                + "<b>Director:</b> " + d.getNombreCompleto() + "<br>"
                                + "<b>Titulo orginal:</b> " + p.getTitulo() + "<br>"
                                + "<b>Sinopsis:</b> " + p.getSinopsis() + "<br>"
                                + "<b>Protagonistas:</b><br> ");
                        datos.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                        content.addComponent(datos);
                        for (Actor ac : a) {
                            Label datos2 = new Label("- " + ac.getNombreCompleto() + "</br>");
                            datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                            datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                            content.addComponent(datos2);
                        }
                        content.setMargin(true);
                        window.setContent(content);
                        window.center();
                        window.setModal(true);
                        window.setResizable(false);
                        window.setClosable(true);
                        UI.getCurrent().addWindow(window);
                    }
                });
                h2.addComponent(table2);
            }
        });
        h1.addComponent(button1);
        h2.addComponent(table);

        BeanItemContainer<Director> bdir = new BeanItemContainer(Director.class, listaDirectores);
        final ComboBox cd = new ComboBox("Directores", bdir);
        cd.setItemCaptionPropertyId("nombreCompleto");
        v1.addComponent(cd);

        BeanItemContainer<Director> adir = new BeanItemContainer(Actor.class, listaActores);
        final ComboBox ca = new ComboBox("Actores", adir);
        ca.setItemCaptionPropertyId("nombreCompleto");
        v1.addComponent(ca);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.addStyleName("btn-filtrar");

        btnFiltrar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                h2.removeAllComponents();
                Table table3 = new Table();

                table3.addContainerProperty("Portada", Image.class, null);
                table3.addContainerProperty("Titulo", String.class, null);
                table3.addContainerProperty("Año", Integer.class, null);
                table3.addContainerProperty("Pais", String.class, null);
                table3.addContainerProperty("Duracion", Integer.class, null);
                table3.addContainerProperty("Trailer", Flash.class, null);

                List<Pelicula> pel = new ArrayList<>();
                try {
                    dao.abrirConexion();
                    if ((cd.getValue() == "" || cd.getValue() == null) && (ca.getValue() == "" || ca.getValue() == null)) {
                        pel = dao.consultarPeliculas();
                    } else if (cd.getValue() == "" || cd.getValue() == null) {
                        pel = dao.filtradoActor(ca.getValue());
                    } else if (ca.getValue() == "" || ca.getValue() == null) {
                        pel = dao.filtradoDirector(cd.getValue());
                    } else {
                        pel = dao.filtradoCompleto(cd.getValue(), ca.getValue());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        dao.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (Pelicula p : pel) {
                    Image portada = new Image();
                    final ExternalResource externalResource = new ExternalResource(p.getImagen());
                    portada.setSource(externalResource);
                    portada.setWidth("180");
                    Flash trailer = new Flash(null, new ExternalResource(p.getTrailer()));
                    trailer.setParameter("allowFullScreen", "true");
                    trailer.setWidth("340");
                    trailer.setHeight("300");
                    table3.addItem(new Object[]{portada, p.getTitulo(), p.getAnio(), p.getPais(), p.getDuracion(), trailer}, p.getIdPelicula());
                }
                table3.setPageLength(table3.size());
                table3.setSelectable(true);
                table3.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {
                        try {
                            dao.abrirConexion();
                        } catch (InstantiationException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Pelicula p = null;
                        Director d = null;
                        List<Actor> a = null;
                        try {
                            p = dao.devolverPelicula((Integer) event.getProperty().getValue());
                            d = dao.devolverDirector(p.getIdDirector());
                            a = dao.devolverActores((Integer) event.getProperty().getValue());
                        } catch (SQLException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        final Window window = new Window("Información detallada:");
                        window.setWidth(700.0f, Unit.PIXELS);
                        final FormLayout content = new FormLayout();
                        Label datos = new Label(
                                "<b>Informacion:</b> Pelicula del " + p.getAnio() + "," + p.getDuracion() + "min.," + p.getPais() + "<br>"
                                + "<b>Genero:</b> " + p.getGenero() + "<br>"
                                + "<b>Director:</b> " + d.getNombreCompleto() + "<br>"
                                + "<b>Titulo orginal:</b> " + p.getTitulo() + "<br>"
                                + "<b>Sinopsis:</b> " + p.getSinopsis() + "<br>"
                                + "<b>Protagonistas:</b><br> ");
                        datos.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                        content.addComponent(datos);
                        for (Actor ac : a) {
                            Label datos2 = new Label("- " + ac.getNombreCompleto() + "</br>");
                            datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                            datos2.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                            content.addComponent(datos2);
                        }
                        content.setMargin(true);
                        window.setContent(content);
                        window.center();
                        window.setModal(true);
                        window.setResizable(false);
                        window.setClosable(true);
                        UI.getCurrent().addWindow(window);
                    }
                });
                h2.addComponent(table3);
            }
        });
        v1.addComponent(btnFiltrar);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Principal.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
