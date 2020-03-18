import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller {
    Stage primaryStage;
    @FXML
    AnchorPane anchorpane;
    @FXML
    AnchorPane helppane;
    @FXML
    AnchorPane docpane;
    @FXML
    TextField input;
    @FXML
    Button submit;
    @FXML
    Label errormsg;
    @FXML
    Canvas canvas;
    @FXML
    MenuBar menubar;
    @FXML
    Button btn_close;
    @FXML
    Button btn_minimize;

    private double xOffset = 0;
    private double yOffset = 0;

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void close() {
        primaryStage.close();
        System.exit(0);
    }

    public void minimize() {
        primaryStage.setIconified(true);
    }

    public void startWindowDrag(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void dragWindow(MouseEvent event) {
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
    }

    public void reset() {
        input.setText("");
        canvas.getGraphicsContext2D().restore();
    }

    public void showHelp() {
        helppane.setVisible(true);
        docpane.setVisible(false);
    }

    public void showDoc() {
        docpane.setVisible(true);
        helppane.setVisible(false);
    }

    public void hideHelp() {
        helppane.setVisible(false);
    }

    public void hideDoc() {
        docpane.setVisible(false);
    }

    public void export() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("export.fxml"));
            Parent root = loader.load();

            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
            popupWindow.setTitle("Exportieren");
            popupWindow.setResizable(false);
            popupWindow.initStyle(StageStyle.UNDECORATED);
            popupWindow.setScene(new Scene(root));

            popupWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
