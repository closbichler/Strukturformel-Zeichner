import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class CanvasFkt {
    public static void drawC(GraphicsContext gc, Grid grid, int col, int row, String u, String r, String d, String l) throws Exception{

        row++;
        gc.fillText("C", grid.getX(col)+grid.size/6, grid.getY(row)-grid.size/7);

        switch(u){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col)+grid.size/2, grid.getY(row-2), grid.getX(col)+grid.size/2, grid.getY(row-1));
                break;
            case "--":
                gc.strokeLine(grid.getX(col)+grid.size/3, grid.getY(row-2), grid.getX(col)+grid.size/3, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+2*grid.size/3, grid.getY(row-2), grid.getX(col)+2*grid.size/3, grid.getY(row-1));
                break;
            case "---":
                gc.strokeLine(grid.getX(col)+grid.size/4, grid.getY(row-2), grid.getX(col)+grid.size/4, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+2*grid.size/4, grid.getY(row-2), grid.getX(col)+2*grid.size/4, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+3*grid.size/4, grid.getY(row-2), grid.getX(col)+3*grid.size/4, grid.getY(row-1));
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                gc.strokeLine(grid.getX(col)+grid.size/2, grid.getY(row-2)+grid.size/2, grid.getX(col)+grid.size/2, grid.getY(row-1));
                if(u.length() == 1)
                    gc.fillText(u, grid.getX(col)+grid.size/3-grid.size/25, grid.getY(row-2)+grid.size/2-grid.size/12);
                if(u.length() == 2)
                    gc.fillText(u, grid.getX(col)+grid.size/6, grid.getY(row-2)+grid.size/2-grid.size/12);

                gc.setFont(prev);
                break;
        }

        switch(r){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-grid.size/2, grid.getX(col+2), grid.getY(row)-grid.size/2);
                break;
            case "--":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-grid.size/3, grid.getX(col+2), grid.getY(row)-grid.size/3);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-2*grid.size/3, grid.getX(col+2), grid.getY(row)-2*grid.size/3);
                break;
            case "---":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-grid.size/4, grid.getX(col+2), grid.getY(row)-grid.size/4);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-2*grid.size/4, grid.getX(col+2), grid.getY(row)-2*grid.size/4);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-3*grid.size/4, grid.getX(col+2), grid.getY(row)-3*grid.size/4);
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                if(r.length() == 1) {
                    gc.strokeLine(grid.getX(col + 1), grid.getY(row) - grid.size / 2, grid.getX(col + 1) + grid.size / 2, grid.getY(row) - grid.size / 2);
                    gc.fillText(r, grid.getX(col+1)+grid.size/2+grid.size/12, grid.getY(row)-grid.size/3);

                }
                if(r.length() == 2) {
                    gc.strokeLine(grid.getX(col + 1), grid.getY(row) - grid.size / 2, grid.getX(col + 1) + grid.size / 6, grid.getY(row) - grid.size / 2);
                    gc.fillText(r, grid.getX(col+1)+grid.size/4, grid.getY(row)-grid.size/3);
                }

                gc.setFont(prev);
                break;
        }

        switch(d){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col)+grid.size/2, grid.getY(row), grid.getX(col)+grid.size/2, grid.getY(row+1));
                break;
            case "--":
                gc.strokeLine(grid.getX(col)+grid.size/3, grid.getY(row), grid.getX(col)+grid.size/3, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+2*grid.size/3, grid.getY(row), grid.getX(col)+2*grid.size/3, grid.getY(row+1));
                break;
            case "---":
                gc.strokeLine(grid.getX(col)+grid.size/4, grid.getY(row), grid.getX(col)+grid.size/4, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+2*grid.size/4, grid.getY(row), grid.getX(col)+2*grid.size/4, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+3*grid.size/4, grid.getY(row), grid.getX(col)+3*grid.size/4, grid.getY(row+1));
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                gc.strokeLine(grid.getX(col)+grid.size/2, grid.getY(row)+grid.size/2, grid.getX(col)+grid.size/2, grid.getY(row));
                if(d.length() == 1)
                    gc.fillText(d, grid.getX(col)+grid.size/3-grid.size/25, grid.getY(row+1)-grid.size/12);
                if(d.length() == 2)
                    gc.fillText(d, grid.getX(col)+grid.size/6, grid.getY(row+1)-grid.size/12);

                gc.setFont(prev);
                break;
        }

        switch(l){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col), grid.getY(row)-grid.size/2, grid.getX(col-1), grid.getY(row)-grid.size/2);
                break;
            case "--":
                gc.strokeLine(grid.getX(col), grid.getY(row)-grid.size/3, grid.getX(col-1), grid.getY(row)-grid.size/3);
                gc.strokeLine(grid.getX(col), grid.getY(row)-2*grid.size/3, grid.getX(col-1), grid.getY(row)-2*grid.size/3);
                break;
            case "---":
                gc.strokeLine(grid.getX(col), grid.getY(row)-grid.size/4, grid.getX(col-1), grid.getY(row)-grid.size/4);
                gc.strokeLine(grid.getX(col), grid.getY(row)-2*grid.size/4, grid.getX(col-1), grid.getY(row)-2*grid.size/4);
                gc.strokeLine(grid.getX(col), grid.getY(row)-3*grid.size/4, grid.getX(col-1), grid.getY(row)-3*grid.size/4);
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                if(l.length() == 1)
                    gc.strokeLine(grid.getX(col), grid.getY(row)-grid.size/2, grid.getX(col-1)+grid.size/2, grid.getY(row)-grid.size/2);
                if(l.length() == 2)
                    gc.strokeLine(grid.getX(col), grid.getY(row)-grid.size/2, grid.getX(col)-grid.size/6, grid.getY(row)-grid.size/2);

                gc.fillText(l, grid.getX(col-1)+grid.size/12, grid.getY(row)-grid.size/3);

                gc.setFont(prev);
                break;
        }
    }



    public static void drawChainVert(GraphicsContext gc, Grid grid, int col, int row, String... c) throws Exception{
        int chainlen = c.length;

        for(int i=0; i<chainlen; i++) {
            int comma1 = c[i].indexOf(","), comma2 = c[i].indexOf(",", comma1 + 1), comma3 = c[i].indexOf(",", comma2 + 1);

            drawC(gc, grid, col, row, c[i].substring(0, comma1), c[i].substring(comma1 + 1, comma2), c[i].substring(comma2 + 1, comma3), c[i].substring(comma3 + 1));

            col += 2;
        }

        grid.getX(col);
    }

    public static void drawChainHor(GraphicsContext gc, Grid grid, int col, int row, String... c) throws Exception{
        int chainlen = c.length;

        for(int i=0; i<chainlen; i++){
            int comma1 = c[i].indexOf(","), comma2 = c[i].indexOf(",", comma1 + 1), comma3 = c[i].indexOf(",", comma2 + 1);

            drawC(gc, grid, col, row, c[i].substring(0, comma1), c[i].substring(comma1 + 1, comma2), c[i].substring(comma2 + 1, comma3), c[i].substring(comma3 + 1));

            row += 2;
        }
    }
}
