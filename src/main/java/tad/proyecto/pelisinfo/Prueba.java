package tad.proyecto.pelisinfo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.vaadin.easyuploads.MultiFileUpload;

/**
 *
 */
@Theme("mytheme")
@Widgetset("tad.proyecto.pelisinfo.MyAppWidgetset")
public class Prueba extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);
        
        MultiFileUpload fileUpload = new MultiFileUpload() {
            @Override
            protected void handleFile(File file, String fileName, String mimeType, long length) {
                String msg = fileName + " uploaded. Saved to file " + file.getAbsolutePath() + " (size " + length + " bytes)";
                Path FROM = Paths.get(file.getAbsolutePath());
                Path TO = Paths.get("C:\\Users\\Patricia\\Desktop\\"+fileName);
                CopyOption[] options = new CopyOption[]{
                  StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES
                }; 
                try {
                    Files.copy(FROM, TO, options);
                    
                } catch (IOException ex) {
                    Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(msg);
            }
        };
        fileUpload.setWidth("600px");
        layout.addComponent(fileUpload);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Prueba.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
