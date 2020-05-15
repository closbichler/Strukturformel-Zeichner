import java.util.ArrayList;

public class ErrorMessages {
    private static boolean undefErr = false;
    private static boolean internalErr = false;
    private static ArrayList<String> messages = new ArrayList<>();

    public static void throwUndefinedError() {
        undefErr = true;
    }

    public static void throwInternalError() { internalErr = true; }

    public static void addMessage(String msg) {
        messages.add(msg);
    }

    public static boolean anyErrorsThrown() {
        if(undefErr || internalErr || !messages.isEmpty())
            return true;
        return false;
    }

    public static String getMessages() {
        if(internalErr)
            return "Es gibt ein internes Problem!";
        if(undefErr)
            return "Bitte überprüfen Sie Ihre Eingabe!";

        String out = "";
        for(String str : messages) {
            out += str + "!\n";
        }
        return out;
    }

    public static String getFirst3Messages() {
        if(internalErr)
            return "Es gibt ein internes Problem!";
        if(undefErr)
            return "Bitte überprüfen Sie Ihre Eingabe!";

        String out = "";
        for(int i=0 ; i<messages.size() && i<=3 ; i++) {
            out += messages.get(i) + "!\n";
        }
        return out;
    }

    public static void clear() {
        messages.clear();
        undefErr = false;
        internalErr = false;
    }
}
