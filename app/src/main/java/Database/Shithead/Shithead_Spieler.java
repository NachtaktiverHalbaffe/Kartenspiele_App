package Database.Shithead;

import java.util.List;

public class Shithead_Spieler {

    private String name;
    private List<Integer> platzierungen;

    public Shithead_Spieler(String name, List<Integer> platzierungen) {
        this.name = name;
        this.platzierungen = platzierungen;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getPlatzierungen() {
        return platzierungen;
    }
}
