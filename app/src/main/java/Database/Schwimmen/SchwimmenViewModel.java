package Database.Schwimmen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SchwimmenViewModel extends AndroidViewModel {
    private SchwimmenRepository repository;
    private LiveData<List<Schwimmen_Partie>> schwimmen_partien;

    public SchwimmenViewModel(@NonNull Application application) {
        super(application);
        repository = new SchwimmenRepository(application);
        schwimmen_partien = repository.getAllSchwimmenPartien();
    }

    public void add(Schwimmen_Partie schwimmen_partie){
        repository.add(schwimmen_partie);
    }

    public void update(Schwimmen_Partie schwimmen_partie){
        repository.update(schwimmen_partie);
    }

    public void delete(Schwimmen_Partie schwimmen_partie){
        repository.delete(schwimmen_partie);
    }

    public LiveData<List<Schwimmen_Partie>> getAllSchwimmen_Partien(){
        return schwimmen_partien;
    }
}
