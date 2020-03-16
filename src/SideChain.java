enum Orientation{
    Up, Right, Left, Down;
}

public class SideChain {
    Orientation orientation;
    int pos;
    String[][] chain;

    public SideChain(Orientation or, int pos, String[][] chain){
        this.orientation = or;
        this.pos = pos;
        this.chain = chain;
    }
}
