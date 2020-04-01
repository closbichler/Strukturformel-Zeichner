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


        canvas.setVisible(true);
        canvas.setDisable(false);
        Model model = new Model();
        model.calculate(input.getText());

        if (model.errors.equals("")) {

            boolean sizeunfit = true;
            int canvaslen = (int) canvas.getWidth(), canvaswid = (int) canvas.getHeight(), fontsize = 150, row = 1, col = 1;
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Grid grid = new Grid(canvaslen, canvaswid, fontsize);

            ArrayList<ArrayList<String>> mainChainString = new ArrayList<>();
            for (int i = 1; i <= model.mainChain.hydroCarbon.getValue(); i++) {
                ArrayList<String> bonds = new ArrayList<>();
                for (int j = 0; j < 4; j++) {
                    bonds.add(null);
                }
                for (SideChain sideChain : model.sideChains) {
                    for (Integer position : sideChain.positions) {
                        if (position == i) {
                            if (bonds.get(0) == null) {
                                bonds.remove(0);
                                bonds.add(0, "-");
                            } else {
                                bonds.remove(2);
                                bonds.add(2, "-");
                            }
                        }
                    }

                }
                if (model.mainChain.double_bonds.contains(i)) {
                    bonds.remove(1);
                    bonds.add(1, "--");
                } else if (model.mainChain.triple_bonds.contains(i)) {
                    bonds.remove(1);
                    bonds.add(1, "---");

                } else {
                    if (i != model.mainChain.hydroCarbon.getValue()) {
                        bonds.remove(1);
                        bonds.add(1, "-");
                    }
                }
                int integer = model.mainChain.bonds_per_carbon.get(i - 1);
                while (integer != 4) {

                    if (bonds.get(0) == null) {
                        bonds.remove(0);
                        bonds.add(0, "H");
                        integer++;
                    } else if (bonds.get(2) == null) {
                        bonds.remove(2);
                        bonds.add(2, "H");
                        integer++;

                    } else if (i == 1 && bonds.get(3) == null) {
                        bonds.remove(3);
                        bonds.add(3, "H");
                        integer++;
                    } else if (i == model.mainChain.hydroCarbon.getValue() && bonds.get(1) == null) {
                        bonds.remove(1);
                        bonds.add(1, "H");
                        integer++;
                    } else {
                        //System.out.println(bonds.get(0) + bonds.get(1) + bonds.get(2) + bonds.get(3) + integer + " " + i);
                    }

                }



                for (int j = 0; j < 4; j++) {
                    if (bonds.get(j) == null) {
                        bonds.remove(j);
                        bonds.add(j, "");
                    }

                }

                mainChainString.add(bonds);

            }
            gc.clearRect(0, 0, canvaslen, canvaswid);
            do {
                try {
                    grid = new Grid(canvaslen, canvaswid, fontsize);
                    gc.setFont(Font.font("Arial", fontsize));
                    //grid.drawGrid(gc);
                    CanvasFkt.drawChainVert(gc, grid, col, row, mainChainString);

                    //CanvasFkt.drawChainVert(gc, grid, col ,row, "H,-,H,H", "H,H,H,");

                    sizeunfit = false;
                } catch (ColIndexException e) {
                    if (e.getCol() < 0) {
                        col++;
                    } else if (e.getCol() >= grid.getMaxCol()) {
                        fontsize--;
                    }
                    gc.clearRect(0, 0, canvaslen, canvaswid);
                } catch (RowIndexException e) {
                    if (e.getRow() < 0) {
                        row++;
                    } else if (e.getRow() >= grid.getMaxRow()) {
                        fontsize--;
                    }
                    gc.clearRect(0, 0, canvaslen, canvaswid);
                }
            } while (sizeunfit);

            slider.valueProperty().addListener(event -> canvas.setRotate(slider.getValue()));
        } else {
            System.out.println(model.errors);
            errormsg.setText(model.errors);
        }
    }
}
