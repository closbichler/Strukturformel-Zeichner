import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportController extends Controller {
    private int txtlinks;
    private String mol, sum, struktur;
    private Canvas canvas;
    private String initialFileName;

    @FXML
    CheckBox molmasse, summenformel, transparenz;
    @FXML
    RadioButton radio1, radio2, radio3;

    public void setInitialFileNameAndCanvas(String name, Canvas canvas, String strukturname, String molmasse, String summenformel) {
        initialFileName = name;
        this.canvas = canvas;
        mol = "Molmasse: " + molmasse;
        sum = "Summenformel: " + summenformel;
        struktur = strukturname;
        txtlinks = Math.min((400-(int)(struktur.length()*4.3)), Math.min((400-(int)(sum.length()*2.9)), (400-(int)(mol.length()*2.9))));
    }

    public void hideDescription() {
        molmasse.setDisable(true);
        summenformel.setDisable(true);
    }

    public void showDescription() {
        molmasse.setDisable(false);
        summenformel.setDisable(false);
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
        if(!transparenz.isSelected()) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg"));
        }

        if(summenformel.isSelected() && molmasse.isSelected() && !radio1.isSelected() && radio2.isSelected()){
            Canvas canvas = new Canvas(this.canvas.getWidth(), this.canvas.getHeight()+50);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.fillText(mol, txtlinks, 20);
            gc.fillText(sum, txtlinks, 40);
            if(transparenz.isSelected())
                gc.drawImage(this.canvas.snapshot(sp, null), 0, 45);
            else
                gc.drawImage(this.canvas.snapshot(null, null), 0, 45);
            this.canvas = canvas;
        }
        else if((summenformel.isSelected() || molmasse.isSelected()) && !radio1.isSelected() && radio2.isSelected()){
            Canvas canvas = new Canvas(this.canvas.getWidth(), this.canvas.getHeight()+25);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            if(molmasse.isSelected())
                gc.fillText(mol, txtlinks, 20);
            else
                gc.fillText(sum, txtlinks, 20);
            if(transparenz.isSelected())
                gc.drawImage(this.canvas.snapshot(sp, null), 0, 25);
            else
                gc.drawImage(this.canvas.snapshot(null, null), 0, 25);
            this.canvas = canvas;
        }

        if(radio2.isSelected()){
            Canvas canvas = new Canvas(this.canvas.getWidth(), this.canvas.getHeight()+25);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Font prev = gc.getFont();
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            gc.fillText(struktur, txtlinks, 20);
            gc.setFont(prev);
            if(transparenz.isSelected())
                gc.drawImage(this.canvas.snapshot(sp, null), 0, 25);
            else
                gc.drawImage(this.canvas.snapshot(null, null), 0, 25);
            this.canvas = canvas;
        }
        else if(radio3.isSelected()){
            Canvas canvas = new Canvas(this.canvas.getWidth(), this.canvas.getHeight()+25);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Font prev = gc.getFont();
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            gc.fillText(struktur, txtlinks, this.canvas.getHeight()+20);
            gc.setFont(prev);
            if(transparenz.isSelected())
                gc.drawImage(this.canvas.snapshot(sp, null), 0, 0);
            else
                gc.drawImage(this.canvas.snapshot(null, null), 0, 0);
            this.canvas = canvas;
        }

        if(summenformel.isSelected() && molmasse.isSelected() && !radio1.isSelected() && radio3.isSelected()){
            Canvas canvas = new Canvas(this.canvas.getWidth(), this.canvas.getHeight()+50);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.fillText(mol, txtlinks, this.canvas.getHeight()+20);
            gc.fillText(sum, txtlinks, this.canvas.getHeight()+40);
            if(transparenz.isSelected())
                gc.drawImage(this.canvas.snapshot(sp, null), 0, 0);
            else
                gc.drawImage(this.canvas.snapshot(null, null), 0, 0);
            this.canvas = canvas;
        }
        else if((summenformel.isSelected() || molmasse.isSelected()) && !radio1.isSelected() && radio3.isSelected()){
            Canvas canvas = new Canvas(this.canvas.getWidth(), this.canvas.getHeight()+25);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            if(molmasse.isSelected())
                gc.fillText(mol, txtlinks, this.canvas.getHeight()+20);
            else
                gc.fillText(sum, txtlinks, this.canvas.getHeight()+20);
            if(transparenz.isSelected())
                gc.drawImage(this.canvas.snapshot(sp, null), 0, 0);
            else
                gc.drawImage(this.canvas.snapshot(null, null), 0, 0);
            this.canvas = canvas;
        }

        stage.close();
        File out = fileChooser.showSaveDialog(window);
        if(out == null)
            return;

        String extension = out.toString().substring(out.toString().length() - 3);

        if(transparenz.isSelected()){
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
