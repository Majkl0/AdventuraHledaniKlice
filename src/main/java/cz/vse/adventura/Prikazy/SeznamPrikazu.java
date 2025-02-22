package cz.vse.adventura.Prikazy;

import cz.vse.adventura.logika.IPrikaz;

import java.util.HashMap;
import java.util.Map;


/**
 *  Class SeznamPrikazu - eviduje seznam přípustných příkazů adventury.
 *  Používá se pro rozpoznávání příkazů
 *  a vrácení odkazu na třídu implementující konkrétní příkaz.
 *  Každý nový příkaz (instance implementující rozhraní Prikaz) se
 *  musí do seznamu přidat metodou vlozPrikaz.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Michael Cerny(odděláno public String vratNazvyPrikazu)
 *@version    pro školní rok 2016/2017, LS2024, 4IT110
 */
public class SeznamPrikazu {
    private Map<String, IPrikaz> mapaSPrikazy;

    /**
     * Konstruktor
     */
    public SeznamPrikazu() {
    mapaSPrikazy = new HashMap<>();
}

    /**
     * Vkládá nový příkaz.
     *
     *@param  prikaz  Instance třídy implementující rozhraní IPrikaz
     */

public void vlozPrikaz(IPrikaz prikaz) {
    mapaSPrikazy.put(prikaz.getNazev(), prikaz);
}

    /**
     * Vrací odkaz na instanci třídy implementující rozhraní IPrikaz,
     * která provádí příkaz uvedený jako parametr.
     *
     *@param  retezec  klíčové slovo příkazu, který chce hráč zavolat
     *@return instance třídy, která provede požadovaný příkaz
     */
    public IPrikaz vratPrikaz(String retezec) {
        return mapaSPrikazy.get(retezec);
    }

    /**
     * Kontroluje, zda zadaný řetězec je přípustný příkaz.
     *
     *@param  retezec  Řetězec, který se má otestovat, zda je přípustný příkaz
     *@return Vrací hodnotu true, pokud je zadaný řetězec přípustný příkaz
     */
    public boolean jePlatnyPrikaz(String retezec) {
        return mapaSPrikazy.containsKey(retezec);
    }

}