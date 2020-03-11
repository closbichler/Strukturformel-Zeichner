import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
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

    public void dragWindow() {
        System.out.println("drag");
    }

    public void reset() {
        input.setText("");
        canvas.getGraphicsContext2D().restore();
    }

    public void showHelp() {
        helppane.setVisible(true);
    }

    public void showDoc() {
        docpane.setVisible(true);
    }

    public void hideHelp() {
        helppane.setVisible(false);
    }

    public void hideDoc() {
        docpane.setVisible(false);
    }
}
