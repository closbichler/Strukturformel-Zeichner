import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
    Stage stage;
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
    TextArea infobox;
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
        this.stage = stage;
    }

    public void close() {
        stage.close();
        System.exit(0);
    }

    public void minimize() {
        stage.setIconified(true);
    }

    public void startWindowDrag(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void dragWindow(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
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
}
