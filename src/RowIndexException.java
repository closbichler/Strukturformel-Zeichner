public class RowIndexException extends Exception {
    private int row;

    public RowIndexException(){
        super();
    }

    public RowIndexException(String msg){
        super(msg);
    }

    public RowIndexException(int row){
        this.row = row;
    }

    public RowIndexException(String msg, int row){
        super(msg);
        this.row = row;
    }

    public int getRow() {
        return row;
    }
}
