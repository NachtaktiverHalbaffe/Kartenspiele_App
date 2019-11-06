package Database.Kartenspiele;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class KartenspieleViewModel extends AndroidViewModel {
    private KartenspieleRepository repository;
    private LiveData<List<Kartenspiel>> kartenspiele;

    public KartenspieleViewModel(@NonNull Application application) {
        super(application);
        repository = new KartenspieleRepository(application);
        kartenspiele = repository.getAllKartenspiele();
    }

    public void add(Kartenspiel kartenspiel){
        repository.add(kartenspiel);
    }

    public void update(Kartenspiel kartenspiel){ repository.update(kartenspiel);
    }

    public void delete(Kartenspiel kartenspiel){
        repository.delete(kartenspiel);
    }

    public LiveData<List<Kartenspiel>> getAllKartenspiele(){
        return kartenspiele;
    }
}
