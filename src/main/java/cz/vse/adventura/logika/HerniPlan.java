package cz.vse.adventura.logika;

import cz.vse.adventura.main.Pozorovatel;
import cz.vse.adventura.main.PredmetPozorovani;

import java.util.HashSet;
import java.util.Set;

/**
 * Třída HerniPlan
 * Představuje mapu (byt) a rozložení objektů (místností a věcí) v něm.
 * Slouží k přidání nových prostorů, věcí a zadání jejich vlastností.
 * Třída propojuje jednotlivé prostory a sleduje, kde se hráč nachází.
 *
 * Autoři: Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Michael Cerny
 * Verze: pro školní rok 2016/2017, LS2024, 4IT110
 */
public class HerniPlan implements PredmetPozorovani {
    private Prostor aktualniProstor;  // Změněno na private pro zapouzdření

    /**
     * Konstruktor třídy HerniPlan. Vytváří místnosti ve hře.
     */
    public HerniPlan() {
        vytvorMistnostiHry(); // Inicializace herního plánu
    }

    private Set<Pozorovatel> seznamPozorovatelu = new HashSet<>();
private void vytvorMistnostiHry() {
    Prostor chodba = new Prostor("Chodba");
    Prostor obyvaciPokoj = new Prostor("Obývák");
    Prostor loznice = new Prostor("Ložnice");
    Prostor kuchyn = new Prostor("Kuchyň");

    chodba.setVychod(obyvaciPokoj);
    chodba.setVychod(loznice);
    chodba.setVychod(kuchyn);
    obyvaciPokoj.setVychod(chodba);
    loznice.setVychod(chodba);
    kuchyn.setVychod(chodba);

    aktualniProstor = chodba;

    //Přidání věcí na chodbu
    Vec kompas = new Vec("Kompas", true);
    Vec sroubovak = new Vec("Šroubovák", true);
    Vec boty = new Vec("Boty", true);
    Vec zamceneVchodoveDvere = new Vec("Zamčené dveře", false);
    chodba.vlozVec(kompas);
    chodba.vlozVec(sroubovak);
    chodba.vlozVec(boty);
    chodba.vlozVec(zamceneVchodoveDvere);

    // Přidání veci do kuchyně
    Vec sul = new Vec("Sůl", true);
    Vec spinavyTalir = new Vec("Špinavý talíř", true);
    Vec zapalky = new Vec("Zápalky", true);
    kuchyn.vlozVec(sul);
    kuchyn.vlozVec(spinavyTalir);
    kuchyn.vlozVec(zapalky);

    // Přidání veci do obyvaci pokoje
    Vec dalkoveOvladani = new Vec("Dálkové ovládání", true);
    Vec kniha = new Vec("Knížka", true);
    Vec klicOdSkrine = new Vec("Klíč od skříně", true);
    obyvaciPokoj.vlozVec(dalkoveOvladani);
    obyvaciPokoj.vlozVec(kniha);
    obyvaciPokoj.vlozVec(klicOdSkrine);

    //Pridani veci do Ložnnice
    Vec polstar = new Vec("Polštář", true);
    Vec perina = new Vec("Peřina", true);
    Vec zamcenaSkrin = new Vec("Zamčená skříň", false);
    loznice.vlozVec(polstar);
    loznice.vlozVec(perina);
    loznice.vlozVec(zamcenaSkrin);
}

    /**
     * Vrátí aktuální prostor, ve kterém se hráč nachází.
     * @return Aktuální prostor.
     */

    /**
     * Nastaví nový aktuální prostor.
     * @param novyProstor Prostor, který se stane novým aktuálním prostorem - když hráč jde
     * do jiné místnosti.
     */
    public void setAktualniProstor(Prostor novyProstor)  { //Setter pro aktuální prostor, pro pohyb pomoci prikazu Jdi
        this.aktualniProstor = novyProstor;
        for(Pozorovatel pozorovatel: seznamPozorovatelu) {
            pozorovatel.aktualizuj();
        }
    }

    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     * @param pozorovatel
     */
    @Override
    public void registruj(Pozorovatel pozorovatel) {
        seznamPozorovatelu.add(pozorovatel);

    }
}