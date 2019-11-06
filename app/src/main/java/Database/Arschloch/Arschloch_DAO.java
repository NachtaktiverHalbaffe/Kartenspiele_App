package Database.Arschloch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Arschloch_DAO {
    @Query("SELECT * FROM `Arschloch Partie`")
    LiveData<List<Arschloch_Partie>> getAllArschloch_Partien();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addArschloch_Partie(Arschloch_Partie arschloch_partie);

    @Delete
    void deleteArschloch_Partie(Arschloch_Partie arschloch_partie);

    @Update
    void updateArschloch_Partie(Arschloch_Partie arschloch_partie);
}
