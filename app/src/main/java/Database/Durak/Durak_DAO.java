package Database.Durak;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Durak_DAO {
    @Query("SELECT * FROM `Durak Partie`")
    LiveData<List<Durak_Partie>> getAllDurak_Partien();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDurak_Partie(Durak_Partie durak_partie);

    @Delete
    void deleteDurak_Partie(Durak_Partie durak_partie);

    @Update
    void updateDurak_Partie(Durak_Partie durak_partie);
}