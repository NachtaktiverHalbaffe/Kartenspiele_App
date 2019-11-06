package Database.Arschloch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ArschlochViewModel extends AndroidViewModel {
    private ArschlochRepository repository;
    private LiveData<List<Arschloch_Partie>> arschloch_partien;

    public ArschlochViewModel(@NonNull Application application) {
        super(application);
        repository = new ArschlochRepository(application);
        arschloch_partien = repository.getAllArschlochPartien();
    }

    public void add(Arschloch_Partie arschloch_partie){
        repository.add(arschloch_partie);
    }

    public void update(Arschloch_Partie arschloch_partie){
        repository.update(arschloch_partie);
    }

    public void delete(Arschloch_Partie arschloch_partie){
        repository.delete(arschloch_partie);
    }

    public LiveData<List<Arschloch_Partie>> getAllArschloch_Partien(){
        return arschloch_partien;
    }
}
