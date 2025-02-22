package cz.vse.adventura.Prikazy;


import cz.vse.adventura.logika.IPrikaz;

/**
 * Třída PrikazNapoveda
 * Implementuje pro hru příkaz "Nápověda".Po zadání příkazu "Nápověda" bez parametrů získá hráč seznam
 * všech dostupných příkazů.
 * Po zadání "Nápověda" s názvem konkrétního příkazu jako parametrem získá hráč nápovědu k zadanému příkazu.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček, Michael Cerny
 * @version pro školní rok 2016/2017, LS2024, 4IT110
 */

public class PrikazNapoveda implements IPrikaz {
    private static final String NAZEV = "Nápověda";
    private SeznamPrikazu platnePrikazy;

    /**
     *  Konstruktor třídy
     *
     *  @param platnePrikazy seznam příkazů,které je možné ve hře použít, aby je nápověda mohla zobrazit uživateli.
     */
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
    this.platnePrikazy = platnePrikazy;
}


    /**
     *  Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje vcelku primitivní zpráva a
     *  seznam dostupných příkazů.
     *
     *  @return napoveda ke hre
     */

    @Override
public String provedPrikaz(String... parametry) {
    if (parametry.length > 0) {
        String nazevPrikazu = parametry[0];
        IPrikaz prikaz = platnePrikazy.vratPrikaz(nazevPrikazu);

        if (prikaz != null) {
            return "Nápověda k příkazu '" + nazevPrikazu + "': " + prikaz.getPopis();
        } else {
            return "Neznámý příkaz. Pro seznam všech příkazů zadejte jen 'napoveda'.";
        }
    } else {
        return "Seznam všech příkazů: Jdi, Použij, Prozkoumej, Vezmi, Vyhoď.  Pro specifickou nápovědu zadejte 'Nápověda [název příkazu]'.";
    }
}
    /**
     * Vrátí název tohoto příkazu.
     */
@Override
public String getNazev() {
    return NAZEV;
}

        /**
     * Vrací popis příkazu "Nápověda". Tato metoda čte a vrací popis konkrétní nápovědy k zadanému příkazu.
     *
     * @return Popis příkazu jako řetězec.
     */
    @Override
    public String getPopis() {
        return null;
    }
}
