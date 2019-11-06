package Database.Golfen;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GolfenRepository {
    private  Golfen_Partie_Dao golfen_dao;
    private LiveData<List<Golfen_Partie>> golfen_partien;

    public GolfenRepository(Application application){
        Golf_database database = Golf_database.getInstance(application);
        golfen_dao = database.golfen_spieler_dao();
        golfen_partien = golfen_dao.getAllGolfen_Partien();
    }

    public void add(Golfen_Partie golfen_partie){
        new AddGolfenAsynctask(golfen_dao).execute(golfen_partie);
    }

    public void update(Golfen_Partie golfen_partie){
        new updateGolfenAsynctask(golfen_dao).execute(golfen_partie);
    }

    public void delete (Golfen_Partie golfen_partie){
        new DeleteGolfenAsynctask(golfen_dao).execute(golfen_partie);
    }

    public LiveData<List<Golfen_Partie>> getAllGolfenPartien(){
        return golfen_partien;
    }

    private static class AddGolfenAsynctask extends AsyncTask<Golfen_Partie, Void, Void> {
        private Golfen_Partie_Dao golfen_dao;

        private AddGolfenAsynctask(Golfen_Partie_Dao golfen_dao){
            this.golfen_dao =golfen_dao;
        }

        @Override
        protected Void doInBackground(Golfen_Partie... golfen_parties) {
            golfen_dao.addGolfen_Partie(golfen_parties[0]);
            return null;
        }
    }

    private static class updateGolfenAsynctask extends AsyncTask<Golfen_Partie, Void, Void> {
        private Golfen_Partie_Dao golfen_dao;

        private updateGolfenAsynctask(Golfen_Partie_Dao golfen_dao){
            this.golfen_dao =golfen_dao;
        }

        @Override
        protected Void doInBackground(Golfen_Partie... golfen_parties) {
            golfen_dao.updateGolfen_Partie(golfen_parties[0]);
            return null;
        }
    }

    private static class DeleteGolfenAsynctask extends AsyncTask<Golfen_Partie, Void, Void> {
        private Golfen_Partie_Dao golfen_dao;

        private DeleteGolfenAsynctask(Golfen_Partie_Dao golfen_dao){
            this.golfen_dao =golfen_dao;
        }

        @Override
        protected Void doInBackground(Golfen_Partie... golfen_parties) {
            golfen_dao.deleteGolf_Partie(golfen_parties[0]);
            return null;
        }
    }


}
