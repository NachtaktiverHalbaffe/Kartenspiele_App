package Database.Schwimmen;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SchwimmenRepository {
    private  Schwimmen_DAO schwimmen_dao;
    private LiveData<List<Schwimmen_Partie>> schwimmen_partien;

    public SchwimmenRepository(Application application){
        Schwimmen_Database database = Schwimmen_Database.getInstance(application);
        schwimmen_dao = database.schwimmen_dao();
        schwimmen_partien = schwimmen_dao.getAllSchwimmen_Partien();
    }

    public void add(Schwimmen_Partie schwimmen_partie){
        new AddSchwimmenAsynctask(schwimmen_dao).execute(schwimmen_partie);
    }

    public void update(Schwimmen_Partie schwimmen_partie){
        new updateSchwimmenAsynctask(schwimmen_dao).execute(schwimmen_partie);
    }

    public void delete (Schwimmen_Partie schwimmen_partie){
        new DeleteSchwimmenAsynctask(schwimmen_dao).execute(schwimmen_partie);
    }

    public LiveData<List<Schwimmen_Partie>> getAllSchwimmenPartien(){
        return schwimmen_partien;
    }

    private static class AddSchwimmenAsynctask extends AsyncTask<Schwimmen_Partie, Void, Void> {
        private Schwimmen_DAO schwimmen_dao;

        private AddSchwimmenAsynctask(Schwimmen_DAO schwimmen_dao){
            this.schwimmen_dao =schwimmen_dao;
        }

        @Override
        protected Void doInBackground(Schwimmen_Partie... schwimmen_parties) {
            schwimmen_dao.addSchwimmen_Partie(schwimmen_parties[0]);
            return null;
        }
    }

    private static class updateSchwimmenAsynctask extends AsyncTask<Schwimmen_Partie, Void, Void> {
        private Schwimmen_DAO schwimmen_dao;

        private updateSchwimmenAsynctask(Schwimmen_DAO schwimmen_dao){
            this.schwimmen_dao =schwimmen_dao;
        }

        @Override
        protected Void doInBackground(Schwimmen_Partie... schwimmen_parties) {
            schwimmen_dao.updateSchwimmen_Partie(schwimmen_parties[0]);
            return null;
        }
    }

    private static class DeleteSchwimmenAsynctask extends AsyncTask<Schwimmen_Partie, Void, Void> {
        private Schwimmen_DAO schwimmen_dao;

        private DeleteSchwimmenAsynctask(Schwimmen_DAO schwimmen_dao){
            this.schwimmen_dao =schwimmen_dao;
        }

        @Override
        protected Void doInBackground(Schwimmen_Partie... schwimmen_parties) {
            schwimmen_dao.deleteSchwimmen_Partie(schwimmen_parties[0]);
            return null;
        }
    }


}