package Database.Shithead;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import utils.DataConverter_Shithead;

@Database(entities = {Shithead_Partie.class}, version =1, exportSchema = false)
@TypeConverters(DataConverter_Shithead.class)
public abstract class Shithead_Database extends RoomDatabase {
    private static Shithead_Database instance;
    public abstract Shithead_DAO shithead_dao();
    public static synchronized  Shithead_Database getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Shithead_Database.class,"Shithead Database").fallbackToDestructiveMigration().build();
        }
        return  instance;
    }
}
