package Database.Skat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SkatRepository {
    private  Skat_Dao skat_dao;
    private LiveData<List<Skat_Partie>> skat_partien;

    public SkatRepository(Application application){
        Skat_database database = Skat_database.getInstance(application);
        skat_dao = database.Skat_Dao();
        skat_partien = skat_dao.getAllSkat_Partien();
    }

    public void add(Skat_Partie skat_partie){
        new AddSkatAsynctask(skat_dao).execute(skat_partie);
    }

    public void update(Skat_Partie skat_partie){
        new updateSkatAsynctask(skat_dao).execute(skat_partie);
    }

    public void delete (Skat_Partie skat_partie){
        new DeleteSkatAsynctask(skat_dao).execute(skat_partie);
    }

    public LiveData<List<Skat_Partie>> getAllSkatPartien(){
        return skat_partien;
    }

    private static class AddSkatAsynctask extends AsyncTask<Skat_Partie, Void, Void> {
        private Skat_Dao skat_dao;

        private AddSkatAsynctask(Skat_Dao skat_dao){
            this.skat_dao =skat_dao;
        }

        @Override
        protected Void doInBackground(Skat_Partie... skat_parties) {
            skat_dao.addSkat_Partie(skat_parties[0]);
            return null;
        }
    }

    private static class updateSkatAsynctask extends AsyncTask<Skat_Partie, Void, Void> {
        private Skat_Dao skat_dao;

        private updateSkatAsynctask(Skat_Dao skat_dao){
            this.skat_dao =skat_dao;
        }

        @Override
        protected Void doInBackground(Skat_Partie... skat_parties) {
            skat_dao.updateSkat_Partie(skat_parties[0]);
            return null;
        }
    }

    private static class DeleteSkatAsynctask extends AsyncTask<Skat_Partie, Void, Void> {
        private Skat_Dao skat_dao;

        private DeleteSkatAsynctask(Skat_Dao skat_dao){
            this.skat_dao =skat_dao;
        }

        @Override
        protected Void doInBackground(Skat_Partie... skat_parties) {
            skat_dao.deleteSkat_Partie(skat_parties[0]);
            return null;
        }
    }
}