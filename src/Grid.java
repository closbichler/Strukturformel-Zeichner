public class Grid {
    int len, wid, size;

    public Grid(int len, int wid, int size){
        this.len = len;
        this.wid = wid;
        this.size = size;
    }

    public int getY(int row) throws Exception{
        if(row * size <= wid)
            return row * size;
        throw new Exception("Index");
    }

    public int getX(int col) throws Exception{
        if(col * size <= len)
            return col * size;
        throw new Exception("Index");
    }
}
