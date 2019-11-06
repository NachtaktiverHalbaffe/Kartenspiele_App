package Database.Kartenspiele;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Kartenspiel")
public class Kartenspiel {

    @NonNull
    @PrimaryKey (autoGenerate = false)
    private String name;

    @ColumnInfo(name = "Minimale Anzahl an Spielern")
    private int spieleranzahl_min;

    @ColumnInfo(name = "Maximale Anzahl an Spielern")
    private int spieleranzahl_max;

    @ColumnInfo(name ="Spielzeit")
    private String spielzeit;

    @ColumnInfo(name = "Benötigtes Kartendeck")
    private String kartendeck;

    @ColumnInfo(name = "Komplexität des Spiels")
    private String komplexitaet;

    private int icon;

    public Kartenspiel(@NonNull String name, int spieleranzahl_min, int spieleranzahl_max, String spielzeit, String kartendeck, String komplexitaet, int icon) {
        this.name = name;
        this.spieleranzahl_min = spieleranzahl_min;
        this.spieleranzahl_max = spieleranzahl_max;
        this.spielzeit = spielzeit;
        this.kartendeck = kartendeck;
        this.komplexitaet = komplexitaet;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getSpieleranzahl_min() {
        return spieleranzahl_min;
    }

    public int getSpieleranzahl_max() {
        return spieleranzahl_max;
    }

    public String getSpielzeit() {
        return spielzeit;
    }

    public String getKartendeck() {
        return kartendeck;
    }

    public String getKomplexitaet() {
        return komplexitaet;
    }
}
