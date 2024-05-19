package pl.pollub.android.app_2.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
abstract class PhonesDatabase extends RoomDatabase {
    private static volatile PhonesDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PhoneDao dao = INSTANCE.getPhoneDao();
                dao.insert(new Phone("google", "Pixel 6a", "Android 14", "google.com"));
                dao.insert(new Phone("google", "Pixel 7a", "Android 14", "google.com"));
                dao.insert(new Phone("google", "Pixel 8a", "Android 15", "google.com"));
            });
        }
    };

    public static PhonesDatabase getPhonesDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhonesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhonesDatabase.class, "phones_database_czw_2")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract PhoneDao getPhoneDao();
}