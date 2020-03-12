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

    //Stores the position of the double and triple bonds
    public ArrayList<Integer> double_bonds = new ArrayList<>();
    public ArrayList<Integer> triple_bonds = new ArrayList<>();

    //String regex1 = "\\s?([A-Z]?[b-z]{3,10})(a)?(\\s?-?(\\d[\\d,]*)-?\\s?)?([a-z]{2,})?([ai]n)$";
    String name;
    String ending_an;
    String ending_en;
    String ending_in;

    //Stores the multiple bond positions e.g. 3,4
    String multibond_positions_en;
    String multibond_positions_in;
    //Stores the greek syllable for the multi bonds e.g. di
    String greek_syllable_en;
    String greek_syllable_in;
    //a or ""
    String multibond_char;

    //Takes the input and turns calculates the output values
    //Returns if the input was correct or if there were any mistakes
    boolean regex(String input) {
        //Stores the input String, the regex, the name (e.g. Prop) and the ending (e.g. an)
        String regex = "\\s?([A-Z]?[b-z]{2,})(a)?" +
                "((an)|(\\s?-?(\\d(,\\d)*)-?\\s?([a-z]{2,})?(en))?" +
                "(\\s?-?(\\d(,\\d)*)-?\\s?([a-z]{2,})?(in))?)";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        if (m.find()) {

            name = m.group(1);
            multibond_char = m.group(2);
            ending_an = m.group(4);
            multibond_positions_en = m.group(6);
            greek_syllable_en = m.group(8);
            multibond_positions_in = m.group(11);
            greek_syllable_in = m.group(13);

            if (ending_an != null) {
                calc_multiple_bondings(name, ending_an, multibond_positions_en);
                calc_multiple_bondings(name, ending_an, multibond_positions_in);
                calc_enums(name, greek_syllable_en, greek_syllable_in);
            } else {
                if (ending_en != null) {
                    calc_multiple_bondings(name, ending_en, multibond_positions_en);
                    calc_enums(name, greek_syllable_en, greek_syllable_in);
                }
                if (ending_in != null) {
                    calc_multiple_bondings(name, ending_in, multibond_positions_in);
                    calc_enums(name, greek_syllable_en, greek_syllable_in);
                }

            }
            System.out.println(this);


            //If any of those methods detects a mistake it will return false
            System.out.println(this);
            return false;
        }
        System.out.println(this);
        return true;

    }

    //Calculates the multibonds with the given input
    //Returns if the input was correct
    private boolean calc_multiple_bondings(String name, String ending, String multibond_positions) {

        if ((ending.equals("en") || ending.equals("in")) && name.equals("Meth")) {
            //Meth syllable with en or in
            System.out.println("Meth" + ending + " doesn't exist!");
            return false;
        }
        if (ending.equals("en") || ending.equals("in")) {
            String[] pos_string = multibond_positions.split(",");
            for (String s : pos_string) {
                if (ending.equals("en") && !double_bonds.contains(Integer.parseInt(s))) {
                    double_bonds.add(Integer.parseInt(s));

                } else if (ending.equals("in") && !triple_bonds.contains(Integer.parseInt(s))) {
                    triple_bonds.add(Integer.parseInt(s));

                } else {
                    //Duplicate Position
                    System.out.println("Duplicate Position of multibonds");
                    return false;
                }
            }
            if (pos_string.length == 0) {
                //Ending en but no position given
                System.out.println("No given Positions for \"" + ending + "\"!");
                return false;
            }
        } else if (ending.equals("an")) {
            if (multibond_positions != null && !multibond_positions.equals("")) {
                //Ending "an" but given multibond position
                System.out.println("An Alkane cannot have a multibond position!");
                return false;
            }
        }
        //Either all positions given or no multibond and ending an
        return true;
    }

    //Creates the enum values from the input name and the greek_syllable
    //Returns if the transition was successfull
    private boolean calc_enums(String name, String greek_syllable_en, String greek_syllable_in) {
        try {
            hydroCarbon = HydroCarbons.valueOf(name.toLowerCase());
        } catch (Exception e) {
            //No HydroCarbon or Wrong HydroCarbon name
            hydroCarbon = HydroCarbons.none;
            System.out.println("Wrong HydroCarbon name!");
            return false;
        }

        if (double_bonds.size() >= 2) {
            try {
                greekNumber_en = GreekNumbers.valueOf(greek_syllable_en.toLowerCase());
            } catch (Exception e) {
                greekNumber_en = GreekNumbers.none;
                //No Greek Syllable or Wrong Greek Syllable name
                System.out.println("Wrong or no Greek Syllable!");
                return false;
            }

            if (double_bonds.size() != greekNumber_en.getValue()) {
                //No Greek Syllable or Wrong Greek Syllable name
                System.out.println("Wrong or no Greek Syllable!");
                return false;
            }
        } else if (triple_bonds.size() >= 2) {
            try {
                greekNumber_in = GreekNumbers.valueOf(greek_syllable_in.toLowerCase());
            } catch (Exception e) {
                greekNumber_in = GreekNumbers.none;
                //No Greek Syllable or Wrong Greek Syllable name
                System.out.println("Wrong or no Greek Syllable!");
                return false;
            }
            if (triple_bonds.size() != greekNumber_in.getValue()) {
                //No Greek Syllable or Wrong Greek Syllable name
                System.out.println("Wrong or no Greek Syllable!");
                return false;
            }
        } else {
            if (greek_syllable_en != null || greek_syllable_in != null) {
                //Greek_syllable even tho the ending is an
                System.out.println();
            }
            greekNumber_en = GreekNumbers.none;
            greekNumber_in = GreekNumbers.none;
        }
        return true;
    }

    //Validates the input
    //Returns if the input was correct
    private boolean validate_input(String multibond_char, String greek_syllable) {
        boolean correct_input = true;

        if (double_bonds.size() >= 2) {
            if (double_bonds.size() != greekNumber_en.getValue()) {
                //The amount of multibond_positions of multiple bonds doesn't match with the greek syllable
                //e.g. Propa2,3en
                System.out.println("Wrong greek syllable!");
                correct_input = false;
            }
            if (multibond_char == null) {
                //missing a after carbon counting syllable
                //e.g Prop2,3dien
                System.out.println("Missing a after carbon counting syllable");
                correct_input = false;
            }

        } else if (triple_bonds.size() >= 2) {
            if (triple_bonds.size() != greekNumber_in.getValue()) {
                //The amount of multibond_positions of multiple bonds don't match with the greek syllable
                //e.g. Propa2,3in
                System.out.print("Wrong greek syllable!");
                correct_input = false;
            }
            if (!multibond_char.equals("a")) {
                //missing a after carbon counting syllable
                //e.g Prop2,3diin
                System.out.println("Missing a after carbon counting syllable");
                correct_input = false;
            }

        }
        if (double_bonds.size() >= 1) {
            for (Integer double_bond : double_bonds) {
                if (double_bond <= 0 || double_bond >= hydroCarbon.getValue()) {
                    //Illegal Positions
                    System.out.println("Wrong Positions [1 - " + (hydroCarbon.getValue() - 1) + "] would be correct");
                    correct_input = false;
                    break;
                }
            }
        } else if (triple_bonds.size() >= 1) {
            for (Integer triple_bonds : triple_bonds) {
                if (triple_bonds <= 0 || triple_bonds >= hydroCarbon.getValue()) {
                    //Illegal Positions
                    System.out.println("Wrong Positions [1 - " + (hydroCarbon.getValue() - 1) + "] would be correct");
                    correct_input = false;
                    break;
                }
            }
        } else {
            if (multibond_char != null && multibond_char.equals("a")) {
                //Incorrect a after Carbon syllable
                //e.g. Propa1en
                System.out.println("Incorrect a after carbon counting syllable");
                correct_input = false;
            }
            if (greek_syllable != null) {
                //Incorrect greek number syllable after carbon counting syllable
                //e.g. Prop1dien
                System.out.println("Incorrect greek number syllable after carbon counting syllable");
                correct_input = false;
            }

        }

        return correct_input;
    }

    @Override
    public String toString() {
        return "MainChain{" +
                "name='" + name + '\'' +
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
