package cz.vse.adventura.main;

import cz.vse.adventura.logika.Hra;
import cz.vse.adventura.logika.IHra;
import cz.vse.adventura.logika.Prostor;
import cz.vse.adventura.logika.Vec;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import cz.vse.adventura.logika.Kapsa;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Hlavní kontroler pro GUI hry.
 * Zajišťuje propojení logiky hry s uživatelským rozhraním.
 */
public class HomeController implements Pozorovatel {

    @FXML
private void napovedaKlik(ActionEvent actionEvent) {
    Stage napovedaStage = new Stage();
    WebView wv = new WebView();
    Scene napovedaScena = new Scene(wv);
    napovedaStage.setScene(napovedaScena);
    napovedaStage.show();
    wv.getEngine().load(getClass().getResource("/cz.vse.adventura/main/napoveda.html").toExternalForm());

    }

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
    @FXML private ListView<Vec> veciVMistnosti;
    @FXML private TitledPane veciVMistnostiPane;
    @FXML private ListView<Vec> veciVKapse;
    @FXML private TitledPane inicializujKapsa;
    private Timeline updateTimeline;


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
    // Inicializace hlavní herní logiky
    hra = new Hra();
    Vec.setHra((Hra) hra);
    vystup.appendText(hra.vratUvitani() + "\n\n");

    // Nastavení viditelnosti TitledPane pro věci v místnosti
    veciVMistnostiPane.setVisible(false);

    // Inicializace souřadnic prostorů pro mapu
    inicializujSouradniceProstoru();

    // Nastavení GUI komponent
    Platform.runLater(() -> {
        vstup.requestFocus();
        panelVychodu.setItems(seznamVychodu);

        // Registrace observerů pro změny ve hře
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {
            aktualizujSeznamVychodu();
            aktualizujPolohuHrace();
            aktualizujVeciVMistnosti();
        });

        hra.registruj(ZmenaHry.KONEC_HRY, () -> aktualizujKonecHry());


        // Počáteční aktualizace GUI
        aktualizujSeznamVychodu();
        aktualizujPolohuHrace();

        veciVKapse.setOnMouseClicked(this::inventarKlik);
    });

    // V metodě initialize()
veciVKapse.setItems(FXCollections.observableArrayList());
hra.registruj(ZmenaHry.ZMENA_INVENTARE, () -> {
    aktualizujKapsa();
});

    veciVMistnosti.setItems(FXCollections.observableArrayList());
    veciVMistnosti.setCellFactory(param -> new ListCell<Vec>() {
    @Override
    protected void updateItem(Vec vec, boolean empty) {
        super.updateItem(vec, empty);
        if (empty || vec == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(vec.getNazev());
            // Přidání obrázku
            ImageView obrazek = new ImageView(new Image(
                getClass().getResource("/cz.vse.adventura/main/prostory/veci/" + vec.getNazev() + ".jpg").toExternalForm()
            ));
            obrazek.setFitHeight(20);
            obrazek.setFitWidth(20);
            setGraphic(obrazek);
        }
    }

});

    veciVKapse.setCellFactory(param -> new ListCell<Vec>() {
        @Override
        protected void updateItem(Vec vec, boolean empty) {
            super.updateItem(vec, empty);
            if (empty || vec == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(vec.getNazev());
                try {
                    ImageView obrazek = new ImageView(new Image(
                            getClass().getResource("/cz.vse.adventura/main/prostory/veci/" +
                                    vec.getNazev().toLowerCase().replace(" ", "_") + ".jpg").toExternalForm()
                    ));
                    obrazek.setFitHeight(40);
                    obrazek.setFitWidth(40);
                    setGraphic(obrazek);
                } catch (Exception e) {
                    setGraphic(null);
                }
            }
        }
    });

    // Nastavení viditelnosti inventáře
    inicializujKapsa.setVisible(true);
    veciVKapse.setItems(Vec.getSeznamVeci());

    updateTimeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
        aktualizujVeciVMistnosti();
        aktualizujKapsa();
    }));
    updateTimeline.setCycleCount(Timeline.INDEFINITE);
    updateTimeline.play();

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

        // Upravíme tuto část
        Prostor aktualniProstor = hra.getHerniPlan().getAktualniProstor();
        veciVMistnostiPane.setVisible(aktualniProstor.bylProzkoumany);
        if (aktualniProstor.bylProzkoumany) {
            aktualizujVeciVMistnosti();
        } else {
            veciVMistnosti.getItems().clear();
        }
    }

    private void aktualizujPolohuHrace() {
        if (hrac != null) {  // Přidaná null kontrola
            String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
            Point2D pozice = souradniceProstoru.get(prostor);
            if (pozice != null) {
                hrac.setLayoutX(pozice.getX());
                hrac.setLayoutY(pozice.getY());
            }
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
    @FXML
    private void prozkoumatMistnost(ActionEvent event) {
        String prikaz = "Prozkoumej";
        zpracujPrikaz(prikaz);

        veciVMistnostiPane.setVisible(true);
        aktualizujVeciVMistnosti();


        // Aktualizace ListView s věcmi v místnosti
        ObservableList<Vec> veciList = FXCollections.observableArrayList();
        Map<String, Vec> veci = hra.getHerniPlan().getAktualniProstor().getVeci();
        veciList.addAll(veci.values());
        veciVMistnosti.setItems(veciList);

        // Přidejte CellFactory pro vlastní zobrazení položek
        veciVMistnosti.setCellFactory(param -> new ListCell<Vec>() {
            @Override
            protected void updateItem(Vec vec, boolean empty) {
                super.updateItem(vec, empty);
                if (empty || vec == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(vec.getNazev());
                    try {
                        String imagePath = vec.getObrazek();
                        ImageView obrazek = new ImageView(new Image(
                                getClass().getResource(vec.getObrazek()).toExternalForm()));
                        obrazek.setFitWidth(40);
                        obrazek.setFitHeight(40);
                        setGraphic(obrazek);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });
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
    private void aktualizujVeciVMistnosti() {
        ObservableList<Vec> veciList = FXCollections.observableArrayList();
        if (hra.getHerniPlan().getAktualniProstor().bylProzkoumany) {
            Map<String, Vec> veci = hra.getHerniPlan().getAktualniProstor().getVeci();
            veciList.addAll(veci.values());
        }
        veciVMistnosti.setItems(veciList);
    }

    @FXML
    private void vemVec(MouseEvent event) {
        ListView<Vec> sourceList = (ListView<Vec>) event.getSource();
        Vec vybranaVec = sourceList.getSelectionModel().getSelectedItem();

        if (vybranaVec != null) {
            String prikaz = "Vezmi " + vybranaVec.getNazev();
            zpracujPrikaz(prikaz);
            aktualizujVeciVMistnosti();
            aktualizujKapsa();
        }
    }

    private void aktualizujKapsa() {
        if (hra.getHerniPlan().getKapsa() != null) {
            Platform.runLater(() -> {
                veciVKapse.setItems(null);
                veciVKapse.setItems(hra.getHerniPlan().getKapsa().getVeci());
            });
        }
    }
    // In HomeController.java
    @FXML
    private void inventarKlik(MouseEvent event) {
        Vec selectedItem = veciVKapse.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Prostor currentRoom = hra.getHerniPlan().getAktualniProstor();

            // Check for special cases with keys
            if (selectedItem.getNazev().equals("Klíč od skříně") &&
                    currentRoom.najdiVec("Zamčená skříň") != null) {
                zpracujPrikaz("Použij Klíč od skříně Zamčená skříň");
            } else if (selectedItem.getNazev().equals("Klíč od dveří") &&
                    currentRoom.najdiVec("Zamčené dveře") != null) {
                zpracujPrikaz("Použij Klíč od dveří Zamčené dveře");
            } else {
                // Default case - drop item
                zpracujPrikaz("Vyhoď " + selectedItem.getNazev());
            }
        }
    }
}


