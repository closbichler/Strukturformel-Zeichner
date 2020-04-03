import java.util.ArrayList;
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
    public String errors = "";

    //Stores the position of the double and triple bonds
    public ArrayList<Integer> double_bonds = new ArrayList<>();
    public ArrayList<Integer> triple_bonds = new ArrayList<>();
    public ArrayList<Integer> bonds_per_carbon = new ArrayList<>();

    //String regex1 = "\\s?([A-Z]?[b-z]{3,10})(a)?(\\s?-?(\\d[\\d,]*)-?\\s?)?([a-z]{2,})?([ai]n)$";
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

    MainChain(String input, boolean sideChain) {
        regex(input, sideChain);
    }

    MainChain(String input) {
        regex(input, false);
    }

    String regex = "^-?([a-z]{2,})" +
            "((an)|("+ //4
            "(a)?(-?(\\d(,\\d)*)?-?([a-z]{2,})?(en))|"+ //10
            "(a)?(-?(\\d(,\\d)*)?-?([a-z]{2,})?(en))?"+ //16
            "(-?(\\d(,\\d)*)?-?([a-z]{2,})?(in))"+ //21
            "))$";

    //Takes the input and turns calculates the output values
    //Returns if the input was correct or if there were any mistakes3e
    private void regex(String input, boolean sideChain) {
        //Stores the input String, the regex, the name (e.g. Prop) and the ending (e.g. an)
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
                        "(a)?(-?(\\d(,\\d)*)?-?([a-z]{2,})?(en))|" + //10
                        "(a)?(-?(\\d(,\\d)*)?-?([a-z]{2,})?(en))?" + //16
                        "(-?(\\d(,\\d)*)?-?([a-z]{2,})?(in))" + //21
                        "))$");
                m = p.matcher(input.substring(i));
                if(!m.find()){
                    errors += "Falsche Eingabe!";
                    return;
                }
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
                calc_enums(name, greek_syllable_en, greek_syllable_in);
                validate_input(multibond_char, ending_an);
            } else {

                if (ending_en != null) {
                    calc_multiple_bondings(name, ending_en, multibond_positions_en);
                    calc_enums(name, greek_syllable_en, greek_syllable_in);
                    findPositions(ending_en, ending_in);
                    validate_input(multibond_char, ending_en);
                }
                if (ending_in != null) {
                    calc_multiple_bondings(name, ending_in, multibond_positions_in);
                    calc_enums(name, greek_syllable_en, greek_syllable_in);
                    findPositions(ending_en, ending_in);
                    validate_input(multibond_char, ending_in);
                }
                if (ending_en == null && ending_in == null && ending_an == null) {
                    //No ending
                    errors += "\nMissing ending [an, en, in]";
                }

            }

        } else {
            errors += "Falsche Eingabe!";
        }


    }

    private boolean findName() {

        for (i = 2; i < name.length(); i++) {
            try {
                HydroCarbons.valueOf(name.substring(0, i));
                name = name.substring(0,i);
                break;
            } catch (Exception e) {

            }
        }
        return i == name.length() - 1;
    }

    private void findPositions(String ending_en, String ending_in) {
        if (ending_en != null && ending_en.equals("en") && double_bonds.size() == 0) {
            System.out.println(hydroCarbon + " " + greekNumber_en);
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
            errors += "\nMeth" + ending + " doesn't exist!";
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
                    errors += "\nDuplicate Position of multibonds";
                    return;
                }
            }

        } else if (ending.equals("an")) {
            if (multibond_positions != null && !multibond_positions.equals("")) {
                //Ending "an" but given multibond position
                errors += "\nAn Alkane cannot have a multibond position!";
                return;
            }
        }
    }

    //Creates the enum values from the input name and the greek_syllable
    //Returns if the transition was successfull
    private void calc_enums(String name, String greek_syllable_en, String greek_syllable_in) {

        //Setting hydroCarbon to the given Value
        try {
            hydroCarbon = HydroCarbons.valueOf(name);
        } catch (Exception e) {
            //No HydroCarbon or Wrong HydroCarbon name
            hydroCarbon = HydroCarbons.none;
            errors += "\nWrong HydroCarbon name!";
            return;
        }

        //setting greekNumber to the given syllable e.g. di
        if (greek_syllable_en != null) {
            try {
                greekNumber_en = GreekNumbers.valueOf(greek_syllable_en);
            } catch (Exception e) {
                greekNumber_en = GreekNumbers.none;
                //No Greek Syllable or Wrong Greek Syllable name
                errors += "\nWrong or no Greek Syllable!";
                return;
            }


        }
        if (greek_syllable_in != null) {
            try {
                greekNumber_in = GreekNumbers.valueOf(greek_syllable_in);
            } catch (Exception e) {
                greekNumber_in = GreekNumbers.none;
                //No Greek Syllable or Wrong Greek Syllable name
                errors += "\nWrong or no Greek Syllable!";
                return;
            }

        }
        if (greek_syllable_en == null && greek_syllable_in == null) {
            if (greek_syllable_en != null && greek_syllable_en.equals("an")) {
                //Greek_syllable even tho the ending is an
                errors += "\nGreeksyllable is not needed for an Alkane";
            }
            greekNumber_en = GreekNumbers.none;
            greekNumber_in = GreekNumbers.none;
        }
        if (greekNumber_en == null) {
            greekNumber_en = GreekNumbers.none;
        }
        if (greekNumber_in == null) {
            greekNumber_in = GreekNumbers.none;
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
                errors += "\nMissing a after carbon counting syllable";
                return;
            }
        }
        if (ending.equals("en") && double_bonds.size() == 0) {
            //Ending en or in but no position given
            errors += "\nNo given Positions for \"" + ending + "\"!";
            return;
        } else if (ending.equals("in") && triple_bonds.size() == 0) {
            //Ending en or in but no position given
            errors += "\nNo given Positions for \"" + ending + "\"!";
            return;
        }
        //Checking the positions of the multibonds
        if (double_bonds.size() >= 1 || triple_bonds.size() >= 1) {
            for (Integer double_bond : double_bonds) {
                if (double_bond <= 0 || double_bond >= hydroCarbon.getValue()) {
                    //Illegal Positions
                    errors += "\nWrong Positions [1 - " + (hydroCarbon.getValue() - 1) + "] would be correct";
                    return;
                }
            }

            for (Integer triple_bond : triple_bonds) {
                if (triple_bond <= 0 || triple_bond >= hydroCarbon.getValue()) {
                    //Illegal Positions
                    errors += "\nWrong Positions [1 - " + (hydroCarbon.getValue() - 1) + "] would be correct";
                    return;
                }
            }
        } else {
            if (multibond_char != null && multibond_char.equals("a")) {
                //Incorrect a after Carbon syllable
                //e.g. Propa1en
                errors += "\nIncorrect a after carbon counting syllable";
                return;
            }
        }
        if (greekNumber_en.getValue() == 0) {
            if (double_bonds.size() > 1) {
                //No Greek Syllable or Wrong Greek Syllable name
                errors += "\nWrong or no Greek Syllable!";
                return;
            }
        } else if (double_bonds.size() != greekNumber_en.getValue()) {
            //No Greek Syllable or Wrong Greek Syllable name
            errors += "\nWrong or no Greek Syllable!";
            return;
        }
        if (greekNumber_in.getValue() == 0) {
            if (triple_bonds.size() > 1) {
                //No Greek Syllable or Wrong Greek Syllable name
                errors += "\nWrong or no Greek Syllable!";
                return;
            }
        } else if (triple_bonds.size() != greekNumber_in.getValue()) {
            //No Greek Syllable or Wrong Greek Syllable name
            errors += "\nWrong or no Greek Syllable!";
            return;
        }
        //Check if there are wrong positions and if every C Atom only got a max amount of 4 bonds
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
                    errors += "\nWrong bond positions [more than 4 bonds]  at" + i + ". C - Atom";
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
