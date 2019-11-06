package Database.Shithead;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface Shithead_DAO {

        @Query("SELECT * FROM `Shithead Partie`")
        LiveData<List<Shithead_Partie>> getAllShithead_Partien();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void addShithead_Partie(Shithead_Partie shithead_partie);

        @Delete
        void deleteShithead_Partie(Shithead_Partie shithead_partie);

        @Update
        void updateShithead_Partie(Shithead_Partie shithead_partie);
}