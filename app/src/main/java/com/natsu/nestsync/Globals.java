package com.natsu.nestsync;

public class Globals {
    // nicht instanziierbarer Singelton, um Daten zu speichern, die über die ganze Klasse zugreifbar sein sollen
    private static final Globals instance = new Globals();

    private Globals() {
        //hier Daten aus de Datenbank einlesen bzw Testdaten hart einkodieren
    }

    public static Globals getInstance() {
        return instance;
    }
}
