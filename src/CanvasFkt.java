import javafx.scene.canvas.GraphicsContext;

public class CanvasFkt {
    public static void drawC(GraphicsContext gc, Grid grid, int col, int row, String o, String l, String u, String r) throws Exception{
        gc.fillText("C", grid.getX(col)+3, grid.getY(row)-2);
        /*gc.fillText("-", grid.getX(col+1), grid.getY(row));
        gc.fillText("-", grid.getX(col-1), grid.getY(row));
        gc.fillText("|", grid.getX(col), grid.getY(row+1));
        gc.fillText("|", grid.getX(col), grid.getY(row-1));*/
        if(l.equals(""))
            gc.strokeLine(grid.getX(col+1), grid.getY(row)-(grid.size/2), grid.getX(col+2), grid.getY(row)-(grid.size/2));
    }
}
