import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

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

                String[] norm = {"", "H" ,"-", "H"};
                String[][] arr = {{"H", "-", "-", "H"}, norm, norm, norm, {"", "H", "H", "-"}}, sc1 =  {{"H", "H", "H", ""}}, sc2 = {{"H", "-", "H", "H"}, {"H", "", "H", ""}};

                CanvasFkt.drawChainVertWithSideChains(gc, grid, 1, 6, arr, new SideChainInput(Orientation.Up, 1, sc1), new SideChainInput(Orientation.Down, 5, sc2));

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
