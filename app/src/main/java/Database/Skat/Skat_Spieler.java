package Database.Skat;

import java.util.List;

public class Skat_Spieler {

    private  String name;
    private List<Integer> punkte;
    private int summe;

    public Skat_Spieler(String name, List<Integer> punkte, int summe) {
        this.name = name;
        this.punkte = punkte;
        this.summe = summe;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPunkte() {
        return punkte;
    }

    public void setPunkte(List<Integer> punkte) {
        this.punkte = punkte;
    }

    public int getSumme() {
        return summe;
    }

    public void setSumme(int summe) {
        this.summe = summe;
    }


}
