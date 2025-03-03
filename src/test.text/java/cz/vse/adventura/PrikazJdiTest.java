package cz.vse.adventura;

import cz.vse.adventura.Prikazy.PrikazJdi;
import cz.vse.adventura.logika.Casovac;
import cz.vse.adventura.logika.HerniPlan;
import cz.vse.adventura.logika.Prostor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrikazJdiTest {
    private HerniPlan herniPlan;
    private PrikazJdi prikazJdi;
    private Casovac casovac;


    @BeforeEach
    void setUp() {
        herniPlan = new HerniPlan();
        casovac = new Casovac(5);
        prikazJdi = new PrikazJdi(herniPlan, casovac);

        Prostor chodba = new Prostor("Chodba");
        Prostor loznice = new Prostor("Ložnice");
        Prostor kuchyne = new Prostor("Kuchyně");
        Prostor obyvak = new Prostor("Obývák");

        chodba.setVychod(loznice);
        chodba.setVychod(kuchyne);
        chodba.setVychod(obyvak);
        chodba.setVychod(loznice);
        loznice.setVychod(chodba);
        kuchyne.setVychod(chodba);
        obyvak.setVychod(chodba);

        herniPlan.setAktualniProstor(chodba);
    }


@Test
void testProvedPrikazSpravnyPohybZChodbyDoKuchyne() {
    assertEquals("Chodba", herniPlan.getAktualniProstor().getNazev());
    prikazJdi.provedPrikaz("Kuchyně");
    assertEquals("Kuchyně", herniPlan.getAktualniProstor().getNazev());
}

@Test
void testProvedPrikazNespravnyPohybZLozniceDoObyvaku() {
    herniPlan.setAktualniProstor(new Prostor("Ložnice")); // Nastaví Ložnice jako aktuální místnost
    assertEquals("Ložnice", herniPlan.getAktualniProstor().getNazev());
    String vysledekPrikazu = prikazJdi.provedPrikaz("Obývák"); // Pokus o přechod do místnosti, která není přímo dostupná z Ložnice
    assertTrue(vysledekPrikazu.contains("Tam se odsud jít nedá!"));
    assertEquals("Ložnice", herniPlan.getAktualniProstor().getNazev()); // Očekává se, že aktuální místnost zůstane Ložnice
}
}