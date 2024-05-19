package pl.pollub.android.app_2.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface PhoneDao {
    @Insert
    void insert(Phone phone);

    @Update
    void update(Phone phone);

    @Query("select * from phones")
    LiveData<List<Phone>> findAllPhones();

    @Delete
    void delete(Phone phone);

    @Query("select * from phones where id=:phoneId")
    Phone getPhone(long phoneId);

    @Query("delete from phones")
    void deleteAll();
}
