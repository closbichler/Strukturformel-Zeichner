import com.sun.webkit.Timer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model {
    MainChain mainChain;
    ArrayList<SideChain> sideChains;

    boolean calculate(String input) {
        mainChain = new MainChain();
        sideChains = new ArrayList<>();
        Pattern p = Pattern.compile("-?[\\d,]+-?\\w+-?\\w+yl");
        Matcher m = p.matcher(input);

        int end = 0;
        while (m.find()) {
            System.out.println(m.group());
            sideChains.add(new SideChain(input.substring(m.start(), m.end())));
            end = m.end();
        }
        mainChain.regex(input.substring(end));

        System.out.println(this);

        return true;
    }

    @Override
    public String toString() {
        return "Model{" +
                "mainChain=" + mainChain +
                ", sideChains=" + sideChains +
                '}';
    }
}
