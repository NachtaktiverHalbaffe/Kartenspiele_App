package Database.Golfen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GolfenViewModel extends AndroidViewModel {
    private GolfenRepository repository;
    private LiveData<List<Golfen_Partie>> golfen_partien;

    public GolfenViewModel(@NonNull Application application) {
        super(application);
        repository = new GolfenRepository(application);
        golfen_partien = repository.getAllGolfenPartien();
    }

    public void add(Golfen_Partie golfen_partie){
        repository.add(golfen_partie);
    }

    public void update(Golfen_Partie golfen_partie){
        repository.update(golfen_partie);
    }

    public void delete(Golfen_Partie golfen_partie){
        repository.delete(golfen_partie);
    }

    public LiveData<List<Golfen_Partie>> getAllGolfen_Partien(){
        return golfen_partien;
    }
}

