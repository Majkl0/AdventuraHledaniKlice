module cz.vse.adventura {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    exports cz.vse.adventura.main;
    opens cz.vse.adventura.main to javafx.fxml;
}