package Database.Durak;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DurakRepository {
    private  Durak_DAO durak_dao;
    private LiveData<List<Durak_Partie>> durak_partien;

    public DurakRepository(Application application){
        Durak_Database database = Durak_Database.getInstance(application);
        durak_dao = database.durak_dao();
        durak_partien = durak_dao.getAllDurak_Partien();
    }

    public void add(Durak_Partie durak_partie){
        new AddDurakAsynctask(durak_dao).execute(durak_partie);
    }

    public void update(Durak_Partie durak_partie){
        new updateDurakAsynctask(durak_dao).execute(durak_partie);
    }

    public void delete (Durak_Partie durak_partie){
        new DeleteDurakAsynctask(durak_dao).execute(durak_partie);
    }

    public LiveData<List<Durak_Partie>> getAllDurakPartien(){ return durak_partien; }

    private static class AddDurakAsynctask extends AsyncTask<Durak_Partie, Void, Void> {
        private Durak_DAO durak_dao;

        private AddDurakAsynctask(Durak_DAO durak_dao){
            this.durak_dao =durak_dao;
        }

        @Override
        protected Void doInBackground(Durak_Partie... durak_parties) {
            durak_dao.addDurak_Partie(durak_parties[0]);
            return null;
        }
    }

    private static class updateDurakAsynctask extends AsyncTask<Durak_Partie, Void, Void> {
        private Durak_DAO durak_dao;

        private updateDurakAsynctask(Durak_DAO durak_dao){
            this.durak_dao =durak_dao;
        }

        @Override
        protected Void doInBackground(Durak_Partie... durak_parties) {
            durak_dao.updateDurak_Partie(durak_parties[0]);
            return null;
        }
    }

    private static class DeleteDurakAsynctask extends AsyncTask<Durak_Partie, Void, Void> {
        private Durak_DAO durak_dao;

        private DeleteDurakAsynctask(Durak_DAO durak_dao){
            this.durak_dao =durak_dao;
        }

        @Override
        protected Void doInBackground(Durak_Partie... durak_parties) {
            durak_dao.deleteDurak_Partie(durak_parties[0]);
            return null;
        }
    }
}
