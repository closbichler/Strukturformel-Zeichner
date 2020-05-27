import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Stores the MainChain of the HydroCarbon
//Simply create an object of it and call the method regex

//Import variables are the enums hydroCarbon, greekNumber, double_bonds and triple_bonds
public class MainChain {

    //Stores carbon count and multibond count
    public HydroCarbons hydroCarbon;
    public GreekNumbers greekNumber_en;
    public GreekNumbers greekNumber_in;

    //Stores the position of the double and triple bonds
    public ArrayList<Integer> double_bonds = new ArrayList<>();
    public ArrayList<Integer> triple_bonds = new ArrayList<>();
    public ArrayList<Integer> bonds_per_carbon = new ArrayList<>();

    public ArrayList<Integer> alcohol_positions = new ArrayList<>();
    public GreekNumbers greekNumber_alcohol;

    private String name;
    private Integer i;
    private String ending_an;
    private String ending_en;
    private String ending_in;

    //Stores the multiple bond positions e.g. 3,4
    private String multibond_positions_en;
    private String multibond_positions_in;
    //Stores the greek syllable for the multi bonds e.g. di
    private String greek_syllable_en;
    private String greek_syllable_in;
    //a or ""
    private String multibond_char;
    public boolean isAlcohol;
    public String alcohol_positions_string;
    private String greek_syllable_alcohol;
    public boolean sideChain;
    public int h_atoms = 0;
    public int oh_atoms;

    MainChain(String input, boolean sideChain) {
        regex(input, sideChain);
    }

    MainChain(String input) {
        regex(input, false);
    }

    String regex = "^-?([a-z]{2,})" +
            "((an)|(" + //4
            "(a)?(-?(\\d{1,3}(,\\d{1,3})*)?-?([a-z]{2,})?(en))|" + //10
            "(a)?(-?(\\d{1,3}(,\\d{1,3})*)?-?([a-z]{2,})?(en))?" + //16
            "(-?(\\d{1,3}(,\\d{1,3})*)?-?([a-z]{2,})?(in))" + //21
            "))((\\d{1,3}(,\\d{1,3})*)?([a-z]{2,})?(ol))?$";

    //Takes the input and turns calculates the output values
    //Returns if the input was correct or if there were any mistakes3e
    private void regex(String input, boolean sideChain) {
        //Stores the input String, the regex, the name (e.g. Prop) and the ending (e.g. an)
        this.sideChain = sideChain;
        if (sideChain) {
            if (!input.matches("^.*[aei]n$")) {
                input += "an";
            }
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        if (m.find()) {
            name = m.group(1);
            if (!findName()) {
                p = Pattern.compile("^()" +
                        "((an)|(" + //4
                        "(a)?(-?(\\d\\d?(,\\d\\d?)*)?-?([a-z]{2,})?(en))|" + //10
                        "(a)?(-?(\\d\\d?(,\\d\\d?)*)?-?([a-z]{2,})?(en))?" + //16
                        "(-?(\\d\\d?(,\\d\\d?)*)?-?([a-z]{2,})?(in))" + //21
                        "))((\\d\\d?(,\\d\\d?)*)?([a-z]{2,})?(ol))?$");
                m = p.matcher(input.substring(i));
                if (!m.find()) {
                    ErrorMessages.throwUndefinedError();
                    return;
                }
            }
            if (m.group(26) != null) {
                isAlcohol = true;
                alcohol_positions_string = m.group(23);
                greek_syllable_alcohol = m.group(25);
            }
            ending_an = m.group(3);
            multibond_char = m.group(5);

            multibond_positions_en = m.group(7);
            greek_syllable_en = m.group(9);
            ending_en = m.group(10);
            if (ending_en == null) {
                multibond_char = m.group(11);
                multibond_positions_en = m.group(13);
                greek_syllable_en = m.group(15);
                ending_en = m.group(16);
            }

            multibond_positions_in = m.group(18);
            greek_syllable_in = m.group(20);
            ending_in = m.group(21);


            if (ending_an != null) {
                calc_multiple_bondings(name, ending_an, multibond_positions_en);
                calc_multiple_bondings(name, ending_an, multibond_positions_in);
                calc_enums(name, greek_syllable_en, greek_syllable_in, isAlcohol);
                calc_bonds_per_carbon();

                validate_input(multibond_char, ending_an);
            } else {

                if (ending_en != null) {
                    calc_multiple_bondings(name, ending_en, multibond_positions_en);
                    calc_enums(name, greek_syllable_en, greek_syllable_in, isAlcohol);
                    findPositions(ending_en, ending_in);
                    calc_bonds_per_carbon();

                    validate_input(multibond_char, ending_en);
                }
                if (ending_in != null) {
                    calc_multiple_bondings(name, ending_in, multibond_positions_in);
                    calc_enums(name, greek_syllable_en, greek_syllable_in, isAlcohol);
                    findPositions(ending_en, ending_in);
                    calc_bonds_per_carbon();

                    validate_input(multibond_char, ending_in);
                }
                if (ending_en == null && ending_in == null && ending_an == null) {
                    //No ending
                    ErrorMessages.addMessage("\nEs fehlt eine Endsilbe (z.B.: an, en, in)");
                }
                oh_atoms = alcohol_positions.size();

            }

        } else {
            ErrorMessages.throwUndefinedError();
        }


    }

    public void calc_alcohol(String alcohol_positions_string, boolean sidechain) {
        if (alcohol_positions_string != null) {
            String[] split = alcohol_positions_string.split(",");
            for (String s : split) {
                int pos = Integer.parseInt(s);
                int anzahl = Collections.frequency(alcohol_positions, pos);
                if(pos > hydroCarbon.getValue()){
                    ErrorMessages.addMessage("Es kann eine OH Gruppe an der " + pos + ". Stelle geben");
                    return;
                }
                else if (bonds_per_carbon.get(pos - 1) < 4 - anzahl) {
                    alcohol_positions.add(pos);
                } else {
                    ErrorMessages.addMessage("Es können nicht " + (anzahl + 1) + " OH-Gruppen an der Stelle " + pos + " geben");
                    alcohol_positions.add(pos);
                }

            }
        }
        if (alcohol_positions.size() == 0) {
            ErrorMessages.addMessage("Bitte geben Sie Positionen für die OH-Gruppen mit");

            if (greekNumber_alcohol.getValue() == 0) {
                alcohol_positions.add(1);
            } else if (greekNumber_alcohol.getValue() <= hydroCarbon.getValue()) {
                for (int i = 1; i <= greekNumber_alcohol.getValue(); i++) {
                    alcohol_positions.add(i);
                }
            } else {
                int available_bonds = 0;
                for (Integer integer : bonds_per_carbon) {
                    available_bonds += 4 - integer;
                }
                if (greekNumber_alcohol.getValue() <= available_bonds) {
                    for (int i = 1; i <= hydroCarbon.getValue(); i++) {
                        int bonds = bonds_per_carbon.get(i - 1); //3
                        int anzahl = 0;
                        if (i == 1 && !sidechain && bonds < (4 - anzahl) && alcohol_positions.size() < greekNumber_alcohol.getValue()) {
                            alcohol_positions.add(i);
                            anzahl++;
                        }
                        if (i == hydroCarbon.getValue() && bonds < (4 - anzahl) && alcohol_positions.size() < greekNumber_alcohol.getValue()) {
                            alcohol_positions.add(i);
                            anzahl++;
                        }
                        if (alcohol_positions.size() < greekNumber_alcohol.getValue() && bonds < (4 - anzahl)) {
                            alcohol_positions.add(i);
                            anzahl++;
                        }

                        if (alcohol_positions.size() < greekNumber_alcohol.getValue() && bonds < (4 - anzahl)) {
                            alcohol_positions.add(i);

                        }
                    }

                } else {
                    ErrorMessages.addMessage("Nicht genug Platz für " + greekNumber_alcohol.getValue() + " OH-Gruppen");
                }
            }

        } else {
            if (alcohol_positions.size() != greekNumber_alcohol.getValue() && alcohol_positions.size() != 1) {
                ErrorMessages.addMessage("Falsche multiplizierende Vorsilbe für " + alcohol_positions.size() + " OH-Gruppen");
            }
        }

    }

    private boolean findName() {

        for (i = 2; i < name.length(); i++) {
            try {
                HydroCarbons.valueOf(name.substring(0, i));
                System.out.println(name.substring(0, i) + " " + name.length());
                for (int j = i+1; j <= name.length(); j++) {
                    try {
                        System.out.println(name.substring(0, j));
                        HydroCarbons.valueOf(name.substring(0, j));

                        i = j;
                        break;
                    } catch (Exception ignored) {
                    }

                }
                name = name.substring(0, i);
                break;
            } catch (Exception ignored) {

            }
        }
        return i == name.length() - 1;
    }

    private void findPositions(String ending_en, String ending_in) {
        if (ending_en != null && ending_en.equals("en") && double_bonds.size() == 0) {
            if (hydroCarbon.getValue() - 1 == greekNumber_en.getValue() || (hydroCarbon.getValue() == 2 && greekNumber_en.getValue() == 0)) {
                for (int i = 1; i < hydroCarbon.getValue(); i++) {
                    double_bonds.add(i);
                }
            }
        }
        if (ending_in != null && ending_in.equals("in") && triple_bonds.size() == 0) {
            if (hydroCarbon.getValue() - 1 == greekNumber_in.getValue() || (hydroCarbon.getValue() == 2 && greekNumber_in.getValue() == 0)) {
                for (int i = 1; i < hydroCarbon.getValue(); i++) {
                    triple_bonds.add(i);
                }
            }
        }

    }

    //Calculates the multibonds with the given input
    //Returns if the input was correct
    private void calc_multiple_bondings(String name, String ending, String multibond_positions) {

        if ((ending.equals("en") || ending.equals("in")) && name.equals("Meth")) {
            //Meth syllable with en or in
            ErrorMessages.addMessage("Die Endsilbe ist nicht korrekt");
            ErrorMessages.addMessage("Bei Verbindungen mit der Länge 1 (Methan) können keine -en oder -in Endsilben vorkommen");
            return;
        }
        //Getting the positions into the arrays double_bonds and triple_bonds
        if ((ending.equals("en") || ending.equals("in")) && multibond_positions != null) {
            String[] pos_string = multibond_positions.split(",");
            for (String s : pos_string) {
                int newinput = Integer.parseInt(s);
                if (ending.equals("en") && !double_bonds.contains(newinput) && !triple_bonds.contains(newinput)) {
                    double_bonds.add(Integer.parseInt(s));

                } else if (ending.equals("in") && !triple_bonds.contains(newinput) && !double_bonds.contains(newinput)) {
                    triple_bonds.add(Integer.parseInt(s));

                } else {
                    //Duplicate Position
                    ErrorMessages.addMessage("Die Positionen der Mehrfachbindungen kommen doppelt vor");
                    return;
                }
            }

        } else if (ending.equals("an")) {
            if (multibond_positions != null && !multibond_positions.equals("")) {
                //Ending "an" but given multibond position
                ErrorMessages.addMessage("Die Position der Mehrfachbindung ist überflüssig");
                ErrorMessages.addMessage("Ein Alkan hat keine Mehrfachbindungen");
                return;
            }
        }
    }

    //Creates the enum values from the input name and the greek_syllable
    //Returns if the transition was successfull
    private void calc_enums(String name, String greek_syllable_en, String greek_syllable_in, boolean isAlcohol) {

        //Setting hydroCarbon to the given Value
        try {
            hydroCarbon = HydroCarbons.valueOf(name);
        } catch (Exception e) {
            //No HydroCarbon or Wrong HydroCarbon name
            hydroCarbon = HydroCarbons.none;
            ErrorMessages.addMessage("Der Name der Hauptkette ist inkorrekt");

        }

        //setting greekNumber to the given syllable e.g. di
        if (greek_syllable_en != null) {
            try {
                greekNumber_en = GreekNumbers.valueOf(greek_syllable_en);
            } catch (Exception e) {
                greekNumber_en = GreekNumbers.none;
                //No Greek Syllable or Wrong Greek Syllable name
                ErrorMessages.addMessage("Die Vorsilbe ist inkorrekt");
            }


        }
        if (greek_syllable_in != null) {
            try {
                greekNumber_in = GreekNumbers.valueOf(greek_syllable_in);
            } catch (Exception e) {
                greekNumber_in = GreekNumbers.none;
                //No Greek Syllable or Wrong Greek Syllable name
                ErrorMessages.addMessage("Die Vorsilbe ist inkorrekt");
                return;
            }

        }
        if (greek_syllable_en == null && greek_syllable_in == null) {
            greekNumber_en = GreekNumbers.none;
            greekNumber_in = GreekNumbers.none;
        }
        if (greekNumber_en == null) {
            greekNumber_en = GreekNumbers.none;
        }
        if (greekNumber_in == null) {
            greekNumber_in = GreekNumbers.none;
        }

        if (isAlcohol) {
            try {
                greekNumber_alcohol = GreekNumbers.valueOf(greek_syllable_alcohol);
            } catch (Exception e) {
                greekNumber_alcohol = GreekNumbers.none;
            }
        }
    }

    //Validates the input
    //Returns if the input was correct
    private void validate_input(String multibond_char, String ending) {

        //Checking if there was an "a" input
        if (double_bonds.size() >= 2 || triple_bonds.size() >= 2) {
            if (multibond_char == null) {
                //missing a after carbon counting syllable
                //e.g Prop2,3dien
                ErrorMessages.addMessage("Nach dem Zahlwort fehlt ein -a (z.B.: Propa 2,3 dien)");
                return;
            }
        }
        if (ending.equals("en") && double_bonds.size() == 0) {
            //Ending en or in but no position given
            ErrorMessages.addMessage("Es werden keine Positionen der Mehrfachbindungen mitgegeben");
            return;
        } else if (ending.equals("in") && triple_bonds.size() == 0) {
            //Ending en or in but no position given
            ErrorMessages.addMessage("Es werden keine Positionen der Mehrfachbindungen mitgegeben");
            return;
        }
        //Checking the positions of the multibonds
        if (double_bonds.size() >= 1 || triple_bonds.size() >= 1) {
            for (Integer double_bond : double_bonds) {
                if (double_bond <= 0 || double_bond >= hydroCarbon.getValue()) {
                    //Illegal Positions
                    ErrorMessages.addMessage("Die Positionen der Doppelbindungen sind nicht korrekt");
                    return;
                }
            }

            for (Integer triple_bond : triple_bonds) {
                if (triple_bond <= 0 || triple_bond >= hydroCarbon.getValue()) {
                    //Illegal Positions
                    ErrorMessages.addMessage("Die Positionen der Dreifachbindungen sind nicht korrekt");
                    return;
                }
            }
        } else {
            if (multibond_char != null && multibond_char.equals("a")) {
                //Incorrect a after Carbon syllable
                //e.g. Propa1en
                ErrorMessages.addMessage("Bei einzelnen Mehrfachbindungen ist ein -a nach dem Zahlwort überflüssig (z.B.: Prop 1 en)");
                return;
            }
        }
        if (greekNumber_en.getValue() == 0) {
            if (double_bonds.size() > 1) {
                //No Greek Syllable or Wrong Greek Syllable name
                ErrorMessages.addMessage("Die multiplizierende Vorsilbe ist nicht korrekt");
                return;
            }
        } else if (double_bonds.size() != greekNumber_en.getValue()) {
            //No Greek Syllable or Wrong Greek Syllable name
            ErrorMessages.addMessage("Die multiplizierende Vorsilbe ist nicht korrekt");
            return;
        }
        if (greekNumber_in.getValue() == 0) {
            if (triple_bonds.size() > 1) {
                //No Greek Syllable or Wrong Greek Syllable name
                ErrorMessages.addMessage("Die multiplizierende Vorsilbe ist nicht korrekt");
                return;
            }
        } else if (triple_bonds.size() != greekNumber_in.getValue()) {
            //No Greek Syllable or Wrong Greek Syllable name
            ErrorMessages.addMessage("Die multiplizierende Vorsilbe ist nicht korrekt");
            return;
        }
        //Check if there are wrong positions and if every C Atom only got a max amount of 4 bonds

    }

    private void calc_bonds_per_carbon() {
        for (int i = 1; i <= hydroCarbon.getValue(); i++) {
            if (hydroCarbon.getValue() == 1) {
                bonds_per_carbon.add(0);
                break;
            } else if (i == 1) {
                if (double_bonds.contains(i)) {
                    bonds_per_carbon.add(2);
                } else if (triple_bonds.contains(i)) {
                    bonds_per_carbon.add(3);
                } else {
                    bonds_per_carbon.add(1);
                }

            } else if (i == hydroCarbon.getValue()) {
                if (double_bonds.contains(i - 1)) {
                    bonds_per_carbon.add(2);
                } else if (triple_bonds.contains(i - 1)) {
                    bonds_per_carbon.add(3);
                } else {
                    bonds_per_carbon.add(1);
                }
            } else {
                int bonds = 0;
                if (double_bonds.contains(i - 1)) {
                    bonds += 2;
                } else if (triple_bonds.contains(i - 1)) {
                    bonds += 3;
                } else {
                    bonds += 1;
                }
                if (double_bonds.contains(i)) {
                    bonds += 2;
                } else if (triple_bonds.contains(i)) {
                    bonds += 3;
                } else {
                    bonds += 1;
                }

                if (bonds > 4) {
                    //the user has given a wrong bond position
                    //e.g. Hexa 2,3 diin
                    ErrorMessages.addMessage("Die Positionen der Mehrfachbindungen sind nicht korrekt");
                    return;
                }
                bonds_per_carbon.add(bonds);
            }

        }
    }

    @Override
    public String toString() {
        return "MainChain{" +
                "hydroCarbon=" + hydroCarbon +
                ", greekNumber_en=" + greekNumber_en +
                ", greekNumber_in=" + greekNumber_in +
                ", double_bonds=" + double_bonds +
                ", triple_bonds=" + triple_bonds +
                ", bonds_per_carbon=" + bonds_per_carbon +
                ", name='" + name + '\'' +
                ", ending_an='" + ending_an + '\'' +
                ", ending_en='" + ending_en + '\'' +
                ", ending_in='" + ending_in + '\'' +
                ", multibond_positions_en='" + multibond_positions_en + '\'' +
                ", multibond_positions_in='" + multibond_positions_in + '\'' +
                ", greek_syllable_en='" + greek_syllable_en + '\'' +
                ", greek_syllable_in='" + greek_syllable_in + '\'' +
                ", multibond_char='" + multibond_char + '\'' +
                '}';
    }
}
