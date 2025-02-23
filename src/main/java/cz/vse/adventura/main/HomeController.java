package cz.vse.adventura.main;

import cz.vse.adventura.logika.Hra;
import cz.vse.adventura.logika.IHra;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

    public class HomeController {
    @FXML
    private TextArea vystup;
    @FXML
    private TextField vstup;

    private IHra hra = new Hra();
    @FXML
    private void initialize() {
        vystup.appendText(hra.vratUvitani()+"\n\n");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vstup.requestFocus();

            }
        });
    }

@FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vystup.appendText("> "+prikaz+"\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek+"\n\n");
        vstup.clear();
}
}
