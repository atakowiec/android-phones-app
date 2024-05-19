package pl.pollub.android.app_2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.pollub.android.app_2.model.Phone;
import pl.pollub.android.app_2.model.PhoneRepository;

public class PhoneViewModel extends AndroidViewModel {
    private final LiveData<List<Phone>> allPhones;
    private final PhoneRepository repository;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        this.repository = new PhoneRepository(application);
        this.allPhones = this.repository.getLiveData();
    }

    public void insert(Phone phone) {
        this.repository.insert(phone);
    }

    public void update(Phone phone) {
        this.repository.update(phone);
    }

    public void delete(Phone phone) {
        this.repository.delete(phone);
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }
}
