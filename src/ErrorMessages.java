import java.util.ArrayList;

public class ErrorMessages {
    private static boolean undefErr = false;
    private static ArrayList<String> messages = new ArrayList<>();

    public static void throwUndifinedError() {
        undefErr = true;
    }

    public static void addMessage(String msg) {
        messages.add(msg);
    }

    public static String getMessages() {
        if(undefErr)
            return "Bitte überprüfen Sie Ihre Eingabe!";

        String out = "";
        for(String str : messages) {
            out += str + "\n";
        }
        return out;
    }
}
