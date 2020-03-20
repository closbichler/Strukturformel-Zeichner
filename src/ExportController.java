import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportController extends Controller {
    private String initialFileName;

    public void setInitialFileName(String name) { initialFileName = name; }

    public void export() {
        Stage window = new Stage();
        Group group =  new Group();
        Scene scene = new Scene(group);
        window.setScene(scene);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportieren");
        fileChooser.setInitialFileName(initialFileName);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG files (*.svg)", "*.svg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif"));

        stage.close();
        fileChooser.showSaveDialog(window);
    }
}
