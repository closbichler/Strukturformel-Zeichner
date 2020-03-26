import com.sun.webkit.Timer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model {
    MainChain mainChain;
    ArrayList<SideChain> sideChains;

    void calculate(String input) {
        sideChains = new ArrayList<>();
        Pattern p = Pattern.compile("(.*)yl\\)?");
        Matcher m = p.matcher(input);

        int end = 0;
        while (m.find()) {
            sideChains.add(new SideChain(input.substring(m.start(), m.end())));
            end = m.end();
        }
        mainChain = new MainChain(input.substring(end));
        System.out.println(this);
        validateChains();


    }

    boolean validateChains(){

        for (SideChain sideChain : sideChains) {
            for (Integer position : sideChain.positions) {
                Integer bonds = mainChain.bonds_per_carbon.get(position);
                mainChain.bonds_per_carbon.remove(position);
                mainChain.bonds_per_carbon.add(position, bonds+1);
                if(bonds+1 > 4){
                    System.out.println("Wrong Sidechain on position " + position);
                    return false;
                }
                if(position <= sideChain.mainChain.hydroCarbon.getValue()){
                    System.out.println("Sidechain on " + position + " would be longer than the mainchain");
                    return false;
                }
                else if(position + sideChain.mainChain.hydroCarbon.getValue() > mainChain.hydroCarbon.getValue() ){
                    System.out.println("Sidechain on " + position + " would be longer than the mainchain");
                    return false;
                }
            }
        }

        return true;
    }
    @Override
    public String toString() {
        return "Model{" +
                "mainChain=" + mainChain + "," +
                "\n\t sideChains=" + sideChains +
                '}';
    }
}
