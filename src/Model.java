import com.sun.webkit.Timer;

public class Model {
    String input;
    MainChain mainChain;

    Model(){
        mainChain = new MainChain();
    }

    boolean calculate(String input){
        this.input = input;
        return mainChain.regex(input);
    }

}
