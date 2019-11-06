package Database.Kartenspiele;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nachtaktiverhalbaffe.kartenspiele.R;

@Database(entities = {Kartenspiel.class}, version = 1, exportSchema = false)
public abstract class Kartenspiel_Database extends RoomDatabase {
    public abstract Kartenspiele_DAO kartenspiele_dao();
    private static Kartenspiel_Database instance;

    public static synchronized  Kartenspiel_Database getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Kartenspiel_Database.class,"Spiele_finden Database").fallbackToDestructiveMigration().build();
        }
        return  instance;
    }
}