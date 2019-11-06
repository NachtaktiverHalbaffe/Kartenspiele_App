package Database.Schlafmuetze;

public class Schlafmuetze_Spieler {

    private String name;
    private int verloren;

    public Schlafmuetze_Spieler(String name, int verloren) {
        this.name = name;
        this.verloren = verloren;
    }

    public String getName() {
        return name;
    }

    public Integer getVerloren() {
        return verloren;
    }
}
