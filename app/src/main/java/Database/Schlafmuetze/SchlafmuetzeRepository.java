package Database.Schlafmuetze;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SchlafmuetzeRepository {
    private  Schlafmuetze_DAO schlafmuetze_dao;
    private LiveData<List<Schlafmuetze_Partie>> schlafmuetze_partien;

    public SchlafmuetzeRepository(Application application){
        Schlafmuetze_Database database = Schlafmuetze_Database.getInstance(application);
        schlafmuetze_dao = database.schlafmuetze_dao();
        schlafmuetze_partien = schlafmuetze_dao.getAllSchlafmuetze_Partien();
    }

    public void add(Schlafmuetze_Partie schlafmuetze_partie){
        new AddSchlafmuetzeAsynctask(schlafmuetze_dao).execute(schlafmuetze_partie);
    }

    public void update(Schlafmuetze_Partie schlafmuetze_partie){
        new updateSchlafmuetzeAsynctask(schlafmuetze_dao).execute(schlafmuetze_partie);
    }

    public void delete (Schlafmuetze_Partie schlafmuetze_partie){
        new DeleteSchlafmuetzeAsynctask(schlafmuetze_dao).execute(schlafmuetze_partie);
    }

    public LiveData<List<Schlafmuetze_Partie>> getAllSchlafmuetzePartien(){ return schlafmuetze_partien; }

    private static class AddSchlafmuetzeAsynctask extends AsyncTask<Schlafmuetze_Partie, Void, Void> {
        private Schlafmuetze_DAO schlafmuetze_dao;

        private AddSchlafmuetzeAsynctask(Schlafmuetze_DAO schlafmuetze_dao){
            this.schlafmuetze_dao =schlafmuetze_dao;
        }

        @Override
        protected Void doInBackground(Schlafmuetze_Partie... schlafmuetze_parties) {
            schlafmuetze_dao.addSchlafmuetze_Partie(schlafmuetze_parties[0]);
            return null;
        }
    }

    private static class updateSchlafmuetzeAsynctask extends AsyncTask<Schlafmuetze_Partie, Void, Void> {
        private Schlafmuetze_DAO schlafmuetze_dao;

        private updateSchlafmuetzeAsynctask(Schlafmuetze_DAO schlafmuetze_dao){
            this.schlafmuetze_dao =schlafmuetze_dao;
        }

        @Override
        protected Void doInBackground(Schlafmuetze_Partie... schlafmuetze_parties) {
            schlafmuetze_dao.updateSchlafmuetze_Partie(schlafmuetze_parties[0]);
            return null;
        }
    }

    private static class DeleteSchlafmuetzeAsynctask extends AsyncTask<Schlafmuetze_Partie, Void, Void> {
        private Schlafmuetze_DAO schlafmuetze_dao;

        private DeleteSchlafmuetzeAsynctask(Schlafmuetze_DAO schlafmuetze_dao){
            this.schlafmuetze_dao =schlafmuetze_dao;
        }

        @Override
        protected Void doInBackground(Schlafmuetze_Partie... schlafmuetze_parties) {
            schlafmuetze_dao.deleteSchlafmuetze_Partie(schlafmuetze_parties[0]);
            return null;
        }
    }

}
