import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SideChain {
    //Stores carbon count and multibond count
    public HydroCarbons carbon_count;
    public GreekNumbers multibond_count;

    ArrayList<Integer> positions = new ArrayList<>();

    SideChain(String input) {
        regex(input);
    }

    private boolean regex(String input) {
        //Stores the input String, the regex, the name (e.g. Prop)
        String regex = "^-?([\\d,]+)-?((\\w*)-)?(\\w+)yl$";
        String name;

        //Stores the multiple bond positions e.g. 3,4
        String position_string;
        //Stores the greek syllable for the multi bonds e.g. di
        String greek_syllable;

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        if (m.find()) {
            position_string = m.group(1);
            greek_syllable = m.group(3);
            name = m.group(4);

            calc_positions(position_string);
            //If any of those methods detects a mistake it will return false
            //System.out.println(this);
            return calc_enums(name, greek_syllable);


        }
        return false;
    }

    void calc_positions(String position_string) {
        String[] position = position_string.split(",");

        for (String s : position) {
            positions.add(Integer.parseInt(s));
        }

    }

    boolean calc_enums(String name, String greek_syllable) {
        try {
            carbon_count = HydroCarbons.valueOf(name.toLowerCase());
        } catch (Exception e) {

            //No HydroCarbon or Wrong HydroCarbon name
            carbon_count = HydroCarbons.none;
            System.out.println("Wrong HydroCarbon name!");
            return false;
        }

        if (positions.size() >= 2) {
            try {
                multibond_count = GreekNumbers.valueOf(greek_syllable.toLowerCase());
            } catch (Exception e) {
                multibond_count = GreekNumbers.none;
                //No Greek Syllable or Wrong Greek Syllable name
                System.out.println("Wrong or no Greek Syllable!");
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nSideChain{" +
                "carbon_count=" + carbon_count +
                ", multibond_count=" + multibond_count +
                ", positions=" + positions +
                '}';
    }
}
