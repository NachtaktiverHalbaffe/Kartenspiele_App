package Database.Durak;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DurakViewModel extends AndroidViewModel {
    private DurakRepository repository;
    private LiveData<List<Durak_Partie>> durak_partien;

    public DurakViewModel(@NonNull Application application) {
        super(application);
        repository = new DurakRepository(application);
        durak_partien = repository.getAllDurakPartien();
    }

    public void add(Durak_Partie durak_partie){
        repository.add(durak_partie);
    }

    public void update(Durak_Partie durak_partie){
        repository.update(durak_partie);
    }

    public void delete(Durak_Partie durak_partie){
        repository.delete(durak_partie);
    }

    public LiveData<List<Durak_Partie>> getAllDurak_Partien(){
        return durak_partien;
    }
}