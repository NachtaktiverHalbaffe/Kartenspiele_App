package Database.Schlafmuetze;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.List;
import utils.DataConverter_Schlafmuetze;

@Entity(tableName = "Schlafmütze Partie")
public class Schlafmuetze_Partie {

        @NonNull
        @PrimaryKey(autoGenerate = false)
        private String partiebezeichnung;

        @TypeConverters(DataConverter_Schlafmuetze.class)
        @ColumnInfo(name = "Spieler")
        private List<Schlafmuetze_Spieler> spieler;

        public Schlafmuetze_Partie(@NonNull String partiebezeichnung, List<Schlafmuetze_Spieler> spieler) {
            this.partiebezeichnung = partiebezeichnung;
            this.spieler = spieler;
        }

        @NonNull
        public String getPartiebezeichnung() {
            return partiebezeichnung;
        }

        public List<Schlafmuetze_Spieler> getSpieler() {
            return spieler;
        }
}

