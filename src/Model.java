import com.sun.webkit.Timer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model {
    MainChain mainChain;
    ArrayList<SideChain> sideChains;

    void calculate(String input) {
        sideChains = new ArrayList<>();
        Pattern p = Pattern.compile("(.*)yl\\)?");
        Matcher m = p.matcher(input);

        int end = 0;
        while (m.find()) {
            sideChains.add(new SideChain(input.substring(m.start(), m.end())));
            end = m.end();
        }
        mainChain = new MainChain(input.substring(end));

        System.out.println(this);

    }

    boolean validateChains(){

        return true;
    }
    @Override
    public String toString() {
        return "Model{" +
                "mainChain=" + mainChain + "," +
                "\n\t sideChains=" + sideChains +
                '}';
    }
}
