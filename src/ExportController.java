import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportController extends Controller {
    private String mol, sum;
    private Canvas canvas;
    private String initialFileName;

    @FXML
    CheckBox cbt, cbs;

    public void setInitialFileNameAndCanvas(String name, Canvas canvas) {
        initialFileName = name;
        this.canvas = canvas;
        mol = "69";
        sum = "C5H4";
    }

    public void export() {
        Stage window = new Stage();
        Group group =  new Group();
        Scene scene = new Scene(group);
        window.setScene(scene);
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportieren");
        fileChooser.setInitialFileName(initialFileName);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif"));
        if(!cbt.isSelected())
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg"));

        if(cbs.isSelected()){
            Canvas canvas = new Canvas(this.canvas.getWidth(), this.canvas.getHeight()+50);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.fillText("Molmasse: " + mol, 10, 20);
            gc.fillText("Summenformel: " + sum, 10, 40);
            if(cbt.isSelected())
                gc.drawImage(this.canvas.snapshot(sp, null), 0, 45);
            else
                gc.drawImage(this.canvas.snapshot(null, null), 0, 45);
            this.canvas = canvas;
        }

        stage.close();
        File out = fileChooser.showSaveDialog(window);
        if(out == null)
            return;

        String extension = out.toString().substring(out.toString().length() - 3);

        if(cbt.isSelected()){
            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(sp, writableImage), null), extension, out);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else if(extension.toLowerCase().equals("gif") || extension.toLowerCase().equals("png") || extension.toLowerCase().equals("jpg")) {
            try {
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                canvas.snapshot(null, writableImage);
                BufferedImage bufferedImage = new BufferedImage((int)canvas.getWidth(), (int)canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
                SwingFXUtils.fromFXImage(writableImage, bufferedImage);
                ImageIO.write(bufferedImage, extension, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
