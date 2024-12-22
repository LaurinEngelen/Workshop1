import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Aufgabe3_Textanalyse {
    public static void aufgabe() throws IOException {
        //
        // a) (6P)
        // Die Datei Kafka_Der_Prozess.txt soll eingelesen werden und verschiedene Auswertungen erfolgen.
        // Ergänzen Sie die Funktion einlesen so, dass die eingelesenen Wörter als Liste zurückgeliefert werden.
        System.out.println("\nAufgabe 3a (6P):");
        long start = System.nanoTime(); // aktuelle Zeit in nsec
        List<String> lst_Kafka = einlesen("data/Kafka_Der_Prozess.txt");
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);

        // Geben Sie die Anzahl der eingelesenen Wörter aus. Benutzen Sie dazu Ihre eingelesene Liste.
        System.out.println(lst_Kafka.size());

        // Sortieren Sie die Liste und geben Sie die ersten 5 Wörter und die letzten 5 Wörter aus.
        start = System.nanoTime();
        lst_Kafka.sort((w1, w2) -> w1.compareTo(w2));
        for (int x = 0; x < 5; x++){
            System.out.println(lst_Kafka.get(x));
        }

        for (int y = lst_Kafka.size()-1; y > lst_Kafka.size()-6; y--){
            System.out.println(lst_Kafka.get(y));
        }
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);

        // Speichern Sie die Liste in eine TreeSet und geben Sie ersten die 100  Wörter aus.
        // Berücksichtigen Sie die Konstruktoren der Klasse TreeSet!
        // Ihr Code: ...
        NavigableSet<String> set_Kafka = new TreeSet<>();
        set_Kafka.addAll(lst_Kafka);
        int n = 0;
        Iterator<String> iter = set_Kafka.iterator();
        while (n < 101  && iter.hasNext()) {
            String t = iter.next();
            System.out.println(t);
            n++;
        }


        //
        // b) (6P)
        // Verwenden Sie die bereits eingelesene Liste lst_Kafka und erstellen Sie eine Häufigkeitstabelle als SortedMap.


        System.out.println("\nAufgabe 3b (6P):");
        start = System.nanoTime();
        SortedMap<String, Integer> fqTable_Kafka = ermittleHaeufigkeiten(lst_Kafka);
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);

        // Wieviel unterschiedliche Wörter gibt es?
        System.out.println(fqTable_Kafka.size());

        // Geben Sie mit Hilfe von subMap alle Wörter (mit ihren Häufigkeiten) aus, die mit "Ver" beginnen.
        for (var lk : fqTable_Kafka.subMap("Ver", "Ves").entrySet()){
            System.out.println(lk);
        }
        // Geben Sie die 20 häufigsten Wörter (mit ihren Häufigkeiten) aus.
        var hList = new ArrayList<>(fqTable_Kafka.entrySet());
        hList.sort((e1,e2)->e2.getValue()-e1.getValue());
        System.out.println(hList.subList(0,19));



        //
        // c) (6P)
        // Ermitteln Sie für den Kafka-Text eine Häufigkeitstabelle der falsch geschriebenen Wörter.
        // Wieviel falsch geschriebene Wörter gibt es?
        // Geben Sie die 20 häufigsten falsch geschriebenen Wörter (mit ihren Häufigkeiten) aus.
        // Ein Wort gilt als falsch geschrieben, wenn es nicht in der Wörterbuchdatei word_list_german_spell_checked.txt vorkommt.
        // Lesen Sie die alle Wörter der Datei ein und speichern Sie sie in eine effiziente Datenstruktur ab.
        // Achtung: Das Einlesen der Datei dauert etwas Zeit, da sie mehr als 2 Millionen Einträge enthält.
        System.out.println("\nAufgabe 3c (6P):");

        start = System.nanoTime();
        // word_list_german_spell_checked.txt einlesen:
        Set<String> lst_spell_check_words = new HashSet<>(einlesen("data/word_list_german_spell_checked.txt"));
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);
        SortedMap<String, Integer> haeufigkeitFalscheWoerter = new TreeMap<>();

        for(String w : set_Kafka){
            if(!lst_spell_check_words.contains(w)){
                haeufigkeitFalscheWoerter.put(w, fqTable_Kafka.get(w));
            }
        }
        System.out.println(haeufigkeitFalscheWoerter.size());

        var bList = new ArrayList<>(haeufigkeitFalscheWoerter.entrySet());
        bList.sort((e1,e2)->e2.getValue()-e1.getValue());
        System.out.println(bList.subList(0,19));

    }

    private static List<String> einlesen(String fileName) throws IOException {
        LineNumberReader in = new LineNumberReader(new FileReader(fileName, StandardCharsets.UTF_8));
        List<String> list = new ArrayList<>();
        String line;

        while ((line = in.readLine()) != null) { //Zeile
            String[] wf = line.split("[^a-z^A-Z^ß^ä^ö^ü^Ä^Ö^Ü]+"); //wf = line in array
            for (String w: wf) { //w = Wort
                if (w.length() == 0 || w.length() == 1)
                    continue;
                list.add(w);
            }
        }
        return list;
    }

    private static SortedMap<String, Integer> ermittleHaeufigkeiten(List<String> wListe)  {
        SortedMap<String, Integer> haeufigkeitWoerter = new TreeMap<>();
        for (String w : wListe){
            if(haeufigkeitWoerter.containsKey(w)){
                int oldValue = haeufigkeitWoerter.get(w);
                haeufigkeitWoerter.put(w, oldValue+1);
            } else {
                haeufigkeitWoerter.put(w, 1);
            }
        }
        return haeufigkeitWoerter;
    }
}
