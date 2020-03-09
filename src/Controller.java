import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    protected TextField text;

    @FXML
    protected Button but;

    @FXML
    public void input(){
        System.out.println(text.getText());

    }




}
