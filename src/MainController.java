import javafx.event.EventHandler;
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
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    String struktur = "";

    public void readFiles() {
        String path = System.getProperty("user.dir");

        try {
            List<String> lines = Files.readAllLines(Paths.get("src/ressources/pages/documentation.txt"));
            String text = "";
            for (String l : lines) {
                text += l + "\n";
            }
            doc.setText(text);

            lines = Files.readAllLines(Paths.get("src/ressources/pages/help.txt"));
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
        m1.setOnAction(e -> copyToClipboard());
        c.getItems().add(m1);
        canvas.setOnContextMenuRequested(e -> {
            c.show(stage, e.getScreenX(), e.getScreenY());
        });

        // KeyCombinations
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCodeCombination saveComb = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            final KeyCodeCombination copyComb = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);

            @Override
            public void handle(KeyEvent keyEvent) {
                if (saveComb.match(keyEvent)) {
                    openExportWindow();
                } else if (copyComb.match(keyEvent)) {
                    copyToClipboard();
                } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            }
        });
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
        if (struktur.length() < 3)
            return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/export.fxml"));
            Stage window = createStage(loader);
            ExportController ec = loader.getController();
            ec.setInitialFileNameAndCanvas(struktur, canvas, struktur, molmasse.getText(), summenformel.getText());
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
        for (MenuItem m : history.getItems()) {
            if (m.getText().equals(struktur))
                return;
        }
        MenuItem menuItem = new MenuItem(struktur);
        menuItem.setOnAction(e -> {
            input.setText(menuItem.getText());
            drawCanvas();
        });
        if (history.getItems().size() > 10) {
            history.getItems().remove(0);
        }
        history.getItems().add(menuItem);
    }

    public void enter(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            drawCanvas();
    }

    public String getMolmasse(int c, int h, int oh) {
        double m = c * 12.01 + h * 1.01 + oh * (1.01 + 16);

        return String.format("%.2f", m) + " g/mol";
    }

    public String getSummenformel(int c, int h, int oh) {
        String sum = "C";

        if (c > 1)
            sum += c;
        if (h > 0)
            sum += "H" + h;
        if (oh == 1)
            sum += "OH";
        if (oh > 1)
            sum += "(OH)" + oh;

        return sum;
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
            boolean alcohol = false;
            int integer = model.mainChain.bonds_per_carbon.get(i - 1);
            while (integer < 4) {

                if (!alcohol) {
                    for (Integer alcohol_position : model.mainChain.alcohol_positions) {
                        if (alcohol_position == i) {
                            if (i == 1 && bonds.get(3) == null) {
                                bonds.remove(3);
                                bonds.add(3, "OH");
                                integer++;
                                alcohol = true;
                            } else if (i == model.mainChain.hydroCarbon.getValue() && bonds.get(1) == null) {
                                bonds.remove(1);
                                bonds.add(1, "OH");
                                integer++;
                                alcohol = true;
                            } else if (bonds.get(0) == null) {

                                bonds.remove(0);
                                bonds.add(0, "OH");
                                integer++;
                                alcohol = true;
                            } else if (bonds.get(2) == null) {
                                bonds.remove(2);
                                bonds.add(2, "OH");
                                integer++;
                                alcohol = true;
                            }
                        }
                    }
                    if (alcohol) {
                        continue;
                    }
                }

                if (bonds.get(0) == null) {
                    bonds.remove(0);
                    bonds.add(0, "H");
                    model.mainChain.h_atoms++;
                    integer++;
                } else if (bonds.get(2) == null) {
                    bonds.remove(2);
                    bonds.add(2, "H");
                    model.mainChain.h_atoms++;
                    integer++;

                } else if (i == 1 && bonds.get(3) == null) {
                    bonds.remove(3);
                    bonds.add(3, "H");
                    model.mainChain.h_atoms++;
                    integer++;
                } else if (i == model.mainChain.hydroCarbon.getValue() && bonds.get(1) == null) {
                    bonds.remove(1);
                    bonds.add(1, "H");
                    model.mainChain.h_atoms++;
                    integer++;
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
                    sideChain.mainChain.h_atoms++;
                    integer++;
                } else if (i == sideChain.mainChain.hydroCarbon.getValue() && bonds.get(right) == null) {
                    bonds.remove(right);
                    bonds.add(right, "");
                    integer++;
                } else if (bonds.get(up) == null) {
                    bonds.remove(up);
                    bonds.add(up, "H");
                    sideChain.mainChain.h_atoms++;
                    integer++;
                } else if (bonds.get(down) == null) {
                    bonds.remove(down);
                    bonds.add(down, "H");
                    sideChain.mainChain.h_atoms++;
                    integer++;

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
        if (input.getText().isEmpty())
            return;

        addToHistory(input.getText());

        canvasplaceholder.setVisible(false);
        canvasplaceholder.setDisable(true);
        canvas.setVisible(true);
        canvas.setDisable(false);
        summenformel.setVisible(true);

        molmasse.setVisible(true);

        Model model = new Model();
        model.calculate(input.getText());

        if (!ErrorMessages.anyErrorsThrown()) {
            errormsg.setText("");

            boolean sizeunfit = true;
            int canvaslen = (int) canvas.getWidth(), canvaswid = (int) canvas.getHeight(), fontsize = 140, row = 1, col = 1;
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
                        if (sideChainInput != null && sideChainInput.pos == position) {
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
            int h_atoms = 0;
            int c_atoms = 0;
            int oh_atoms = 0;

            for (SideChain sideChain : model.sideChains) {
                h_atoms += sideChain.mainChain.h_atoms;
                c_atoms += sideChain.mainChain.hydroCarbon.getValue() * sideChain.positions.size();
                oh_atoms += sideChain.mainChain.alcohol_positions.size();
            }
            h_atoms += model.mainChain.h_atoms;
            c_atoms += model.mainChain.hydroCarbon.getValue();
            oh_atoms += model.mainChain.alcohol_positions.size();


            struktur = input.getText();
            summenformel.setText(getSummenformel(c_atoms, h_atoms, oh_atoms));
            molmasse.setText(getMolmasse(c_atoms, h_atoms, oh_atoms));

            gc.clearRect(0, 0, canvaslen, canvaswid);
            do {

                try {
                    if (fontsize == 0) {
                        ErrorMessages.throwInternalError();
                        printErrorMessages();
                        return;
                    }
                    grid = new Grid(canvaslen, canvaswid, fontsize);
                    gc.setFont(Font.font("Arial", fontsize));
                    //grid.drawGrid(gc);

                    //Änderungen in diesem if-Zweig müssen auch im try-cath-Block unterhalb dieser do-while-Schleife vorgenommen werden (aktuell Zeile 479)
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
                int up = 0, down = 0;
                ArrayList<Integer> positions = new ArrayList<>();
                for (SideChain s : model.sideChains) {
                    for (Integer j : s.positions) {
                        if (positions.contains(j)) {
                            if (s.mainChain.hydroCarbon.getValue() * 2 > down) {
                                down = s.mainChain.hydroCarbon.getValue() * 2;
                            }
                        } else if (s.mainChain.hydroCarbon.getValue() * 2 > up) {
                            up = s.mainChain.hydroCarbon.getValue() * 2;
                        }
                        positions.add(j);
                    }
                }

                col += (grid.getMaxCol() - model.mainChain.hydroCarbon.getValue() * 2) / 2;
                row = (grid.getMaxRow() - (up + down + 3)) / 2 + up + 1;
                if (((grid.getMaxRow() - (up + down + 3)) % 2) == 1)
                    row++;

                gc.clearRect(0, 0, canvaslen, canvaswid);
                //Hier Änderungen vom if-Block aus (derzeit) Zeile 430 einfügen
                if (model.sideChains == null) {
                    CanvasFkt.drawChainVert(gc, grid, col, row, false, mainChainString);
                } else {
                    CanvasFkt.drawChainVertWithSideChains(gc, grid, col, row, mainChainString, sideChainInputs);
                }

            } catch (Exception e) {
                e.printStackTrace();
                ErrorMessages.throwInternalError();
            }
        } else {
            printErrorMessages();
        }
    }

    public void printErrorMessages() {
        errormsg.setText(ErrorMessages.getFirst3Messages());
        ErrorMessages.clear();
    }

    public void copyToClipboard() {
        if (struktur.isEmpty())
            return;
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);
        content.putImage(writableImage);
        clipboard.setContent(content);
    }
}
