package Database.Skat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import java.util.List;

import utils.DataConverter_Skat;
import utils.Dataconverter_Skat_gespielt;

@Entity(tableName = "Skat_Partie")
public class Skat_Partie {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String partiebezeichnung;

    @TypeConverters(DataConverter_Skat.class)
    @ColumnInfo(name = "Spieler")
    private List<Skat_Spieler> skat_spieler;


    @TypeConverters(Dataconverter_Skat_gespielt.class)
    @ColumnInfo(name = "Trumpf")
    private List<String> trumpf;

    @TypeConverters({Dataconverter_Skat_gespielt.class})
    @ColumnInfo(name = "Gewinnstufe")
    private List<String> gewinnstufe;

    public Skat_Partie(@NonNull String partiebezeichnung, List<Skat_Spieler> skat_spieler, List<String> trumpf, List<String> gewinnstufe) {
        this.partiebezeichnung = partiebezeichnung;
        this.skat_spieler = skat_spieler;
        this.trumpf = trumpf;
        this.gewinnstufe = gewinnstufe;
    }

    public String getPartiebezeichnung() {
        return partiebezeichnung;
    }

    public void setPartiebezeichnung(String partiebezeichnung) {
        this.partiebezeichnung = partiebezeichnung;
    }

    public List<Skat_Spieler> getSkat_spieler() {
        return skat_spieler;
    }

    public void setSkat_spieler(List<Skat_Spieler> skat_spieler) {
        this.skat_spieler = skat_spieler;
    }

    public List<String> getTrumpf() {
        return trumpf;
    }

    public void setTrumpf(List<String> trumpf) {
        this.trumpf = trumpf;
    }

    public List<String> getGewinnstufe() {
        return gewinnstufe;
    }

    public void setGewinnstufe(List<String> gewinnstufe) {
        this.gewinnstufe = gewinnstufe;
    }
}



