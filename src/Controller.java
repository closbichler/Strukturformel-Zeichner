
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    @FXML
    Canvas kekvas;
    @FXML
    Slider slider;

    public void test(){
        boolean sizeunfit = true;
        int canvaslen = (int)kekvas.getWidth(), canvaswid = (int)kekvas.getHeight(), fontsize = 150, row = 1, col = 1;
        GraphicsContext gc = kekvas.getGraphicsContext2D();
        Grid grid = new Grid(canvaslen, canvaswid, fontsize);

        do {
            try {
                grid = new Grid(canvaslen, canvaswid, fontsize);
                gc.setFont(Font.font("Arial", fontsize));
                grid.drawGrid(gc);

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
            kekvas.setRotate(slider.getValue());
        });
    }
}
