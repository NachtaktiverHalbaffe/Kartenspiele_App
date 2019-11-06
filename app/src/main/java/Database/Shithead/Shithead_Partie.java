package Database.Shithead;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import utils.DataConverter_Shithead;

@Entity(tableName = "Shithead Partie")
public class Shithead_Partie {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String partiebezeichnung;

    @TypeConverters(DataConverter_Shithead.class)
    @ColumnInfo(name = "Spieler")
    private List<Shithead_Spieler> spieler;

    public Shithead_Partie(@NonNull String partiebezeichnung, List<Shithead_Spieler> spieler) {
        this.partiebezeichnung = partiebezeichnung;
        this.spieler = spieler;
    }

    @NonNull
    public String getPartiebezeichnung() {
        return partiebezeichnung;
    }

    public List<Shithead_Spieler> getSpieler() {
        return spieler;
    }
}
