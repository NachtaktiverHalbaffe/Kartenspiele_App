package Database.Durak;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import utils.Dataconverter_Durak;

@Database(entities = {Durak_Partie.class}, version =1, exportSchema = false)
@TypeConverters(Dataconverter_Durak.class)
public abstract class Durak_Database extends RoomDatabase {
    private static Durak_Database instance;
    public abstract Durak_DAO durak_dao();
    public static synchronized  Durak_Database getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Durak_Database.class,"Durak Database").fallbackToDestructiveMigration().build();
        }
        return  instance;
    }
}