package Database.Skat;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import utils.DataConverter_Skat;

@Database(entities = {Skat_Partie.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverter_Skat.class)
public abstract class Skat_database extends RoomDatabase {
    public abstract Skat_Dao Skat_Dao();
    private static Skat_database instance;

    public static synchronized  Skat_database getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Skat_database.class,"Skat Database").fallbackToDestructiveMigration().build();
        }
        return  instance;
    }
}
