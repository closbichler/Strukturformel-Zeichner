import com.sun.webkit.Timer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model {
    MainChain mainChain;
    ArrayList<SideChain> sideChains;
    String errors ="";

    boolean calculate(String input) {
        sideChains = new ArrayList<>();
        input = input.toLowerCase().replace(" ","").replace("-","");
        Pattern p = Pattern.compile("(\\d\\d?(,\\d\\d?)*)([a-z]{2,})?\\(?(([a-z]{2,})" +
                "(|("+
                "(a)?((\\d\\d?(,\\d\\d?)*)?([a-z]{2,})?(en))|" +
                "(a)?((\\d\\d?(,\\d\\d?)*)?([a-z]{2,})?(en))?" +
                "((\\d\\d?(,\\d\\d?)*)?([a-z]{2,})?(in))" +
                ")))yl\\)?");
        Matcher m = p.matcher(input);

        int end = 0;
        while (m.find()) {
            sideChains.add(new SideChain(m.group(0)));
            end = m.end();
        }
        mainChain = new MainChain(input.substring(end));

        validateChains();
        if(!mainChain.errors.equals(""))
            errors+="\nMainchain:"+mainChain.errors;
        for (SideChain sideChain : sideChains) {
            if(!sideChain.errors.equals("")) {
                errors += "\nSideChain:" + sideChain.errors;
            }
        }
        if(!errors.equals("")){
            System.out.println(this);
        }
        return errors.equals("");

    }

    void validateChains(){

        for (SideChain sideChain : sideChains) {
            for (Integer position : sideChain.positions) {
                Integer bonds = mainChain.bonds_per_carbon.get(position-1);
                mainChain.bonds_per_carbon.remove(position-1);
                mainChain.bonds_per_carbon.add(position-1, bonds+1);

                if(bonds+1 > 4){
                    errors+="\nWrong Sidechain on position " + position;
                    return;
                }
                if(position <= sideChain.mainChain.hydroCarbon.getValue()){
                    errors+="\nSidechain on " + position + " would be longer than the mainchain";
                    return;
                }
                else if(position + sideChain.mainChain.hydroCarbon.getValue() > mainChain.hydroCarbon.getValue() ){
                    errors+="\nSidechain on " + position + " would be longer than the mainchain";
                    return;
                }
            }
        }
        if(mainChain.isAlcohol){
            mainChain.calc_alcohol(mainChain.alcohol_positions_string, mainChain.sideChain);
        }

    }
    @Override
    public String toString() {
        return "Model{" +
                "mainChain=" + mainChain + "," +
                "\n\t sideChains=" + sideChains +
                '}';
    }
}
