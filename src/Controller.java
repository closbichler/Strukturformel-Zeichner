import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class Controller {
    @FXML
    Canvas kekvas;

    public void test() throws Exception{
        int canvaslen = 300, canvaswid = 150, fontsize = 20;

        Grid grid = new Grid(canvaslen, canvaswid, fontsize);

        GraphicsContext gc = kekvas.getGraphicsContext2D();
        gc.setFont(Font.font("Arial", fontsize));

        /*for(int i = 0; i<grid.getMaxCol(); i++)
            gc.strokeLine(grid.getX(i), grid.getY(0), grid.getX(i), grid.getMaxY());

        for(int i = 0; i<grid.getMaxRow(); i++)
            gc.strokeLine(grid.getX(0), grid.getY(i), grid.getMaxX(), grid.getY(i));*/

        CanvasFkt.drawC(gc, grid, 1, 1, "---", "---", "---", "---");
        CanvasFkt.drawC(gc, grid, 3, 3, "--", "--", "--", "--");
        CanvasFkt.drawC(gc, grid, 3, 1, "", "", "", "");
        CanvasFkt.drawC(gc, grid, 1, 3, "", "", "", "");
    }
}
