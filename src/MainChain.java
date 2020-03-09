import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainChain {
    String input;
    String regex;
    String name;
    String ending;
    int c_count;
    ArrayList<Integer> double_bondings;
    ArrayList<Integer> triple_bondings;
    String positions;

    MainChain() {

        regex = "\\s?(\\w{3,4}a?)(\\s?\\-?(\\d[\\d,]*)\\-?\\s?)?([aei]n)$";

        double_bondings = new ArrayList<>();
        triple_bondings = new ArrayList<>();
    }

    boolean regex(String input) {
        this.input = input;
        boolean result = true;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.input);

        result = m.find();
        if (result) {
            name = m.group(1);
            positions = m.group(3);
            ending = m.group(4);
            System.out.println(this);
        }

        return result;
    }

    @Override
    public String toString() {
        return "MainChain{" +
                "input='" + input + '\'' +
                ", regex='" + regex + '\'' +
                ", name='" + name + '\'' +
                ", ending='" + ending + '\'' +
                ", c_count=" + c_count +
                ", double_bondings=" + double_bondings +
                ", triple_bondings=" + triple_bondings +
                ", positions='" + positions + '\'' +
                '}';
    }
}
