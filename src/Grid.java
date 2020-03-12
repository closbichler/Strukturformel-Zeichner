import javafx.scene.canvas.GraphicsContext;

class Grid {
    private int len, wid, size;

    Grid(int len, int wid, int size){
        this.len = len;
        this.wid = wid;
        this.size = size;
    }

    int getY(int row) throws Exception{
        if(row * size <= wid && row >= 0)
            return row * size;
        throw new Exception("Index");
    }

    int getX(int col) throws Exception{
        if(col * size <= len && col >= 0)
            return col * size;
        throw new Exception("Index");
    }

    int getMaxRow(){
        return wid/size;
    }

    int getMaxCol(){
        return len/size;
    }

    int getMaxY(){
        return (wid/size) * size;
    }

    int getMaxX(){
        return (len/size) * size;
    }

    void drawGrid(GraphicsContext gc) throws Exception{
        for (int i = 0; i < getMaxCol(); i++)
            gc.strokeLine(getX(i), getY(0), getX(i), getMaxY());

        for (int i = 0; i < getMaxRow(); i++)
            gc.strokeLine(getX(0), getY(i), getMaxX(), getY(i));
    }

    int getSize(){
        return size;
    }
}
