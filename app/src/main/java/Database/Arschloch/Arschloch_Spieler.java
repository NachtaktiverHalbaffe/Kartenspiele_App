package Database.Arschloch;

import java.util.List;

public class Arschloch_Spieler {

    private String name;
    private  List<Integer> platzierungen;

    public Arschloch_Spieler(String name, List<Integer> platzierungen) {
        this.name = name;
        this.platzierungen = platzierungen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPlatzierungen() {
        return platzierungen;
    }

    public void setPlatzierungen(List<Integer> platzierungen) {
        this.platzierungen = platzierungen;
    }
}
