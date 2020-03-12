import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class Controller {
    @FXML
    Canvas kekvas;

    public void test() throws Exception{
        boolean sizeunfit = true;
        int canvaslen = (int)kekvas.getWidth(), canvaswid = (int)kekvas.getHeight(), fontsize = 150;
        GraphicsContext gc = kekvas.getGraphicsContext2D();

        do {
            try {
                Grid grid = new Grid(canvaslen, canvaswid, fontsize);

                gc.setFont(Font.font("Arial", fontsize));

                for (int i = 0; i < grid.getMaxCol(); i++)
                    gc.strokeLine(grid.getX(i), grid.getY(0), grid.getX(i), grid.getMaxY());

                for (int i = 0; i < grid.getMaxRow(); i++)
                    gc.strokeLine(grid.getX(0), grid.getY(i), grid.getMaxX(), grid.getY(i));

                //CanvasFkt.drawC(gc, grid, 1, 1, "H", "H", "H", "H");

                CanvasFkt.drawChainVert(gc, grid, 1, 1, ",--,,H", "H,-,,", ",---,,", ",H,,");

                CanvasFkt.drawChainHor(gc, grid, 1, 3, "-,H,-,H", ",OH,-,OH", ",,---,", ",,H,");

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

        System.out.println(fontsize);
    }
}
