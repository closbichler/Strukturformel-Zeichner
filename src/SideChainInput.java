import java.util.ArrayList;

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

    public SideChainInput(Orientation or, int pos, ArrayList<ArrayList<String>> chain){
        this.orientation = or;
        this.pos = pos;

        this.chain = new String[chain.size()][];
        for(int i=0; i<chain.size(); i++){
            ArrayList<String> c = chain.get(i);
            this.chain[i] = c.toArray(new String[c.size()]);
        }
    }

    @Override
    public String toString() {
        String chainString = "";
        for (String[] strings : chain) {
            chainString+="[";
            for (String string : strings) {
                chainString+=string + ",";
            }
            chainString+="],";
        }
        return "SideChainInput{" +
                "orientation=" + orientation +
                ", pos=" + pos +
                ", chain=" + chainString+
                '}';
    }
}
