package Database.Schwimmen;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import utils.DataConverter_Schwimmen;

@Entity(tableName = "Schwimmen Partie")
public class Schwimmen_Partie {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String titel;

    @TypeConverters(DataConverter_Schwimmen.class)
    @ColumnInfo(name = "Spieler")
    private List<Schwimmen_Spieler> schwimmen_spieler;

    @ColumnInfo(name = "Verbliebene Spieler")
    private int verbliebene_spieler;

    @ColumnInfo(name = "geschwommen")
    private boolean geschwommen;

    public Schwimmen_Partie(@NonNull String titel, List<Schwimmen_Spieler> schwimmen_spieler, int verbliebene_spieler, boolean geschwommen) {
        this.titel = titel;
        this.schwimmen_spieler = schwimmen_spieler;
        this.verbliebene_spieler = verbliebene_spieler;
        this.geschwommen = geschwommen;
    }



    @NonNull
    public String getTitel() {
        return titel;
    }

    public void setTitel(@NonNull String titel) {
        this.titel = titel;
    }

    public List<Schwimmen_Spieler> getSchwimmen_spieler() {
        return schwimmen_spieler;
    }

    public void setSchwimmen_spieler(List<Schwimmen_Spieler> schwimmen_spieler) {
        this.schwimmen_spieler = schwimmen_spieler;
    }

    public int getVerbliebene_spieler() {
        return verbliebene_spieler;
    }

    public void setVerbliebene_spieler(int verbliebene_spieler) {
        this.verbliebene_spieler = verbliebene_spieler;
    }

    public boolean isGeschwommen() {
        return geschwommen;
    }

    public void setGeschwommen(boolean geschwommen) {
        this.geschwommen = geschwommen;
    }
}
