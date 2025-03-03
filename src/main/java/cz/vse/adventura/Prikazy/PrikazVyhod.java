package cz.vse.adventura.Prikazy;

import cz.vse.adventura.logika.HerniPlan;
import cz.vse.adventura.logika.IPrikaz;
import cz.vse.adventura.logika.Kapsa;
import cz.vse.adventura.logika.Vec;
import cz.vse.adventura.main.ZmenaHry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

import static cz.vse.adventura.logika.Vec.hra;

/**
 * Třída PrikazVyhod
 * Implementuje příkaz "Vyhoď". Umožňuje hráči odstranit předmět z inventáře a umístí jej do aktuálního prostoru hry.
 *
 * @author Michael Cerny
 * @version LS2024, 4IT110
 */

public class PrikazVyhod implements IPrikaz {
    private static final String NAZEV = "Vyhoď";
    private Kapsa kapsa;
    private HerniPlan herniPlan;

    /**
 * Konstruktor pro inicializaci příkazu vyhoď.
 */
public PrikazVyhod(Kapsa kapsa, HerniPlan herniPlan) {
        this.kapsa = kapsa;
        this.herniPlan = herniPlan;
}

    /**
     * Provede příkaz "Vyhoď", který umožňuje hráči vyhodit předmět z inventáře.
     * Tento příkaz vyžaduje, aby hráč specifikoval název předmětu, který chce vyhodit.
     * Pokud je předmět úspěšně vyhozen, je umístěn do aktuálního prostoru ve hře.
     */
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co mám vyhodit? Musíš zadat název předmětu.";
        }
        String nazevPredmetu = String.join(" ", parametry);

        if (!kapsa.obsahujePredmet(nazevPredmetu)) {
            return "Předmět '" + nazevPredmetu + "' nemáš v inventáři.";
        }

        Vec vyhozenyPredmet = kapsa.odeberPredmet(nazevPredmetu);
        herniPlan.getAktualniProstor().vlozVec(vyhozenyPredmet);
        hra.upozorniPozorovatele(ZmenaHry.ZMENA_MISTNOSTI);
        return "Vyhodil jsi předmět '" + nazevPredmetu + "'.";
    }

    /**
     * Vrátí název tohoto příkazu.
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

    @Override
    /**
     * Vrátí popis příkazu "Vyhod", který slouží k informování hráče o tom, jak tento příkaz používat.
     * Tento popis je zobrazen v nápovědě.
     */

    public String getPopis() {
        return "Příkaz 'Vyhoď' [název předmětu] vyhodí předmět z inventáře(kapsy). Slouží pro uvolnění místa v inventáři, vyhozenou věc najdeš v místnosti, kde si ji nechal.";
    }

}

