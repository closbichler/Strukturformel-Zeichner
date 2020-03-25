import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SideChain {
    public GreekNumbers greekNumber;

    ArrayList<Integer> positions = new ArrayList<>();
    MainChain mainChain;

    SideChain(String input) {
        regex(input);
    }

    private boolean regex(String input) {
        String regex = "^(\\d(,\\d)*)-?\\s*?([A-Za-z]{2,})?\\(?(.*)yl\\)?$";
        String name;

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
            mainchain = m.group(4).trim();
            calc_positions(position_string);
            mainchain = calc_syllable(greek_syllable, mainchain);
            mainChain = new MainChain(mainchain, true);

        }
        return false;
    }

    void calc_positions(String position_string) {
        String[] position = position_string.split(",");

        for (String s : position) {
            positions.add(Integer.parseInt(s));
        }

    }

    String calc_syllable(String greek_syllable, String mainchain) {
        if (greek_syllable == null) {
            int i;
            for (i = 0; i < mainchain.length(); i++) {
                try {
                    greekNumber = GreekNumbers.valueOf(mainchain.substring(0, i));
                    break;
                } catch (Exception e) {

                }

            }
            if (i != mainchain.length()) {
                mainchain = mainchain.substring(i);

            }
        } else if (greek_syllable != null) {
            try {
                greekNumber = GreekNumbers.valueOf(greek_syllable);
                if (greekNumber.getValue() != positions.size()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Wrong Greek Syllable");
            }

        }
        return mainchain;
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
