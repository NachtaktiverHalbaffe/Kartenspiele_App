package Database.Golfen;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import java.util.List;

import utils.DataConverter_Golfen;

@Entity(tableName = "Golfen_Partie")
public class Golfen_Partie {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String partiebezeichnung;

    @TypeConverters(DataConverter_Golfen.class)
    @ColumnInfo(name = "Spieler")
    private List<Golfen_Spieler> golfen_spieler;

    public Golfen_Partie(String partiebezeichnung, List<Golfen_Spieler> golfen_spieler) {
        this.partiebezeichnung = partiebezeichnung;
        this.golfen_spieler = golfen_spieler;
    }

    public String getPartiebezeichnung() {
        return partiebezeichnung;
    }
    public List<Golfen_Spieler> getGolfen_spieler() {
        return golfen_spieler;
    }


}
