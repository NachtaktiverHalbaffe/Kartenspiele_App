package Database.Golfen;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Golfen_Partie_Dao {
    @Query("SELECT * FROM Golfen_Partie")
    LiveData<List <Golfen_Partie>> getAllGolfen_Partien();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addGolfen_Partie(Golfen_Partie golfen_partie);

    @Delete
    void deleteGolf_Partie(Golfen_Partie golfen_partie);

    @Update
    void updateGolfen_Partie(Golfen_Partie golfen_partie);
}
