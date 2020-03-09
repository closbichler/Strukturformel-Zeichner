import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Controller {
    @FXML
    Canvas kekvas;

    public void test(){
        GraphicsContext gc = kekvas.getGraphicsContext2D();

        gc.fillRect(10,10,10,10);


    }
}
