package Database.Kartenspiele;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class KartenspieleRepository {
    private  Kartenspiele_DAO kartenspiele_dao;
    private LiveData<List<Kartenspiel>>kartenspiele;

    public KartenspieleRepository(Application application){
        Kartenspiel_Database database = Kartenspiel_Database.getInstance(application);
        kartenspiele_dao = database.kartenspiele_dao();
        kartenspiele= kartenspiele_dao.getAllKartenspiele();
    }

    public void add(Kartenspiel kartenspiel){
        new AddKartenspieleAsynctask(kartenspiele_dao).execute(kartenspiel);
    }

    public void update(Kartenspiel kartenspiel){
        new updateKartenspieleAsynctask(kartenspiele_dao).execute(kartenspiel);
    }

    public void delete (Kartenspiel kartenspiel){
        new DeleteKartenspieleAsynctask(kartenspiele_dao).execute(kartenspiel);
    }

    public LiveData<List<Kartenspiel>> getAllKartenspiele(){
        return kartenspiele;
    }

    private static class AddKartenspieleAsynctask extends AsyncTask<Kartenspiel, Void, Void> {
        private Kartenspiele_DAO kartenspiele_dao;

        private AddKartenspieleAsynctask(Kartenspiele_DAO kartenspiele_dao){
            this.kartenspiele_dao =kartenspiele_dao;
        }

        @Override
        protected Void doInBackground(Kartenspiel... kartenspiele) {
            kartenspiele_dao.addKartenspiel(kartenspiele[0]);
            return null;
        }
    }

    private static class updateKartenspieleAsynctask extends AsyncTask<Kartenspiel, Void, Void> {
        private Kartenspiele_DAO kartenspiele_dao;

        private updateKartenspieleAsynctask(Kartenspiele_DAO kartenspiele_dao){
            this.kartenspiele_dao =kartenspiele_dao;
        }

        @Override
        protected Void doInBackground(Kartenspiel... kartenspiele) {
            kartenspiele_dao.updateKartenspiel(kartenspiele[0]);
            return null;
        }
    }

    private static class DeleteKartenspieleAsynctask extends AsyncTask<Kartenspiel, Void, Void> {
        private Kartenspiele_DAO kartenspiele_dao;

        private DeleteKartenspieleAsynctask(Kartenspiele_DAO kartenspiele_dao){
            this.kartenspiele_dao =kartenspiele_dao;
        }

        @Override
        protected Void doInBackground(Kartenspiel... kartenspiele) {
            kartenspiele_dao.deleteKartenspiel(kartenspiele[0]);
            return null;
        }
    }




}
