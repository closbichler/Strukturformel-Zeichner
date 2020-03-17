import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class CanvasFkt {
    public static void drawC(GraphicsContext gc, Grid grid, int col, int row, String u, String r, String d, String l) throws Exception{

        row++;
        gc.fillText("C", grid.getX(col)+(float)grid.getSize()/6, grid.getY(row)-(float)grid.getSize()/7);

        switch(u){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/2, grid.getY(row-2), grid.getX(col)+(float)grid.getSize()/2, grid.getY(row-1));
                break;
            case "--":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/3, grid.getY(row-2), grid.getX(col)+(float)grid.getSize()/3, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+2*(float)grid.getSize()/3, grid.getY(row-2), grid.getX(col)+2*(float)grid.getSize()/3, grid.getY(row-1));
                break;
            case "---":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/4, grid.getY(row-2), grid.getX(col)+(float)grid.getSize()/4, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+2*(float)grid.getSize()/4, grid.getY(row-2), grid.getX(col)+2*(float)grid.getSize()/4, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+3*(float)grid.getSize()/4, grid.getY(row-2), grid.getX(col)+3*(float)grid.getSize()/4, grid.getY(row-1));
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/2, grid.getY(row-2)+(float)grid.getSize()/2, grid.getX(col)+(float)grid.getSize()/2, grid.getY(row-1));
                if(u.length() == 1)
                    gc.fillText(u, grid.getX(col)+(float)grid.getSize()/3-(float)grid.getSize()/25, grid.getY(row-2)+(float)grid.getSize()/2-(float)grid.getSize()/12);
                if(u.length() == 2)
                    gc.fillText(u, grid.getX(col)+(float)grid.getSize()/6, grid.getY(row-2)+(float)grid.getSize()/2-(float)grid.getSize()/12);

                gc.setFont(prev);
                break;
        }

        switch(r){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-(float)grid.getSize()/2, grid.getX(col+2), grid.getY(row)-(float)grid.getSize()/2);
                break;
            case "--":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-(float)grid.getSize()/3, grid.getX(col+2), grid.getY(row)-(float)grid.getSize()/3);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-2*(float)grid.getSize()/3, grid.getX(col+2), grid.getY(row)-2*(float)grid.getSize()/3);
                break;
            case "---":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-(float)grid.getSize()/4, grid.getX(col+2), grid.getY(row)-(float)grid.getSize()/4);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-2*(float)grid.getSize()/4, grid.getX(col+2), grid.getY(row)-2*(float)grid.getSize()/4);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-3*(float)grid.getSize()/4, grid.getX(col+2), grid.getY(row)-3*(float)grid.getSize()/4);
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                if(r.length() == 1) {
                    gc.strokeLine(grid.getX(col + 1), grid.getY(row)-(float)grid.getSize() / 2, grid.getX(col + 1)+(float)grid.getSize() / 2, grid.getY(row)-(float)grid.getSize() / 2);
                    gc.fillText(r, grid.getX(col+1)+(float)grid.getSize()/2+(float)grid.getSize()/12, grid.getY(row)-(float)grid.getSize()/3);

                }
                if(r.length() == 2) {
                    gc.strokeLine(grid.getX(col + 1), grid.getY(row)-(float)grid.getSize() / 2, grid.getX(col + 1)+(float)grid.getSize() / 6, grid.getY(row)-(float)grid.getSize() / 2);
                    gc.fillText(r, grid.getX(col+1)+(float)grid.getSize()/4, grid.getY(row)-(float)grid.getSize()/3);
                }

                gc.setFont(prev);
                break;
        }

        switch(d){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/2, grid.getY(row), grid.getX(col)+(float)grid.getSize()/2, grid.getY(row+1));
                break;
            case "--":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/3, grid.getY(row), grid.getX(col)+(float)grid.getSize()/3, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+2*(float)grid.getSize()/3, grid.getY(row), grid.getX(col)+2*(float)grid.getSize()/3, grid.getY(row+1));
                break;
            case "---":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/4, grid.getY(row), grid.getX(col)+(float)grid.getSize()/4, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+2*(float)grid.getSize()/4, grid.getY(row), grid.getX(col)+2*(float)grid.getSize()/4, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+3*(float)grid.getSize()/4, grid.getY(row), grid.getX(col)+3*(float)grid.getSize()/4, grid.getY(row+1));
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/2, grid.getY(row)+(float)grid.getSize()/2, grid.getX(col)+(float)grid.getSize()/2, grid.getY(row));
                if(d.length() == 1)
                    gc.fillText(d, grid.getX(col)+(float)grid.getSize()/3-(float)grid.getSize()/25, grid.getY(row+1)-(float)grid.getSize()/12);
                if(d.length() == 2)
                    gc.fillText(d, grid.getX(col)+(float)grid.getSize()/6, grid.getY(row+1)-(float)grid.getSize()/12);

                gc.setFont(prev);
                break;
        }

        switch(l){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/2, grid.getX(col-1), grid.getY(row)-(float)grid.getSize()/2);
                break;
            case "--":
                gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/3, grid.getX(col-1), grid.getY(row)-(float)grid.getSize()/3);
                gc.strokeLine(grid.getX(col), grid.getY(row)-2*(float)grid.getSize()/3, grid.getX(col-1), grid.getY(row)-2*(float)grid.getSize()/3);
                break;
            case "---":
                gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/4, grid.getX(col-1), grid.getY(row)-(float)grid.getSize()/4);
                gc.strokeLine(grid.getX(col), grid.getY(row)-2*(float)grid.getSize()/4, grid.getX(col-1), grid.getY(row)-2*(float)grid.getSize()/4);
                gc.strokeLine(grid.getX(col), grid.getY(row)-3*(float)grid.getSize()/4, grid.getX(col-1), grid.getY(row)-3*(float)grid.getSize()/4);
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                if(l.length() == 1)
                    gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/2, grid.getX(col-1)+(float)grid.getSize()/2, grid.getY(row)-(float)grid.getSize()/2);
                if(l.length() == 2)
                    gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/2, grid.getX(col)-(float)grid.getSize()/6, grid.getY(row)-(float)grid.getSize()/2);

                gc.fillText(l, grid.getX(col-1)+(float)grid.getSize()/12, grid.getY(row)-(float)grid.getSize()/3);

                gc.setFont(prev);
                break;
        }
    }

    public static void drawC(GraphicsContext gc, Grid grid, int col, int row, ArrayList<String> c) throws Exception{

        row++;
        gc.fillText("C", grid.getX(col)+(float)grid.getSize()/6, grid.getY(row)-(float)grid.getSize()/7);

        drawC(gc, grid, col, row, c.get(0), c.get(1), c.get(2), c.get(3));
    }

    public static void drawC(GraphicsContext gc, Grid grid, int col, int row, String[] c) throws Exception{

        row++;
        gc.fillText("C", grid.getX(col)+(float)grid.getSize()/6, grid.getY(row)-(float)grid.getSize()/7);

        switch(c[0]){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/2, grid.getY(row-2), grid.getX(col)+(float)grid.getSize()/2, grid.getY(row-1));
                break;
            case "--":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/3, grid.getY(row-2), grid.getX(col)+(float)grid.getSize()/3, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+2*(float)grid.getSize()/3, grid.getY(row-2), grid.getX(col)+2*(float)grid.getSize()/3, grid.getY(row-1));
                break;
            case "---":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/4, grid.getY(row-2), grid.getX(col)+(float)grid.getSize()/4, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+2*(float)grid.getSize()/4, grid.getY(row-2), grid.getX(col)+2*(float)grid.getSize()/4, grid.getY(row-1));
                gc.strokeLine(grid.getX(col)+3*(float)grid.getSize()/4, grid.getY(row-2), grid.getX(col)+3*(float)grid.getSize()/4, grid.getY(row-1));
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/2, grid.getY(row-2)+(float)grid.getSize()/2, grid.getX(col)+(float)grid.getSize()/2, grid.getY(row-1));
                if(c[0].length() == 1)
                    gc.fillText(c[0], grid.getX(col)+(float)grid.getSize()/3-(float)grid.getSize()/25, grid.getY(row-2)+(float)grid.getSize()/2-(float)grid.getSize()/12);
                if(c[0].length() == 2)
                    gc.fillText(c[0], grid.getX(col)+(float)grid.getSize()/6, grid.getY(row-2)+(float)grid.getSize()/2-(float)grid.getSize()/12);

                gc.setFont(prev);
                break;
        }

        switch(c[1]){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-(float)grid.getSize()/2, grid.getX(col+2), grid.getY(row)-(float)grid.getSize()/2);
                break;
            case "--":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-(float)grid.getSize()/3, grid.getX(col+2), grid.getY(row)-(float)grid.getSize()/3);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-2*(float)grid.getSize()/3, grid.getX(col+2), grid.getY(row)-2*(float)grid.getSize()/3);
                break;
            case "---":
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-(float)grid.getSize()/4, grid.getX(col+2), grid.getY(row)-(float)grid.getSize()/4);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-2*(float)grid.getSize()/4, grid.getX(col+2), grid.getY(row)-2*(float)grid.getSize()/4);
                gc.strokeLine(grid.getX(col+1), grid.getY(row)-3*(float)grid.getSize()/4, grid.getX(col+2), grid.getY(row)-3*(float)grid.getSize()/4);
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                if(c[1].length() == 1) {
                    gc.strokeLine(grid.getX(col + 1), grid.getY(row)-(float)grid.getSize() / 2, grid.getX(col + 1)+(float)grid.getSize() / 2, grid.getY(row)-(float)grid.getSize() / 2);
                    gc.fillText(c[1], grid.getX(col+1)+(float)grid.getSize()/2+(float)grid.getSize()/12, grid.getY(row)-(float)grid.getSize()/3);

                }
                if(c[1].length() == 2) {
                    gc.strokeLine(grid.getX(col + 1), grid.getY(row)-(float)grid.getSize() / 2, grid.getX(col + 1)+(float)grid.getSize() / 6, grid.getY(row)-(float)grid.getSize() / 2);
                    gc.fillText(c[1], grid.getX(col+1)+(float)grid.getSize()/4, grid.getY(row)-(float)grid.getSize()/3);
                }

                gc.setFont(prev);
                break;
        }

        switch(c[2]){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/2, grid.getY(row), grid.getX(col)+(float)grid.getSize()/2, grid.getY(row+1));
                break;
            case "--":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/3, grid.getY(row), grid.getX(col)+(float)grid.getSize()/3, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+2*(float)grid.getSize()/3, grid.getY(row), grid.getX(col)+2*(float)grid.getSize()/3, grid.getY(row+1));
                break;
            case "---":
                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/4, grid.getY(row), grid.getX(col)+(float)grid.getSize()/4, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+2*(float)grid.getSize()/4, grid.getY(row), grid.getX(col)+2*(float)grid.getSize()/4, grid.getY(row+1));
                gc.strokeLine(grid.getX(col)+3*(float)grid.getSize()/4, grid.getY(row), grid.getX(col)+3*(float)grid.getSize()/4, grid.getY(row+1));
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                gc.strokeLine(grid.getX(col)+(float)grid.getSize()/2, grid.getY(row)+(float)grid.getSize()/2, grid.getX(col)+(float)grid.getSize()/2, grid.getY(row));
                if(c[2].length() == 1)
                    gc.fillText(c[2], grid.getX(col)+(float)grid.getSize()/3-(float)grid.getSize()/25, grid.getY(row+1)-(float)grid.getSize()/12);
                if(c[2].length() == 2)
                    gc.fillText(c[2], grid.getX(col)+(float)grid.getSize()/6, grid.getY(row+1)-(float)grid.getSize()/12);

                gc.setFont(prev);
                break;
        }

        switch(c[3]){
            case "":
                break;
            case "-":
                gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/2, grid.getX(col-1), grid.getY(row)-(float)grid.getSize()/2);
                break;
            case "--":
                gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/3, grid.getX(col-1), grid.getY(row)-(float)grid.getSize()/3);
                gc.strokeLine(grid.getX(col), grid.getY(row)-2*(float)grid.getSize()/3, grid.getX(col-1), grid.getY(row)-2*(float)grid.getSize()/3);
                break;
            case "---":
                gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/4, grid.getX(col-1), grid.getY(row)-(float)grid.getSize()/4);
                gc.strokeLine(grid.getX(col), grid.getY(row)-2*(float)grid.getSize()/4, grid.getX(col-1), grid.getY(row)-2*(float)grid.getSize()/4);
                gc.strokeLine(grid.getX(col), grid.getY(row)-3*(float)grid.getSize()/4, grid.getX(col-1), grid.getY(row)-3*(float)grid.getSize()/4);
                break;
            default:
                Font prev = gc.getFont();
                gc.setFont(Font.font("Arial", prev.getSize()/2));

                if(c[3].length() == 1)
                    gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/2, grid.getX(col-1)+(float)grid.getSize()/2, grid.getY(row)-(float)grid.getSize()/2);
                if(c[3].length() == 2)
                    gc.strokeLine(grid.getX(col), grid.getY(row)-(float)grid.getSize()/2, grid.getX(col)-(float)grid.getSize()/6, grid.getY(row)-(float)grid.getSize()/2);

                gc.fillText(c[3], grid.getX(col-1)+(float)grid.getSize()/12, grid.getY(row)-(float)grid.getSize()/3);

                gc.setFont(prev);
                break;
        }
    }

    public static void drawChainVert(GraphicsContext gc, Grid grid, int col, int row, String... c) throws Exception{
        for (String s : c) {
            int comma1 = s.indexOf(","), comma2 = s.indexOf(",", comma1 + 1), comma3 = s.indexOf(",", comma2 + 1);

            drawC(gc, grid, col, row, s.substring(0, comma1), s.substring(comma1 + 1, comma2), s.substring(comma2 + 1, comma3), s.substring(comma3 + 1));

            col += 2;
        }

        grid.getX(col);
    }

    public static void drawChainHor(GraphicsContext gc, Grid grid, int col, int row, String... c) throws Exception{
        for (String s : c) {
            int comma1 = s.indexOf(","), comma2 = s.indexOf(",", comma1 + 1), comma3 = s.indexOf(",", comma2 + 1);

            drawC(gc, grid, col, row, s.substring(0, comma1), s.substring(comma1 + 1, comma2), s.substring(comma2 + 1, comma3), s.substring(comma3 + 1));

            row += 2;
        }
    }

    public static void drawChainVert(GraphicsContext gc, Grid grid, int col, int row, ArrayList<ArrayList<String>> c) throws Exception{
        for (ArrayList<String> s : c) {
           drawC(gc, grid, col, row, s.get(0), s.get(1), s.get(2), s.get(3));

            col += 2;
        }

        grid.getX(col);
    }

    public static void drawChainHor(GraphicsContext gc, Grid grid, int col, int row, ArrayList<ArrayList<String>> c) throws Exception{
        for (ArrayList<String> s : c) {
            drawC(gc, grid, col, row, s.get(0), s.get(1), s.get(2), s.get(3));

            row += 2;
        }
    }

    public static void drawChainVert(GraphicsContext gc, Grid grid, int col, int row, String[][] c) throws Exception{
        for (String[] s : c) {
            drawC(gc, grid, col, row, s[0], s[1], s[2], s[3]);

            col += 2;
        }

        grid.getX(col);
    }

    public static void drawChainHor(GraphicsContext gc, Grid grid, int col, int row, String[][] c) throws Exception{
        for (String[] s : c) {
            drawC(gc, grid, col, row, s[0], s[1], s[2], s[3]);

            row += 2;
        }
    }

    public static void drawChainVertWithSideChains(GraphicsContext gc, Grid grid, int col, int row, String[][] c, SideChainInput... s) throws Exception{
        int colc = col;
        for (String[] i : c) {
            drawC(gc, grid, colc, row, i[0], i[1], i[2], i[3]);

            colc += 2;
        }

        grid.getX(colc);

        for (SideChainInput i : s){
            if(i.orientation == Orientation.Down){
                drawChainHor(gc, grid, col+2*(i.pos-1), row+2, i.chain);
            }
            else if(i.orientation == Orientation.Up){
                drawChainHor(gc, grid, col+2*(i.pos-1), row-2*i.chain.length, i.chain);
            }
        }
    }

    public static void drawChainHorWithSideChains(GraphicsContext gc, Grid grid, int col, int row, String[][] c, SideChainInput... s) throws Exception{
        int rowc = row;
        for (String[] i : c) {
            drawC(gc, grid, col, rowc, i[0], i[1], i[2], i[3]);

            rowc += 2;
        }

        for (SideChainInput i : s){
            if(i.orientation == Orientation.Right){
                drawChainVert(gc, grid, col+2, row+2*(i.pos-1), i.chain);
            }
            else if(i.orientation == Orientation.Left){
                drawChainVert(gc, grid, col-2*i.chain.length, row+2*(i.pos-1), i.chain);
            }
        }
    }

    public static void drawChainVertWithSideChains(GraphicsContext gc, Grid grid, int col, int row, ArrayList<ArrayList<String>> c, SideChainInput... s) throws Exception{
        drawChainVertWithSideChains(gc, grid, col, row, c, s);
    }

    public static void drawChainHorWithSideChains(GraphicsContext gc, Grid grid, int col, int row, ArrayList<ArrayList<String>> c, SideChainInput... s) throws Exception{
        drawChainHorWithSideChains(gc, grid, col, row, c, s);
    }
}
