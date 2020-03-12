public class Grid {
    int len, wid, size;

    public Grid(int len, int wid, int size){
        this.len = len;
        this.wid = wid;
        this.size = size;
    }

    public int getY(int row) throws Exception{
        if(row * size <= wid && row >= 0)
            return row * size;
        throw new Exception("Index");
    }

    public int getX(int col) throws Exception{
        if(col * size <= len && col >= 0)
            return col * size;
        throw new Exception("Index");
    }

    public int getMaxRow(){
        return wid/size;
    }

    public int getMaxCol(){
        return len/size;
    }

    public int getMaxY(){
        return ((int)(wid/size)) * size;
    }

    public int getMaxX(){
        return ((int)(len/size)) * size;
    }
}
