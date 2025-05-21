module at.szybbs.strukturformelzeichner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;

    opens at.szybbs.strukturformelzeichner to javafx.fxml;
    exports at.szybbs.strukturformelzeichner;
}
