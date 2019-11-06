package Database.Arschloch;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import utils.DataConverter_Arschloch;

@Entity(tableName = "Arschloch Partie")
public class Arschloch_Partie {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String partiebezeichnung;

    @TypeConverters(DataConverter_Arschloch.class)
    @ColumnInfo (name = "Spieler")
    private  List<Arschloch_Spieler> spieler;

    public Arschloch_Partie(@NonNull String partiebezeichnung, List<Arschloch_Spieler> spieler) {
        this.partiebezeichnung = partiebezeichnung;
        this.spieler = spieler;
    }

    @NonNull
    public String getPartiebezeichnung() {
        return partiebezeichnung;
    }

    public List<Arschloch_Spieler> getSpieler() {
        return spieler;
    }
}
