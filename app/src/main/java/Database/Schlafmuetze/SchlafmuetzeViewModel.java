package Database.Schlafmuetze;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SchlafmuetzeViewModel extends AndroidViewModel {
    private SchlafmuetzeRepository repository;
    private LiveData<List<Schlafmuetze_Partie>> schlafmuetze_partien;

    public SchlafmuetzeViewModel(@NonNull Application application) {
        super(application);
        repository = new SchlafmuetzeRepository(application);
        schlafmuetze_partien = repository.getAllSchlafmuetzePartien();
    }

    public void add(Schlafmuetze_Partie schlafmuetze_partie){
        repository.add(schlafmuetze_partie);
    }

    public void update(Schlafmuetze_Partie schlafmuetze_partie){
        repository.update(schlafmuetze_partie);
    }

    public void delete(Schlafmuetze_Partie schlafmuetze_partie){
        repository.delete(schlafmuetze_partie);
    }

    public LiveData<List<Schlafmuetze_Partie>> getAllSchlafmuetze_Partien(){
        return schlafmuetze_partien;
    }
}
