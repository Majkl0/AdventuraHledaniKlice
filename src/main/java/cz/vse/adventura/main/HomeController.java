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
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HomeController implements Pozorovatel {

    @FXML private ImageView hrac;
    @FXML private ListView<Prostor> panelVychodu;
    @FXML private Button tlacitkoOdesli;
    @FXML private TextArea vystup;
    @FXML private TextField vstup;
    @FXML private ListView<Vec> veciVMistnosti;
    @FXML private TitledPane veciVMistnostiPane;
    @FXML private ListView<Vec> veciVKapse;
    @FXML private TitledPane inicializujKapsa;
    @FXML private Button tlacitkoProzkoumatMistnost;
    private Timeline updateTimeline;

    private IHra hra;
    private int clickCounter = 0;
    private boolean epilogPrinted = false;
    private final ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();
    private final Map<String, Point2D> souradniceProstoru = new HashMap<>();

    @Override
    public void aktualizuj() {
        // Not needed - using lambda expressions
    }

    @FXML
    private void initialize() {
        hra = new Hra();
        Vec.setHra((Hra) hra);
        vystup.appendText(hra.vratUvitani() + "\n\n");

        veciVMistnostiPane.setVisible(false);
        inicializujSouradniceProstoru();

        Platform.runLater(() -> {
            vstup.requestFocus();
            panelVychodu.setItems(seznamVychodu);
            hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, this::aktualizujProstredi);
            hra.registruj(ZmenaHry.KONEC_HRY, this::aktualizujKonecHry);
            hra.registruj(ZmenaHry.ZMENA_CASU, this::aktualizujCas);
            aktualizujProstredi();
            hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> aktualizujSeznamVychodu());
        });

        veciVKapse.setItems(FXCollections.observableArrayList());
        hra.registruj(ZmenaHry.ZMENA_INVENTARE, this::aktualizujKapsa);

        veciVMistnosti.setItems(FXCollections.observableArrayList());
        nastavCellFactory(veciVMistnosti);
        nastavCellFactory(veciVKapse);

        veciVKapse.setOnMouseClicked(this::inventarKlik);

        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
            aktualizujVeciVMistnosti();
            aktualizujKapsa();
        }));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    private void inicializujSouradniceProstoru() {
        souradniceProstoru.put("Chodba", new Point2D(251, 126));
        souradniceProstoru.put("Ložnice", new Point2D(159, 34));
        souradniceProstoru.put("Kuchyň", new Point2D(416, 57));
        souradniceProstoru.put("Obývák", new Point2D(288, 262));
    }

    private void nastavCellFactory(ListView<Vec> listView) {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Vec vec, boolean empty) {
                super.updateItem(vec, empty);
                if (empty || vec == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(vec.getNazev());
                    try {
                        String imageName = vec.getNazev().toLowerCase().replace(" ", "_") + ".jpg";
                        ImageView obrazek = new ImageView(new Image(
                                getClass().getResource("/cz.vse.adventura/main/prostory/veci/" + imageName).toExternalForm()
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
    }

    private void aktualizujProstredi() {
        aktualizujSeznamVychodu();
        aktualizujPolohuHrace();
        aktualizujVeciVMistnosti();
    }

    @FXML
    private void aktualizujSeznamVychodu() {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getSeznamVychodu());
        panelVychodu.setDisable(false);

        Prostor aktualniProstor = hra.getHerniPlan().getAktualniProstor();
        veciVMistnostiPane.setVisible(aktualniProstor.bylProzkoumany);
        if (!aktualniProstor.bylProzkoumany) {
            veciVMistnosti.getItems().clear();
        }
    }

    private void aktualizujPolohuHrace() {
        if (hrac != null) {
            Point2D pozice = souradniceProstoru.get(hra.getHerniPlan().getAktualniProstor().getNazev());
            if (pozice != null) {
                hrac.setLayoutX(pozice.getX());
                hrac.setLayoutY(pozice.getY());
            }
        }
    }

    @FXML
    private void odesliVstup(ActionEvent event) {
        String prikaz = vstup.getText();
        vstup.clear();
        zpracujPrikaz(prikaz);
    }

    private void zpracujPrikaz(String prikaz) {
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        if (!hra.konecHry() || !epilogPrinted) {
            vystup.appendText(vysledek + "\n\n");
        }

        if (hra.konecHry() && !epilogPrinted) {
            vystup.appendText(hra.vratEpilog() + "\n");
            zakazVstupy();
            epilogPrinted = true;
        }

        if (prikaz.startsWith("Jdi") || prikaz.startsWith("Vyhoď") ||
                prikaz.startsWith("Prozkoumej") || prikaz.startsWith("Vezmi") ||
                prikaz.startsWith("Použij")) {
            zvysKliky();
        }
    }

    private void zvysKliky() {
        clickCounter++;
        if (clickCounter == 10) {
            vystup.appendText("Pozor, máš už jen 20 minut!\n");
        } else if (clickCounter == 20) {
            vystup.appendText("Pozor, máš už jen 10 minut!\n");
        } else if (clickCounter >= 30) {
            hra.setKonecHry(true);
            if (!epilogPrinted) {
                vystup.appendText(hra.vratEpilog() + "\n");
                zakazVstupy();
                epilogPrinted = true;
            }
        }
    }

    private void zakazVstupy() {
        vstup.setDisable(true);
        tlacitkoOdesli.setDisable(true);
        panelVychodu.setDisable(true);
        veciVMistnosti.setDisable(true);
        veciVKapse.setDisable(true);
        tlacitkoProzkoumatMistnost.setDisable(true);
    }

    @FXML
    private void prozkoumatMistnost(ActionEvent event) {
        String prikaz = "Prozkoumej";
        zpracujPrikaz(prikaz);
        veciVMistnostiPane.setVisible(true);
        aktualizujVeciVMistnosti();
    }

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

    @FXML
    private void vemVec(MouseEvent event) {
        Vec vybranaVec = veciVMistnosti.getSelectionModel().getSelectedItem();
        if (vybranaVec != null) {
            String prikaz = "Vezmi " + vybranaVec.getNazev();
            zpracujPrikaz(prikaz);
            aktualizujVeciVMistnosti();
            aktualizujKapsa();
        }
    }

    private void aktualizujVeciVMistnosti() {
        ObservableList<Vec> veciList = FXCollections.observableArrayList();
        if (hra.getHerniPlan().getAktualniProstor().bylProzkoumany) {
            veciList.addAll(hra.getHerniPlan().getAktualniProstor().getVeci().values());
        }
        veciVMistnosti.setItems(veciList);
    }

    private void aktualizujKapsa() {
        Platform.runLater(() -> veciVKapse.setItems(hra.getHerniPlan().getKapsa().getVeci()));
    }

    @FXML
    private void inventarKlik(MouseEvent event) {
        Vec selectedItem = veciVKapse.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Prostor currentRoom = hra.getHerniPlan().getAktualniProstor();

            if (selectedItem.getNazev().equals("Klíč od skříně") &&
                    currentRoom.najdiVec("Zamčená skříň") != null) {
                zpracujPrikaz("Použij Klíč od skříně Zamčená skříň");
            } else if (selectedItem.getNazev().equals("Klíč od dveří") &&
                    currentRoom.najdiVec("Zamčené dveře") != null) {
                zpracujPrikaz("Použij Klíč od dveří Zamčené dveře");
            } else {
                zpracujPrikaz("Vyhoď " + selectedItem.getNazev());
            }
        }
    }

    @FXML
    private void novaHraKlik(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Skutečně si přejete začít novou hru?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

        hra = new Hra();
        Vec.setHra((Hra) hra); }

        vystup.clear();
        vystup.appendText(hra.vratUvitani() + "\n\n");
        vstup.clear();
        vstup.setDisable(false);
        tlacitkoOdesli.setDisable(false);

        aktualizujSeznamVychodu();
        aktualizujPolohuHrace();
        veciVKapse.getItems().clear();
        veciVMistnosti.getItems().clear();
        veciVMistnostiPane.setVisible(false);
        clickCounter = 0;
        epilogPrinted = false;

        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, this::aktualizujProstredi);
        hra.registruj(ZmenaHry.KONEC_HRY, this::aktualizujKonecHry);
        hra.registruj(ZmenaHry.ZMENA_CASU, this::aktualizujCas);
    }

    private void aktualizujCas() {
        int zbyvajiciAkce = hra.getHerniPlan().getCasovac().getZbyvajiciAkce();
        if (zbyvajiciAkce == 20 || zbyvajiciAkce == 10) {
            Platform.runLater(() -> vystup.appendText("Pozor, máš už jen " + zbyvajiciAkce + " minut!\n"));
        }
        if (zbyvajiciAkce <= 0 && !epilogPrinted) {
            Platform.runLater(() -> {
                hra.setKonecHry(true);
                vystup.appendText(hra.vratEpilog() + "\n");
                zakazVstupy();
                epilogPrinted = true;
            });
        }
    }

    @FXML
    private void napovedaKlik(ActionEvent actionEvent) {
        Stage napovedaStage = new Stage();
        WebView wv = new WebView();
        Scene napovedaScena = new Scene(wv);
        napovedaStage.setScene(napovedaScena);
        napovedaStage.show();
        wv.getEngine().load(getClass().getResource("/cz.vse.adventura/main/napoveda.html").toExternalForm());
    }

    public void ukoncitHru(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Skutečně si přejete ukončit hru?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void aktualizujKonecHry() {
        if (hra.konecHry() && !epilogPrinted) {
            vystup.appendText(hra.vratEpilog() + "\n");
            zakazVstupy();
            epilogPrinted = true;
        }
    }
}