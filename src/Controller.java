import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {
    protected Stage stage;
    protected double xOffset = 0;
    protected double yOffset = 0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startWindowDrag(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void dragWindow(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void close() {
        stage.close();
    }
}
