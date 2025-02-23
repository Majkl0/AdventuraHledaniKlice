module cz.vse.adventura {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens cz.vse.adventura.main to javafx.fxml;
    opens cz.vse.adventura to javafx.fxml;

    exports cz.vse.adventura.main;
    exports cz.vse.adventura;
}
