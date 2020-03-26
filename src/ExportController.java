import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class ExportController extends Controller {
    private Canvas canvas;
    private String initialFileName;

    public void setInitialFileNameAndCanvas(String name, Canvas canvas) {
        initialFileName = name;
        this.canvas = canvas;
    }

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
        File out = fileChooser.showSaveDialog(window);

        if(out != null) {
            try {
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, out.toString().substring(out.toString().length() - 3), out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
