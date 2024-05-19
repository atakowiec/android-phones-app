package pl.pollub.android.app_2.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private final PhoneDao phoneDao;
    private final LiveData<List<Phone>> liveData;

    public PhoneRepository(Application application) {
        PhonesDatabase db = PhonesDatabase.getPhonesDatabase(application);
        this.phoneDao = db.getPhoneDao();
        this.liveData = this.phoneDao.findAllPhones();
    }

    public LiveData<List<Phone>> getLiveData() {
        return liveData;
    }

    public void insert(Phone phone) {
        PhonesDatabase.databaseWriteExecutor.execute(() -> this.phoneDao.insert(phone));
    }

    public void update(Phone phone) {
        PhonesDatabase.databaseWriteExecutor.execute(() -> this.phoneDao.update(phone));
    }

    public void delete(Phone phone) {
        PhonesDatabase.databaseWriteExecutor.execute(() -> this.phoneDao.delete(phone));
    }

    public Phone getPhone(long phoneId) {
        return this.phoneDao.getPhone(phoneId);
    }
}
