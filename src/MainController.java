import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @FXML
    Label doc;
    @FXML
    Label help;
    @FXML
    Label strukturname;
    @FXML
    Label molmasse;
    @FXML
    Label summenformel;

    public void readFiles() {
        String path = System.getProperty("user.dir");
        try {
            List<String> lines = Files.readAllLines(Paths.get(path, "src/ressources/pages/documentation.txt"));
            String text = "";
            for (String l : lines) {
                text += l + "\n";
            }
            doc.setText(text);

            lines = Files.readAllLines(Paths.get(path, "src/ressources/pages/help.txt"));
            text = "";
            for (String l : lines) {
                text += l + "\n";
            }
            help.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            ExportController ec = loader.getController();
            ec.setInitialFileNameAndCanvas("Test", canvas);
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
        popupWindow.getIcons().add(new Image(getClass().getResource("ressources/images/taskbar-icon.png").toExternalForm()));

        return popupWindow;
    }

    public void enter(javafx.scene.input.KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
            drawCanvas();
    }
    
    public void drawCanvas() {
        canvasplaceholder.setVisible(false);
        canvasplaceholder.setDisable(true);
        canvas.setVisible(true);
        canvas.setDisable(false);
        summenformel.setVisible(true);
        strukturname.setVisible(true);
        molmasse.setVisible(true);

        boolean sizeunfit = true;
        int canvaslen = (int)canvas.getWidth(), canvaswid = (int)canvas.getHeight(), fontsize = 150, row = 1, col = 1;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Grid grid = new Grid(canvaslen, canvaswid, fontsize);

        do {
            try {
                grid = new Grid(canvaslen, canvaswid, fontsize);
                gc.setFont(Font.font("Arial", fontsize));
                //grid.drawGrid(gc);

                String[] norm = {"", "H" ,"-", "H"};
                String[][] arr = {{"H", "-", "-", "H"}, norm, norm, norm, {"", "H", "H", "-"}}, sc1 =  {{"H", "H", "H", ""}}, sc2 = {{"H", "-", "H", "H"}, {"H", "", "H", ""}};
                ArrayList<ArrayList<String>> sc3 = new ArrayList<>();
                sc3.add(new ArrayList<>());
                sc3.add(new ArrayList<>());
                sc3.get(0).addAll(Arrays.asList(sc2[0]));
                sc3.get(1).addAll(Arrays.asList(sc2[1]));

                CanvasFkt.drawChainHorWithSideChains(gc, grid, col, row, arr, new SideChainInput(Orientation.Right, 2, sc1), new SideChainInput(Orientation.Left, 5, sc2));
                CanvasFkt.drawE(gc, grid, 8, 6, "O", "H", "HO", "H", "HO");
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

        slider.valueProperty().addListener(event -> {
            canvas.setRotate(slider.getValue());
        });
    }
}
