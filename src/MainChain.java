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

    MainChain(){

        regex = "\\s(\\w{3,6}a?(\\s\\-(\\d[\\d,]*)\\-\\s)?[aei]n)$";

        double_bondings = new ArrayList<>();
        triple_bondings = new ArrayList<>();
    }

    boolean regex(String input){
        this.input = input;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.input);
        name = m.group(1);
        ending = m.group(3);
        //name = m.group(1);
        System.out.println(this);
        return m.find();
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
                '}';
    }
}
