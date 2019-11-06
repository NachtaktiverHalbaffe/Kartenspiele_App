package Database.Shithead;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ShitheadRepository {
    private  Shithead_DAO shithead_dao;
    private LiveData<List<Shithead_Partie>> shithead_partien;

    public ShitheadRepository(Application application){
        Shithead_Database database = Shithead_Database.getInstance(application);
        shithead_dao = database.shithead_dao();
        shithead_partien = shithead_dao.getAllShithead_Partien();
    }

    public void add(Shithead_Partie shithead_partie){
        new AddShitheadAsynctask(shithead_dao).execute(shithead_partie);
    }

    public void update(Shithead_Partie shithead_partie){
        new updateShitheadAsynctask(shithead_dao).execute(shithead_partie);
    }

    public void delete (Shithead_Partie shithead_partie){
        new DeleteShitheadAsynctask(shithead_dao).execute(shithead_partie);
    }

    public LiveData<List<Shithead_Partie>> getAllShitheadPartien(){ return shithead_partien; }

    private static class AddShitheadAsynctask extends AsyncTask<Shithead_Partie, Void, Void> {
        private Shithead_DAO shithead_dao;

        private AddShitheadAsynctask(Shithead_DAO shithead_dao){
            this.shithead_dao =shithead_dao;
        }

        @Override
        protected Void doInBackground(Shithead_Partie... shithead_parties) {
            shithead_dao.addShithead_Partie(shithead_parties[0]);
            return null;
        }
    }

    private static class updateShitheadAsynctask extends AsyncTask<Shithead_Partie, Void, Void> {
        private Shithead_DAO shithead_dao;

        private updateShitheadAsynctask(Shithead_DAO shithead_dao){
            this.shithead_dao =shithead_dao;
        }

        @Override
        protected Void doInBackground(Shithead_Partie... shithead_parties) {
            shithead_dao.updateShithead_Partie(shithead_parties[0]);
            return null;
        }
    }

    private static class DeleteShitheadAsynctask extends AsyncTask<Shithead_Partie, Void, Void> {
        private Shithead_DAO shithead_dao;

        private DeleteShitheadAsynctask(Shithead_DAO shithead_dao){
            this.shithead_dao =shithead_dao;
        }

        @Override
        protected Void doInBackground(Shithead_Partie... shithead_parties) {
            shithead_dao.deleteShithead_Partie(shithead_parties[0]);
            return null;
        }
    }
}
