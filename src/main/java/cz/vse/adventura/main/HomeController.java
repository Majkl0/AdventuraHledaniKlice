package cz.vse.adventura.main;

import cz.vse.adventura.logika.HerniPlan;
import cz.vse.adventura.logika.Hra;
import cz.vse.adventura.logika.IHra;
import cz.vse.adventura.logika.Prostor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class HomeController implements Pozorovatel{
    @FXML
    private ListView panelVychodu;
    @FXML
    private Button tlacitkoOdesli;
    @FXML
    private TextArea vystup;
    @FXML
    private TextField vstup;
    @FXML
    private IHra hra = new Hra();
    @FXML
    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();
    @FXML
    private void initialize() {
        vystup.appendText(hra.vratUvitani()+"\n\n");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vstup.requestFocus();
                panelVychodu.setItems(seznamVychodu);
                hra.getHerniPlan().registruj(HomeController.this);
                aktualizujSeznamVychodu();

            }
        });
    }

    @FXML
    private void aktualizujSeznamVychodu() {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getSeznamVychodu());
    }

    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vystup.appendText("> "+prikaz+"\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek+"\n\n");
        vstup.clear();

        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
            vstup.setDisable(true);
            tlacitkoOdesli.setDisable(true);

        }
}
        public void ukoncitHru(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Skutečně si přejete ukončit hru?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                Platform.exit();
        }

    /**
     *
     */
    @Override
    public void aktualizuj() {
    aktualizujSeznamVychodu();
    }
}
