import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainController extends Controller {
    @FXML
    AnchorPane anchorpane;
    @FXML
    MenuBar menubar;
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
    Slider slider;
    @FXML
    Button btn_close;
    @FXML
    Button btn_minimize;
    @FXML
    ImageView canvasplaceholder;

    public void minimize() {
        stage.setIconified(true);
    }

    public void reset() {
        input.setText("");
        canvas.getGraphicsContext2D().restore();
    }

    public void showHelp() {
        helppane.setVisible(true);
        helppane.setManaged(true);
        docpane.setVisible(false);
        docpane.setManaged(false);
    }

    public void showDoc() {
        docpane.setVisible(true);
        docpane.setManaged(true);
        helppane.setVisible(false);
        helppane.setManaged(false);
    }

    public void hideHelp() {
        helppane.setVisible(false);
        helppane.setManaged(false);
    }

    public void hideDoc() {
        docpane.setVisible(false);
        docpane.setManaged(false);
    }

    public void openExportWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/export.fxml"));
            Stage window = createStage(loader);
            window.setTitle("Exportieren");
            window.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Stage createStage(FXMLLoader loader) throws java.io.IOException {
        Stage popupWindow = new Stage();
        Parent root = loader.load();

        root.getStylesheets().add(getClass().getResource("ressources/styles/style.css").toExternalForm());
        Controller controller = loader.getController();
        controller.setStage(popupWindow);

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setResizable(false);
        popupWindow.initStyle(StageStyle.UNDECORATED);
        popupWindow.setScene(new Scene(root));
        popupWindow.getIcons().add(new Image(getClass().getResource("images/Strukturformel-Zeichner_Icon_2.png").toExternalForm()));

        return popupWindow;
    }
    
    public void drawCanvas() {
        canvasplaceholder.setVisible(false);
        canvasplaceholder.setDisable(true);


        boolean sizeunfit = true;
        int canvaslen = (int)canvas.getWidth(), canvaswid = (int)canvas.getHeight(), fontsize = 150, row = 1, col = 1;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Grid grid = new Grid(canvaslen, canvaswid, fontsize);

        do {
            try {

                //CanvasFkt.drawChainVert(gc, grid, col ,row, "H,-,H,H", "H,H,H,");

                sizeunfit = false;
            } catch (ColIndexException e) {
                if(e.getCol() < 0){
                    col++;
                }
                else if(e.getCol() >= grid.getMaxCol()) {
                    fontsize--;
                }
                gc.clearRect(0, 0, canvaslen, canvaswid);
            } catch (RowIndexException e){
                if(e.getRow() < 0){
                    row++;
                }
                else if(e.getRow() >= grid.getMaxRow()) {
                    fontsize--;
                }
                gc.clearRect(0, 0, canvaslen, canvaswid);
            }
        } while(sizeunfit);

        slider.valueProperty().addListener(event -> canvas.setRotate(slider.getValue()));
    }
}
