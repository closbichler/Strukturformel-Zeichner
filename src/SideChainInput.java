enum Orientation{
    Up, Right, Left, Down
}

public class SideChainInput {
    Orientation orientation;
    int pos;
    String[][] chain;

    public SideChainInput(Orientation or, int pos, String[][] chain){
        this.orientation = or;
        this.pos = pos;
        this.chain = chain;
    }
}
