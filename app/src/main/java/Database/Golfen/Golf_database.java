package Database.Golfen;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import utils.DataConverter_Golfen;

/**
 * Created by NachtaktiverHalbaffe on 29.11.2017.
 */

@Database(entities = {Golfen_Partie.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverter_Golfen.class)
public abstract class Golf_database extends RoomDatabase {
    public abstract Golfen_Partie_Dao golfen_spieler_dao();
    private static Golf_database instance;

    public static synchronized  Golf_database getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Golf_database.class,"Golf Database").fallbackToDestructiveMigration().build();
        }
        return  instance;
    }
}
