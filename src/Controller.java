import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    Model model = new Model();
    @FXML
    protected TextField text;

    @FXML
    protected Button but;

    @FXML
    protected TextArea errors;

    @FXML
    public void input(){

        model.calculate(text.getText());


    }




}
