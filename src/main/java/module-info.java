module cz.vse.adventura {
    requires javafx.controls;
    requires javafx.fxml;

    exports cz.vse.adventura.main;
    opens cz.vse.adventura.main to javafx.fxml;
}