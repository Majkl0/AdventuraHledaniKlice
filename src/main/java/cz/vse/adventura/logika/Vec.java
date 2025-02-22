package cz.vse.adventura.logika;

import java.util.HashSet;
import java.util.Set;

/**
 * Třída Vec
 * Reprezentuje předměty, které můžeme přenášet nebo mohou obsahovat další předměty.
 * Každá věc má svůj název a vlastnost zda je přenositelná.
 * 2 předměty jsou určeny k odemykání dalších dvou předmětů
 * Díky této třídě můžeme manipulovat s předměty a hru vyhrát.
 *
 * @author Michael Cerny
 * @version LS2024, 4IT110
 */
public class Vec {
    private String nazev;
    private boolean jePrenositelna;

    /**
     * Konstruktor pro vytvoření instance třídy Vec.
     * Inicializuje věc s daným názvem a určuje, zda je věc přenositelná.
     */
    public Vec(String nazevVeci, boolean jePrenositelna) {
        this.nazev = nazevVeci;
        this.jePrenositelna = jePrenositelna;
    }

    /**
     * Vrací název předmětu.
     * @return Název předmětu jako řetězec.
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * Vrací informaci, zda je předmět přenositelný.
     * @return true pokud lze předmět přenášet, jinak false.
     */
    public boolean jePrenositelna() {
        return jePrenositelna;
    }
 }

