package pl.pollub.android.app_2;

import static pl.pollub.android.app_2.PhonesListActivity.PHONE_ANDROID_VERSION_KEY;
import static pl.pollub.android.app_2.PhonesListActivity.PHONE_ID_KEY;
import static pl.pollub.android.app_2.PhonesListActivity.PHONE_MANUFACTURER_KEY;
import static pl.pollub.android.app_2.PhonesListActivity.PHONE_MODEL_KEY;
import static pl.pollub.android.app_2.PhonesListActivity.PHONE_WEB_SITE_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class PhoneActivity extends AppCompatActivity {
    private EditText manufacturerEt;
    private EditText modelEt;
    private EditText androidVersionEt;
    private EditText webSiteEt;
    private Button webSiteBt;
    private Button cancelBt;
    private Button saveBt;
    private PhoneViewModel phoneViewModel;
    private long phoneId;
    private String phoneManufacturer;
    private String phoneModel;
    private String phoneAndroidVersion;
    private String phoneWebSite;
    private Intent editIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        this.phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        this.manufacturerEt = this.findViewById(R.id.manufacturer_et);
        this.modelEt = this.findViewById(R.id.model_et);
        this.androidVersionEt = this.findViewById(R.id.android_version_et);
        this.webSiteEt = this.findViewById(R.id.web_site_et);
        this.webSiteBt = this.findViewById(R.id.web_site_bt);
        this.cancelBt = this.findViewById(R.id.cancel_bt);
        this.saveBt = this.findViewById(R.id.save_bt);
        loadPhone();

        this.saveBt.setOnClickListener(view -> savePhone());
        this.cancelBt.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void loadPhone() {
        Intent intent = this.getIntent();
        if (intent == null) return;

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        long id = bundle.getLong(PHONE_ID_KEY);
        this.phoneId = id;
        if (id > 0) {
            this.phoneManufacturer = bundle.getString(PHONE_MANUFACTURER_KEY);
            this.phoneModel = bundle.getString(PHONE_MODEL_KEY);
            this.phoneAndroidVersion = bundle.getString(PHONE_ANDROID_VERSION_KEY);
            this.phoneWebSite = bundle.getString(PHONE_WEB_SITE_KEY);
            this.manufacturerEt.setText(String.valueOf(this.phoneManufacturer));
            this.modelEt.setText(this.phoneModel);
            this.androidVersionEt.setText(this.phoneAndroidVersion);
            this.webSiteEt.setText(this.phoneWebSite);
        }
    }

    private void savePhone() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent();

        // todo add validation

        bundle.putLong(PHONE_ID_KEY, this.phoneId);
        bundle.putString(PHONE_MANUFACTURER_KEY, this.manufacturerEt.getText().toString());
        bundle.putString(PHONE_MODEL_KEY, this.modelEt.getText().toString());
        bundle.putString(PHONE_ANDROID_VERSION_KEY, this.androidVersionEt.getText().toString());
        bundle.putString(PHONE_WEB_SITE_KEY, this.webSiteEt.getText().toString());

        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}