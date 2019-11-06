package Database.Schlafmuetze;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Schlafmuetze_DAO {

    @Query("SELECT * FROM `Schlafmütze Partie`")
    LiveData<List<Schlafmuetze_Partie>> getAllSchlafmuetze_Partien();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSchlafmuetze_Partie(Schlafmuetze_Partie schlafmuetze_partie);

    @Delete
    void deleteSchlafmuetze_Partie(Schlafmuetze_Partie schlafmuetze_partie);

    @Update
    void updateSchlafmuetze_Partie(Schlafmuetze_Partie schlafmuetze_partie);
}
