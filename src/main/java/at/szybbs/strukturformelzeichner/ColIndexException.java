package at.szybbs.strukturformelzeichner;

public class ColIndexException extends Exception {
    private int col;

    public ColIndexException(){
        super();
    }

    public ColIndexException(String msg){
        super(msg);
    }

    public ColIndexException(int col){
        this.col = col;
    }

    public ColIndexException(String msg, int col){
        super(msg);
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}
