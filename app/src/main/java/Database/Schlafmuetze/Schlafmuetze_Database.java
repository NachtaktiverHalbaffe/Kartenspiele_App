package Database.Schlafmuetze;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import utils.DataConverter_Schlafmuetze;

@Database(entities = {Schlafmuetze_Partie.class}, version =1, exportSchema = false)
@TypeConverters(DataConverter_Schlafmuetze.class)
public abstract class Schlafmuetze_Database extends RoomDatabase {
    private static Schlafmuetze_Database instance;
    public abstract Schlafmuetze_DAO schlafmuetze_dao();
    public static synchronized  Schlafmuetze_Database getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Schlafmuetze_Database.class,"Schlafmütze Database").fallbackToDestructiveMigration().build();
        }
        return  instance;
    }
}
