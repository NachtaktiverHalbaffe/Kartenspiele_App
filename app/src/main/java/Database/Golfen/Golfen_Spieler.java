package Database.Golfen;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by NachtaktiverHalbaffe on 29.11.2017.
 */

public class Golfen_Spieler {

    private  String name;
    private  List<Integer> punkte;
    private int summe;

    public Golfen_Spieler(String name, List<Integer> punkte, int summe) {
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
