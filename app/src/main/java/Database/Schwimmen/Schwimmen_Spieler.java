package Database.Schwimmen;

public class Schwimmen_Spieler {

    private String name;
    private int leben;
    private String platzierung;
    private boolean tod;

    public Schwimmen_Spieler(String name, int leben, String platzierung, boolean tod) {
        this.name = name;
        this.leben = leben;
        this.platzierung = platzierung;
        this.tod = tod;
    }

    public Schwimmen_Spieler(String name, int leben, String platzierung) {
        this.name = name;
        this.leben = leben;
        this.platzierung = platzierung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeben() {
        return leben;
    }

    public void setLeben(int leben) {
        this.leben = leben;
    }

    public String getPlatzierung() {
        return platzierung;
    }

    public void setPlatzierung(String platzierung) {
        this.platzierung = platzierung;
    }

    public boolean isTod() {
        return tod;
    }

    public void setTod(boolean tod) {
        this.tod = tod;
    }
}
