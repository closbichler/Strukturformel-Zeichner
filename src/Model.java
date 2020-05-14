import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model {
    MainChain mainChain;
    ArrayList<SideChain> sideChains;

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
        if (mainChain.hydroCarbon != null && mainChain.hydroCarbon != HydroCarbons.none) {
            validateChains();
        }

        return false;
    }

    void validateChains() {

        for (SideChain sideChain : sideChains) {
            for (Integer position : sideChain.positions) {
                Integer bonds = mainChain.bonds_per_carbon.get(position - 1);
                mainChain.bonds_per_carbon.remove(position - 1);
                mainChain.bonds_per_carbon.add(position - 1, bonds + 1);
                if (bonds + 1 > 4) {
                    ErrorMessages.addMessage("Die Position der Seitenkette ist nicht korrekt");
                    return;
                }
                if (position <= sideChain.mainChain.hydroCarbon.getValue()) {
                    ErrorMessages.addMessage("Die Seitenkette ist inkorrekt");
                    ErrorMessages.addMessage("Die Seitenkette wäre länger als die Hauptkette");
                    return;
                } else if (position + sideChain.mainChain.hydroCarbon.getValue() > mainChain.hydroCarbon.getValue()) {
                    ErrorMessages.addMessage("Die Seitenkette wäre länger als die Hauptkette");
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
