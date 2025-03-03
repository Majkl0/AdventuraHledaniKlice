package cz.vse.adventura.logika;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Kapsa {
    private static final int MAXIMALNI_POCET_VECI = 3;
    private static ObservableList<Vec> seznamVeci;

    public Kapsa() {
        seznamVeci = FXCollections.observableArrayList();
    }

    public boolean pridejPredmet(Vec predmet) {
        if (seznamVeci.size() >= MAXIMALNI_POCET_VECI) {
            return false;
        }
        boolean uspech = seznamVeci.add(predmet);
        if (uspech) {
            aktualizujUI();
        }
        return uspech;
    }

    public Vec odeberPredmet(String nazevVeci) {
        Vec predmet = seznamVeci.stream()
            .filter(vec -> vec.getNazev().equals(nazevVeci))
            .findFirst()
            .orElse(null);
        if (predmet != null) {
            seznamVeci.remove(predmet);
            aktualizujUI();
        }
        return predmet;
    }

    public boolean obsahujePredmet(String nazevVeci) {
        return seznamVeci.stream()
            .anyMatch(vec -> vec.getNazev().equals(nazevVeci));
    }

    public ObservableList<Vec> getVeci() {
        return seznamVeci;
    }

    private void aktualizujUI() {
        Platform.runLater(() -> {
            List<Vec> kopieSeznamu = new ArrayList<>(seznamVeci);
            seznamVeci.clear();
            seznamVeci.addAll(kopieSeznamu);
        });
    }
}