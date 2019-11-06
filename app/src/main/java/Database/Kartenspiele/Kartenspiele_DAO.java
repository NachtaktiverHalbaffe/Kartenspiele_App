package Database.Kartenspiele;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Kartenspiele_DAO {
    @Query("SELECT * FROM Kartenspiel")
    LiveData<List<Kartenspiel>> getAllKartenspiele();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addKartenspiel(Kartenspiel kartenspiel);

    @Delete
    void deleteKartenspiel(Kartenspiel kartenspiel);

    @Update
    void updateKartenspiel(Kartenspiel kartenspiel);
}
