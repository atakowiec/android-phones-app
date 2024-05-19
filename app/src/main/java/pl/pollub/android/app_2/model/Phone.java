package pl.pollub.android.app_2.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class Phone {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "manufacturer")
    @NonNull
    private String manufacturer;

    @ColumnInfo(name = "model")
    @NonNull
    private String model;

    @ColumnInfo(name = "android_version")
    @NonNull
    private String androidVersion;

    @ColumnInfo(name = "web_site")
    @NonNull
    private String webSite;

    @Ignore
    public Phone(long id, @NonNull String manufacturer, @NonNull String model, @NonNull String androidVersion, @NonNull String webSite) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.webSite = webSite;
    }

    public Phone(@NonNull String manufacturer, @NonNull String model, @NonNull String androidVersion, @NonNull String webSite) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.webSite = webSite;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getManufacturer() {
        return manufacturer;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    @NonNull
    public String getAndroidVersion() {
        return androidVersion;
    }

    @NonNull
    public String getWebSite() {
        return webSite;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setManufacturer(@NonNull String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    public void setAndroidVersion(@NonNull String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public void setWebSite(@NonNull String webSite) {
        this.webSite = webSite;
    }
}
