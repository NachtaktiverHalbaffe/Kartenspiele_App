package Database.Arschloch;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ArschlochRepository {
    private  Arschloch_DAO arschloch_dao;
    private LiveData<List<Arschloch_Partie>> arschloch_partien;

    public ArschlochRepository(Application application){
        Arschloch_Database database = Arschloch_Database.getInstance(application);
        arschloch_dao = database.arschloch_dao();
        arschloch_partien = arschloch_dao.getAllArschloch_Partien();
    }

    public void add(Arschloch_Partie arschloch_partie){
        new AddArschlochAsynctask(arschloch_dao).execute(arschloch_partie);
    }

    public void update(Arschloch_Partie arschloch_partie){
        new updateArschlochAsynctask(arschloch_dao).execute(arschloch_partie);
    }

    public void delete (Arschloch_Partie arschloch_partie){
        new DeleteArschlochAsynctask(arschloch_dao).execute(arschloch_partie);
    }

    public LiveData<List<Arschloch_Partie>> getAllArschlochPartien(){
        return arschloch_partien;
    }

    private static class AddArschlochAsynctask extends AsyncTask<Arschloch_Partie, Void, Void>{
        private Arschloch_DAO arschloch_dao;

        private AddArschlochAsynctask(Arschloch_DAO arschloch_dao){
            this.arschloch_dao =arschloch_dao;
        }

        @Override
        protected Void doInBackground(Arschloch_Partie... arschloch_parties) {
            arschloch_dao.addArschloch_Partie(arschloch_parties[0]);
            return null;
        }
    }

    private static class updateArschlochAsynctask extends AsyncTask<Arschloch_Partie, Void, Void>{
        private Arschloch_DAO arschloch_dao;

        private updateArschlochAsynctask(Arschloch_DAO arschloch_dao){
            this.arschloch_dao =arschloch_dao;
        }

        @Override
        protected Void doInBackground(Arschloch_Partie... arschloch_parties) {
            arschloch_dao.updateArschloch_Partie(arschloch_parties[0]);
            return null;
        }
    }

    private static class DeleteArschlochAsynctask extends AsyncTask<Arschloch_Partie, Void, Void>{
        private Arschloch_DAO arschloch_dao;

        private DeleteArschlochAsynctask(Arschloch_DAO arschloch_dao){
            this.arschloch_dao =arschloch_dao;
        }

        @Override
        protected Void doInBackground(Arschloch_Partie... arschloch_parties) {
            arschloch_dao.deleteArschloch_Partie(arschloch_parties[0]);
            return null;
        }
    }
}
