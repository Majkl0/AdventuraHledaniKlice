package cz.vse.adventura;

import cz.vse.adventura.logika.*;
import cz.vse.adventura.Prikazy.PrikazProzkoumej;
import cz.vse.adventura.logika.Prostor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrikazProzkoumejTest {

    private HerniPlan herniPlan;
    private Prostor prostor;


    @BeforeEach
    void setUp() {
        herniPlan = new HerniPlan();
        prostor = new Prostor("Kuchyn");
        prostor.vlozVec(new Vec("Hrnec", true));
        herniPlan.setAktualniProstor(prostor);

    }

    @Test
    void testPrvniNavstevaMistnosti() {
        String vysledek = prostor.dlouhyPopis();
        assertEquals("\nVýchody: \n", vysledek);
        assertFalse(prostor.bylProzkoumany); // Ověřuje, že místnost nebyla ještě prozkoumána
    }

    @Test
    void testProzkoumaniMistnosti() {
        prostor.setBylProzkoumany(true); // Simuluje použití příkazu prozkoumej
        String vysledek = prostor.dlouhyPopis();
        assertTrue(prostor.bylProzkoumany); // Ověřuje, že místnost byla prozkoumána
        assertEquals("\nVýchody: \nVěci v prostoru: Hrnec", vysledek);
    }
}
