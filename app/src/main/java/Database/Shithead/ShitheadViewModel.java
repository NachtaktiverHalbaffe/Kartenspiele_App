package Database.Shithead;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ShitheadViewModel extends AndroidViewModel {
    private ShitheadRepository repository;
    private LiveData<List<Shithead_Partie>> shithead_partien;

    public ShitheadViewModel(@NonNull Application application) {
        super(application);
        repository = new ShitheadRepository(application);
        shithead_partien = repository.getAllShitheadPartien();
    }

    public void add(Shithead_Partie shithead_partie){
        repository.add(shithead_partie);
    }

    public void update(Shithead_Partie shithead_partie){
        repository.update(shithead_partie);
    }

    public void delete(Shithead_Partie shithead_partie){
        repository.delete(shithead_partie);
    }

    public LiveData<List<Shithead_Partie>> getAllShithead_Partien(){
        return shithead_partien;
    }
}
