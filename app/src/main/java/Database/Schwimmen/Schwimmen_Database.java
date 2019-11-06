package Database.Schwimmen;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import utils.DataConverter_Schwimmen;

@Database(entities = {Schwimmen_Partie.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverter_Schwimmen.class)
public abstract class Schwimmen_Database extends RoomDatabase {
    public abstract Schwimmen_DAO schwimmen_dao();
    private static Schwimmen_Database instance;

    public static synchronized  Schwimmen_Database getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Schwimmen_Database.class,"Schwimmen Database").fallbackToDestructiveMigration().build();
        }
        return  instance;
    }
}
