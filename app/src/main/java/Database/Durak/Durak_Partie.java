package Database.Durak;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import utils.DataConverter_Arschloch;

@Entity(tableName = "Durak Partie")
public class Durak_Partie {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String partiebezeichnung;

    @TypeConverters(DataConverter_Arschloch.class)
    @ColumnInfo(name = "Spieler")
    private List<Durak_Spieler> spieler;

    public Durak_Partie(@NonNull String partiebezeichnung, List<Durak_Spieler> spieler) {
        this.partiebezeichnung = partiebezeichnung;
        this.spieler = spieler;
    }

    @NonNull
    public String getPartiebezeichnung() {
        return partiebezeichnung;
    }

    public List<Durak_Spieler> getSpieler() {
        return spieler;
    }
}

