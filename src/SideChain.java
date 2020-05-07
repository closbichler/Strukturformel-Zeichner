import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SideChain {
    public GreekNumbers greekNumber;

    ArrayList<Integer> positions = new ArrayList<>();
    MainChain mainChain;
    String errors="";

    SideChain(String input) {
        regex(input);
    }

    private void regex(String input) {
        String regex = "^(\\d\\d?(,\\d\\d?)*)-?([A-Za-z]{2,})?\\(?(([a-z]{2,})" +
                "(|("+
                "(a)?(-?(\\d\\d?(,\\d\\d?)*)?-?([a-z]{2,})?(en))|" +
                "(a)?(-?(\\d\\d?(,\\d\\d?)*)?-?([a-z]{2,})?(en))?" +
                "(-?(\\d\\d?(,\\d\\d?)*)?-?([a-z]{2,})?(in))" +
                ")))yl\\)?$";

        //Stores the multiple bond positions e.g. 3,4
        String position_string;
        //Stores the greek syllable for the multi bonds e.g. di
        String greek_syllable;
        String mainchain;

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        if (m.find()) {
            position_string = m.group(1);
            greek_syllable = m.group(3);
            calc_positions(position_string);
            mainchain = calc_syllable(greek_syllable, m.group(4));
            mainChain = new MainChain(mainchain, true);
            validateChains();
            if(!mainChain.errors.equals("")){
                errors += mainChain.errors;
            }
        }
        else{
            errors += "Falsche Eingabe";
        }

    }

    void calc_positions(String position_string) {
        String[] position = position_string.split(",");

        for (String s : position) {
            positions.add(Integer.parseInt(s));
        }

    }

    String calc_syllable(String greek_syllable, String mainchain) {
        if (greek_syllable != null) {
            try {
                greekNumber = GreekNumbers.valueOf(greek_syllable);
                if (greekNumber.getValue() != positions.size() && positions.size() != 1) {
                    errors+="\nGreekNumber "+greek_syllable+" doesn't match with given positions.";
                }
            } catch (Exception e) {

                mainchain = greek_syllable + mainchain;
                return calc_syllable(null, mainchain);
            }

        } else {
            int i;
            for (i = 0; i < mainchain.length(); i++) {
                try {
                    greekNumber = GreekNumbers.valueOf(mainchain.substring(0, i));
                    break;
                } catch (Exception e) {

                }

            }

            if (i != mainchain.length()) {

                return mainchain.substring(i);
            }
        }
        if(greekNumber == null){
            greekNumber = GreekNumbers.none;
        }
        return mainchain;
    }

    boolean validateChains() {

        if (greekNumber != null && greekNumber.getValue() != positions.size() && positions.size()!= 1) {
            errors+="Wrong Greeksyllable in the sidechain";
            return false;
        }


        return true;
    }

    @Override
    public String toString() {
        return "SideChain{" +
                "greekNumber=" + greekNumber +
                ", positions=" + positions +
                ", \nmainChain=" + mainChain +
                "}\n";
    }
}
