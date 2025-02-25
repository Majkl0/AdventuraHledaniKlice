package cz.vse.adventura.main;

import cz.vse.adventura.Prikazy.PrikazJdi;
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
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Optional;

public class HomeController implements Pozorovatel{
    @Override
    public void aktualizuj() {
        // Prázdná implementace, protože aktualizace jsou řešeny pomocí lambda výrazů
    }
    @FXML
    private ListView<Prostor> panelVychodu;
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
                hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI,() -> aktualizujSeznamVychodu());
                hra.registruj(ZmenaHry.KONEC_HRY,() -> aktualizujKonecHry());
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
        vstup.clear();
        zpracujPrikaz(prikaz);
    }

    private void zpracujPrikaz(String prikaz) {
        vystup.appendText("> "+ prikaz +"\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek+"\n\n");
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
      public void aktualizujKonecHry() {
            if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
            }
            vstup.setDisable(hra.konecHry());
            tlacitkoOdesli.setDisable(hra.konecHry());
            panelVychodu.setDisable(hra.konecHry());
        }


    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent) {
        try {
            Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
            if (cil != null) {
                String prikaz = "Jdi " + cil.getNazev();
                zpracujPrikaz(prikaz);
            }
        } catch (Exception e) {
            // Logování chyby
            System.err.println("Chyba při přechodu do prostoru: " + e.getMessage());
        }
    }


}
