import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
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
    Label molmasse;
    @FXML
    Label summenformel;
    @FXML
    Menu history;

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

        // Contextmenu in Canvas
        ContextMenu c = new ContextMenu();
        MenuItem m1 = new MenuItem("In Zwischenablage kopieren");
        m1.setOnAction(e -> copyToClipboard(e));
        c.getItems().add(m1);
        canvas.setOnContextMenuRequested(e -> {c.show(stage, e.getScreenX(), e.getScreenY());});
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
  
    public void addToHistory(String struktur) {
        for(MenuItem m : history.getItems()) {
            if(m.getText().equals(struktur))
                return;
        }
        MenuItem menuItem = new MenuItem(struktur);
        menuItem.setOnAction(e -> {
            input.setText(menuItem.getText());
            drawCanvas();
        });
        if(history.getItems().size() > 10) {
            history.getItems().remove(0);
        }
        history.getItems().add(menuItem);
    }

    public void enter(javafx.scene.input.KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
            drawCanvas();
    }
  
    public void calcMainChain(ArrayList<ArrayList<String>> mainChainString, Model model) {
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
                    integer++;
                    System.out.println(bonds.get(0) + bonds.get(1) + bonds.get(2) + bonds.get(3) + integer + " " + i);
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
    }

    public void calcSideChains(ArrayList<ArrayList<String>> sideChainString, Orientation orientation, SideChain sideChain) {
        int up, down, left, right;
        if (orientation == Orientation.Down) {
            up = 3;
            down = 1;
            left = 2;
            right = 0;
        } else {
            up = 1;
            down = 3;
            left = 0;
            right = 2;
        }
        for (int i = 1; i <= sideChain.mainChain.hydroCarbon.getValue(); i++) {
            ArrayList<String> bonds = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                bonds.add(null);
            }

            if (sideChain.mainChain.double_bonds.contains(i)) {
                bonds.remove(right);
                bonds.add(right, "--");
            } else if (sideChain.mainChain.triple_bonds.contains(i)) {
                bonds.remove(right);
                bonds.add(right, "---");

            } else {
                if (i != sideChain.mainChain.hydroCarbon.getValue()) {
                    bonds.remove(right);
                    bonds.add(right, "-");
                }
            }
            int integer = sideChain.mainChain.bonds_per_carbon.get(i - 1);
            while (integer != 4) {
                if (i == 1 && bonds.get(left) == null) {
                    bonds.remove(left);
                    bonds.add(left, "H");
                    integer++;
                } else if (i == sideChain.mainChain.hydroCarbon.getValue() && bonds.get(right) == null) {
                    bonds.remove(right);
                    bonds.add(right, "");
                    integer++;
                } else if (bonds.get(up) == null) {
                    bonds.remove(up);
                    bonds.add(up, "H");
                    integer++;
                } else if (bonds.get(down) == null) {
                    bonds.remove(down);
                    bonds.add(down, "H");
                    integer++;

                } else {
                    System.out.println(bonds.get(0) + bonds.get(1) + bonds.get(2) + bonds.get(3) + integer + " " + i);
                }

            }

            for (int j = 0; j < 4; j++) {
                if (bonds.get(j) == null) {
                    bonds.remove(j);
                    bonds.add(j, "");
                }

            }

            sideChainString.add(bonds);

        }
        if (orientation == Orientation.Down) {
            for (int i = 0; i < sideChainString.size() / 2; i++) {
                ArrayList<String> tmp1 = sideChainString.remove(i);
                ArrayList<String> tmp2 = sideChainString.remove(sideChainString.size() - 1 - i);
                sideChainString.add(i, tmp2);
                sideChainString.add(sideChainString.size() - i, tmp1);
            }
        }
    }

    public void drawCanvas() {
        if(input.getText().isBlank())
            return;

        addToHistory(input.getText());

        canvasplaceholder.setVisible(false);
        canvasplaceholder.setDisable(true);
        canvas.setVisible(true);
        canvas.setDisable(false);
      
        Model model = new Model();
        model.calculate(input.getText());

        if (!ErrorMessages.anyErrorsThrown()) {
            errormsg.setText("");
            boolean sizeunfit = true;
            int canvaslen = (int) canvas.getWidth(), canvaswid = (int) canvas.getHeight(), fontsize = 150, row = 1, col = 1;
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Grid grid = new Grid(canvaslen, canvaswid, fontsize);

            ArrayList<ArrayList<String>> mainChainString = new ArrayList<>();
            calcMainChain(mainChainString, model);

            int size = 0;
            for (SideChain sideChain : model.sideChains) {
                size += sideChain.positions.size();
            }
            SideChainInput[] sideChainInputs = new SideChainInput[size];
            int i = 0;
            for (SideChain sideChain : model.sideChains) {

                for (Integer position : sideChain.positions) {
                    ArrayList<ArrayList<String>> sideChainString = new ArrayList<>();

                    boolean orientation = true;
                    for (SideChainInput sideChainInput : sideChainInputs) {
                        if (sideChainInput != null && sideChainInput.equals(position)) {
                            calcSideChains(sideChainString, Orientation.Down, sideChain);
                            SideChainInput down = new SideChainInput(Orientation.Down, position, sideChainString);
                            sideChainInputs[i] = down;
                            i++;
                            orientation = false;
                            break;
                        }
                    }
                    if (orientation) {
                        calcSideChains(sideChainString, Orientation.Up, sideChain);
                        SideChainInput up = new SideChainInput(Orientation.Up, position, sideChainString);
                        sideChainInputs[i] = up;
                        i++;
                    }
                }

            }

                /*for (SideChainInput sideChainInput : sideChainInputs) {
                    System.out.println(sideChainInput);
                }*/

            gc.clearRect(0, 0, canvaslen, canvaswid);
            do {

                try {
                    grid = new Grid(canvaslen, canvaswid, fontsize);
                    gc.setFont(Font.font("Arial", fontsize));
                    //grid.drawGrid(gc);

                    //Änderungen in diesem if-Zweig müssen auch im try-cath-Block unterhalb dieser do-while-Schleife vorgenommen werden (aktuell Zeile 348)
                    if (model.sideChains == null) {
                        CanvasFkt.drawChainVert(gc, grid, col, row, false, mainChainString);
                    } else {

                        CanvasFkt.drawChainVertWithSideChains(gc, grid, col, row, mainChainString, sideChainInputs);
                    }

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

            try {
                col += (grid.getMaxCol() - model.mainChain.hydroCarbon.getValue() * 2) / 2;
                gc.clearRect(0, 0, canvaslen, canvaswid);
                //Hier Änderungen vom if-Block aus (derzeit) Zeile 320 einfügen
                if (model.sideChains == null) {
                    CanvasFkt.drawChainVert(gc, grid, col, row, false, mainChainString);
                } else {
                    CanvasFkt.drawChainVertWithSideChains(gc, grid, col, row, mainChainString, sideChainInputs);
                }

            } catch (Exception e) {
                System.out.println("Fehler beim Zentrieren!");
            }
        } else {
            errormsg.setText(ErrorMessages.getFirst3Messages());
            ErrorMessages.clear();
        }
    }

    public void copyToClipboard(ActionEvent actionEvent) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, writableImage);
        content.putImage(writableImage);
        clipboard.setContent(content);
    }
}
