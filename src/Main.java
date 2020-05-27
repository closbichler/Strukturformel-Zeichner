import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ressources/pages/page0.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(getClass().getResource("ressources/styles/style.css").toExternalForm());

        primaryStage.setTitle("Strukturformel-Zeichner");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResource("ressources/images/taskbar-icon.png").toExternalForm()));

        MainController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.readFiles();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
