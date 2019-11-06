package Database.Skat;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Skat_Dao {
    @Query("SELECT * FROM Skat_Partie")
    LiveData<List <Skat_Partie>> getAllSkat_Partien();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSkat_Partie(Skat_Partie skat_partie);

    @Delete
    void deleteSkat_Partie(Skat_Partie skat_partie);

    @Update
    void updateSkat_Partie(Skat_Partie skat_partie);

}
