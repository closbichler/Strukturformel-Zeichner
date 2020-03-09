import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Stores the MainChain of the HydroCarbon
//Simply create an object of it and call the method regex

//Import variables are the enums carbon_count, multibond_count, double_bonds and triple_bonds
public class MainChain {

    //Stores carbon count and multibond count
    public HydroCarbons carbon_count;
    public GreekNumbers multibond_count;

    //Stores the position of the double and triple bonds
    public ArrayList<Integer> double_bonds = new ArrayList<>();
    public ArrayList<Integer> triple_bonds = new ArrayList<>();

    //Stores the input String, the regex the name (e.g. Prop) and the ending (e.g. an)
    private String input = "";
    private String regex = "\\s?(\\w{3,4})(a?)(\\s?-?(\\d[\\d,]*)-?\\s?)?(\\w{2,})?([aei]n)$";
    private String name = "";
    private String ending = "";

    //Stores the multiple bond positions e.g. 3,4
    private String multibond_positions = "";

    //Stores the greek syllable for the multi bonds e.g. di
    private String greek_syllable = "";

    //a or ""
    private String multibond_char = "";

    //Takes the input and turns calculates the output values
    //Returns if the input was correct or if there were any mistakes
    boolean regex(String input) {
        this.input = input;

        boolean result;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.input);

        result = m.find();
        if (result) {
            name = m.group(1);
            multibond_char = m.group(2);
            multibond_positions = m.group(4);
            greek_syllable = m.group(5);
            ending = m.group(6);


            if (!calc_multiple_bondings()
                    || !calc_carbon_count()
                    || !validate_input()) {

                //If any of those methods detects a mistake it will return false
                result = false;
            }

            System.out.println(this);
        }

        return result;
    }

    //Validates the input
    //Returns if the input was correct
    private boolean validate_input() {
        boolean correct_input = true;

        if (double_bonds.size() >= 2) {
            if (double_bonds.size() != multibond_count.getValue()) {
                //The amount of multibond_positions of multiple bonds don't match with the greek syllable
                //e.g. Propa2,3en
                System.out.println("Wrong greek syllable!");
                correct_input = false;
            }
            if (!multibond_char.equals("a")) {
                //missing a after carbon counting syllable
                //e.g Prop2,3dien
                System.out.println("Missing a after carbon counting syllable");
                correct_input = false;
            }

        } else if (triple_bonds.size() >= 2) {
            if (triple_bonds.size() != multibond_count.getValue()) {
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

        } else {
            if (multibond_char.equals("a")) {
                //Incorrect a after Carbon syllable
                //e.g. Propa1en
                System.out.println("Incorrect a after carbon counting syllable");
                correct_input = false;
            }
            if (greek_syllable != null && !greek_syllable.equals("")) {
                //Incorrect greek number syllable after carbon counting syllable
                //e.g. Prop1dien
                System.out.println("Incorrect greek number syllable after carbon counting syllable");
                correct_input = false;
            }
        }

        return correct_input;
    }

    //Creates the enum values from the input name and the greek_syllable
    //Returns if the transition was successfull
    private boolean calc_carbon_count() {
        boolean result = true;
        try {
            carbon_count = HydroCarbons.valueOf(name);
        } catch (NullPointerException e) {
            //No HydroCarbon or Wrong HydroCarbon name
            System.out.println("Wrong HydroCarbon name!");
            result = false;
        }
        if (double_bonds.size() >= 2 && triple_bonds.size() >= 2) {
            try {
                multibond_count = GreekNumbers.valueOf(greek_syllable);
            } catch (NullPointerException e) {
                //No Greek Syllable or Wrong Greek Syllable name
                System.out.println("Wrong or no Greek Syllable!");
                result = false;
            }
        }
        return result;
    }

    //Calculates the multibonds with the given input
    //Returns if the input was correct
    private boolean calc_multiple_bondings() {
        boolean result = false;
        if (ending.equals("en")) {
            String[] pos_string = multibond_positions.split(",");
            for (String s : pos_string) {
                double_bonds.add(Integer.parseInt(s));
                result = true;
            }
            if (!result) {
                System.out.println("Wrong ending \"en\"!");
                //Ending en but no position given
                return false;
            }

        } else if (ending.equals("in")) {
            String[] pos_string = multibond_positions.split(",");
            for (String s : pos_string) {
                triple_bonds.add(Integer.parseInt(s));
                result = true;
            }

            if (!result) {
                System.out.println("Wrong ending \"in\"!");
                //Ending in but no position given
                return false;
            }
        }
        //Either all positions given or no multibond and ending an
        return true;
    }

    @Override
    public String toString() {
        return "MainChain{" +
                "carbon_count=" + carbon_count +
                ", multibond_count=" + multibond_count +
                ", double_bonds=" + double_bonds +
                ", triple_bonds=" + triple_bonds +
                ", input='" + input + '\'' +
                //", regex='" + regex + '\'' +
                ", name='" + name + '\'' +
                ", ending='" + ending + '\'' +
                ", multibond_positions='" + multibond_positions + '\'' +
                ", greek_syllable='" + greek_syllable + '\'' +
                ", multibond_char='" + multibond_char + '\'' +
                '}';
    }
}
