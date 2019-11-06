package Database.Skat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SkatViewModel extends AndroidViewModel {
    private SkatRepository repository;
    private LiveData<List<Skat_Partie>> skat_partien;

    public SkatViewModel(@NonNull Application application) {
        super(application);
        repository = new SkatRepository(application);
        skat_partien = repository.getAllSkatPartien();
    }

    public void add(Skat_Partie skat_partie){
        repository.add(skat_partie);
    }

    public void update(Skat_Partie skat_partie){
        repository.update(skat_partie);
    }

    public void delete(Skat_Partie skat_partie){
        repository.delete(skat_partie);
    }

    public LiveData<List<Skat_Partie>> getAllSkat_Partien(){
        return skat_partien;
    }
}
