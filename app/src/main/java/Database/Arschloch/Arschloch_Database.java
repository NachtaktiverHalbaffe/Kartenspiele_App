package Database.Arschloch;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import utils.DataConverter_Arschloch;

@Database(entities = {Arschloch_Partie.class}, version =1, exportSchema = false)
@TypeConverters(DataConverter_Arschloch.class)
public abstract class Arschloch_Database extends RoomDatabase {
    private static Arschloch_Database instance;
    public abstract Arschloch_DAO arschloch_dao();
    public static synchronized  Arschloch_Database getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Arschloch_Database.class,"Arschloch Database").fallbackToDestructiveMigration().build();
        }
        return  instance;
    }
}
