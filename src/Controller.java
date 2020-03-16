import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Controller {
    @FXML
    Canvas kekvas;
    @FXML
    Slider slider;

    public void test(){
        boolean sizeunfit = true;
        int canvaslen = (int)kekvas.getWidth(), canvaswid = (int)kekvas.getHeight(), fontsize = 50;
        GraphicsContext gc = kekvas.getGraphicsContext2D();

        do {
            try {
                Grid grid = new Grid(canvaslen, canvaswid, fontsize);

                gc.setFont(Font.font("Arial", fontsize));

                grid.drawGrid(gc);

                String[] norm = {"H", "-" ,"H", ""};
                String[][] arr = {{"H", "-", "H", "H"}, norm, norm, norm, {"H", "H", "H", ""}};

                CanvasFkt.drawChainVert(gc, grid, 1, 1, arr);
                CanvasFkt.drawC(gc, grid, 3, 4, norm);

                sizeunfit = false;
            } catch (Exception e) {
                fontsize -= 1;
                gc.clearRect(0, 0, canvaslen, canvaswid);
                if(fontsize < 1){
                    sizeunfit = false;
                    System.out.println("Illegally Place C[hain]");
                }
            }
        } while(sizeunfit);

        slider.valueProperty().addListener(event -> {
            kekvas.setRotate(slider.getValue());
        });
    }
}
