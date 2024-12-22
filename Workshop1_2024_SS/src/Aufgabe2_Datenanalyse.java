import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Aufgabe2_Datenanalyse {

    public static void aufgabe() throws IOException {

        // a) (2P)
        // Werfen Sie einen Blick in die Statistik-Datei:
        //      data/12411-0017-KREISE_$F.csv bzw. data/12411-0017-KREISE_$F.xlsx
        // In der Datei sind alle Landkreise von Deutschland mit einer Altersverteilung aufgelistet.
        // Die gegebene statische Methode einlesen liest alle Landkreise in eine Liste ein.
        // Geben Sie die Liste (jeder Landkreis in eine Zeile) aus. Hinweis: schreiben Sie eine statische print-Methode.
        // Bestimmen Sie die Anzahl der Landkreise.
        System.out.println("\nAufgabe 2a (2P):");
        List<Landkreis> landkreiseBRD = einlesen("data/12411-0017-KREISE_$F.csv");

        ausgeben(landkreiseBRD);
        System.out.println(landkreiseBRD.size());


        // b) (3P)
        // Sortieren Sie die Liste nach dem Namen und geben Sie die Liste (jeder Landkreis in eine Zeile) aus:
        System.out.println("\nAufgabe 2b (3P):");
        //landkreiseBRD.sort(Comparator.comparing(Landkreis::name));
        landkreiseBRD.sort((lk1, lk2) -> lk1.name().compareTo(lk2.name()));
        ausgeben(landkreiseBRD);


        // c) (3P)
        // Sortieren Sie die Liste absteigend nach der Einwohnerzahl und geben Sie die Liste (jeder Landkreis in eine Zeile) aus:
        System.out.println("\nAufgabe 2c (3P):");
        landkreiseBRD.sort((lk1,lk2)->lk2.anzahlEinwohner()-lk1.anzahlEinwohner());
        ausgeben(landkreiseBRD);


        // d) (3P)
        // Erstellen Sie eine Map, die für jeden Landkreisnamen die Einwohnerzahl speichert.
        // Geben Sie für alle mit 'K' beginnenden Landkreise den Namen und die Einwohnerzahl aus.
        // Benutzen Sie dazu subMap!
        System.out.println("\nAufgabe 2d (3P):");
        NavigableMap<String, Integer> Landkreise = new TreeMap<>();
        for (Landkreis lk : landkreiseBRD){
            Landkreise.put(lk.name(), lk.anzahlEinwohner());
        }

        for (var lk : Landkreise.subMap("K","L").entrySet()){
            System.out.println(lk);
        }

        // e) (1P)
        // Geben Sie die Menge (Set) aller Landkreisnamen aus:
        System.out.println("\nAufgabe 2e (1P):");
        Set<String> Menge = new HashSet<>();

        /*for (Landkreis lk : landkreiseBRD){
            Menge.add(lk.name());
        }
        */

        System.out.println(Landkreise.keySet());
    }

    private static void ausgeben(List l){
        for (Object Landkreis : l){
            System.out.println(Landkreis);
        }

    }

    private static List<Landkreis> einlesen(String fn) throws IOException {
        List<Landkreis> kreiseBRD = new LinkedList<>();

        // lese ";"-separated file
        LineNumberReader in = new LineNumberReader(new FileReader(fn, StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            Scanner lineScanner = new Scanner(line).useDelimiter(";");
            // Zeilen, die keinen Landkreis beschreiben, werden ausgelassen:
            if (!lineScanner.hasNextInt())
                continue;
            int plz = lineScanner.nextInt();
            String name = lineScanner.next();
            // Landkreise ohne Einwohnerzahlen werden ausgelassen:
            if (!lineScanner.hasNextInt())
                continue;
            // Lese die Einwohnerzahl ewz in der letzten Spalte:
            int ewz = 0;
            while (lineScanner.hasNextInt())
                ewz = lineScanner.nextInt();
            kreiseBRD.add(new Landkreis(name,plz,ewz));
        }

        return kreiseBRD;
    }
}
