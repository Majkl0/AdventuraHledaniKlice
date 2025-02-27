package cz.vse.adventura.main;

import cz.vse.adventura.logika.Hra;
import cz.vse.adventura.logika.IHra;
import cz.vse.adventura.logika.Prostor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Hlavní kontroler pro GUI hry.
 * Zajišťuje propojení logiky hry s uživatelským rozhraním.
 */
public class HomeController implements Pozorovatel {

    // Konstanty pro souřadnice prostorů
    private static final class ProstorSouradnice {
        static final Point2D CHODBA = new Point2D(251, 126);
        static final Point2D LOZNICE = new Point2D(159, 34);
        static final Point2D KUCHYN = new Point2D(416, 57);
        static final Point2D OBYVAK = new Point2D(288, 262);
    }

    @FXML private ImageView hrac;
    @FXML private ListView<Prostor> panelVychodu;
    @FXML private Button tlacitkoOdesli;
    @FXML private TextArea vystup;
    @FXML private TextField vstup;

    private IHra hra;
    private final ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();
    private final Map<String, Point2D> souradniceProstoru = new HashMap<>();

    /**
     * Implementace rozhraní Pozorovatel.
     * Prázdná, protože aktualizace jsou řešeny pomocí lambda výrazů.
     */
    @Override
    public void aktualizuj() {
        // Implementace není potřeba - využíváme lambda výrazy
    }

    /**
     * Inicializace kontroleru.
     * Volá se automaticky po načtení FXML.
     */
    @FXML
    private void initialize() {
        hra = new Hra();
        vystup.appendText(hra.vratUvitani() + "\n\n");

        inicializujSouradniceProstoru();

        Platform.runLater(() -> {
            vstup.requestFocus();
            panelVychodu.setItems(seznamVychodu);

            hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {
                aktualizujSeznamVychodu();
                aktualizujPolohuHrace();
            });

            hra.registruj(ZmenaHry.KONEC_HRY, this::aktualizujKonecHry);
            aktualizujSeznamVychodu();
        });
    }

    /**
     * Inicializuje mapu souřadnic jednotlivých prostorů.
     */
    private void inicializujSouradniceProstoru() {
        souradniceProstoru.put("Chodba", ProstorSouradnice.CHODBA);
        souradniceProstoru.put("Ložnice", ProstorSouradnice.LOZNICE);
        souradniceProstoru.put("Kuchyň", ProstorSouradnice.KUCHYN);
        souradniceProstoru.put("Obývák", ProstorSouradnice.OBYVAK);
    }

    /**
     * Aktualizuje seznam východů z aktuální místnosti.
     */
    @FXML
    private void aktualizujSeznamVychodu() {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getSeznamVychodu());
        panelVychodu.setDisable(false);
    }

    /**
     * Aktualizuje pozici hráče na mapě.
     */
    private void aktualizujPolohuHrace() {
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        Point2D pozice = souradniceProstoru.get(prostor);
        if (pozice != null) {
            hrac.setLayoutX(pozice.getX());
            hrac.setLayoutY(pozice.getY());
        }
    }

    /**
     * Zpracuje vstup od uživatele po stisku tlačítka.
     */
    @FXML
    private void odesliVstup(ActionEvent event) {
        String prikaz = vstup.getText();
        vstup.clear();
        zpracujPrikaz(prikaz);
    }

    /**
     * Zpracuje příkaz a zobrazí výsledek.
     */
    private void zpracujPrikaz(String prikaz) {
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek + "\n\n");
    }

    /**
     * Zobrazí dialog pro potvrzení ukončení hry.
     */
    public void ukoncitHru(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Skutečně si přejete ukončit hru?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Aktualizuje stav GUI při konci hry.
     */
    public void aktualizujKonecHry() {
        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
        }
        boolean jeKonec = hra.konecHry();
        vstup.setDisable(jeKonec);
        tlacitkoOdesli.setDisable(jeKonec);
        panelVychodu.setDisable(jeKonec);
    }

    /**
     * Zpracuje kliknutí na panel východů.
     */
    @FXML
    private void klikPanelVychodu(MouseEvent event) {
        try {
            Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
            if (cil != null) {
                zpracujPrikaz("Jdi " + cil.getNazev());
            }
        } catch (RuntimeException e) {
            vystup.appendText("Chyba při zpracování příkazu: " + e.getMessage() + "\n");
        }
    }
}