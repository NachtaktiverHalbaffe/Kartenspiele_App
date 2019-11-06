package Database.Schwimmen;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Schwimmen_DAO {
    @Query("SELECT * FROM `Schwimmen Partie`")
    LiveData<List<Schwimmen_Partie>> getAllSchwimmen_Partien();

    //@Query("SELECT * FROM Golfen_Partie WHERE id = :id")
    //Golfen_Partie getGolfenPartieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSchwimmen_Partie(Schwimmen_Partie schwimmen_partie);

    @Delete
    void deleteSchwimmen_Partie(Schwimmen_Partie schwimmen_partie);

    @Update
    void updateSchwimmen_Partie(Schwimmen_Partie schwimmen_partie);
}
