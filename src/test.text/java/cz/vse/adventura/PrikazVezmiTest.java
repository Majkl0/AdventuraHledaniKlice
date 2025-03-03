package cz.vse.adventura;

import cz.vse.adventura.logika.*;
import cz.vse.adventura.Prikazy.PrikazVezmi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrikazVezmiTest {

    private HerniPlan herniPlan;
    private Kapsa kapsa;
    private PrikazVezmi prikazVezmi;
    private Casovac casovac;


    @BeforeEach
    void setUp() {
        herniPlan = new HerniPlan();
        kapsa = new Kapsa();
        casovac = new Casovac(4);
        prikazVezmi = new PrikazVezmi(herniPlan, kapsa, casovac);
    }

    @Test
    void testVezmiKdyzNeniKapsaPlna() {
        Prostor prostor = new Prostor("Chodba");
        Vec vec = new Vec("klíč", true);
        prostor.vlozVec(vec);
        herniPlan.setAktualniProstor(prostor);
    }

    @Test
    void testVezmiNeprenositelnouVec() {
        Prostor prostor = new Prostor("testovaciProstor");
        Vec vec = new Vec("skříň", false);
        prostor.vlozVec(vec);
        herniPlan.setAktualniProstor(prostor);

        String vysledek = prikazVezmi.provedPrikaz("skříň");
        assertEquals("Předmět 'skříň' opravdu sebrat nelze.", vysledek);
        assertFalse(kapsa.obsahujePredmet("skříň"));
    }
}