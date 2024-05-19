package pl.pollub.android.app_2;

import static pl.pollub.android.app_2.PhonesListActivity.PHONE_ANDROID_VERSION_KEY;
import static pl.pollub.android.app_2.PhonesListActivity.PHONE_ID_KEY;
import static pl.pollub.android.app_2.PhonesListActivity.PHONE_MANUFACTURER_KEY;
import static pl.pollub.android.app_2.PhonesListActivity.PHONE_MODEL_KEY;
import static pl.pollub.android.app_2.PhonesListActivity.PHONE_WEB_SITE_KEY;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pl.pollub.android.app_2.util.UrlUtil;

public class PhoneActivity extends AppCompatActivity {
    private PhoneViewModel phoneViewModel;
    private Intent editIntent;
    private EditText manufacturerEt;
    private EditText modelEt;
    private EditText androidVersionEt;
    private EditText webSiteEt;
    private long phoneId;
    private String phoneWebSite;
    private TextView manufacturerErrorTv;
    private TextView modelErrorTv;
    private TextView androidVersionErrorTv;
    private TextView webSiteErrorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        this.phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        this.manufacturerEt = this.findViewById(R.id.manufacturer_et);
        this.modelEt = this.findViewById(R.id.model_et);
        this.androidVersionEt = this.findViewById(R.id.android_version_et);
        this.webSiteEt = this.findViewById(R.id.web_site_et);

        this.manufacturerErrorTv = this.findViewById(R.id.manufacturer_error_tv);
        this.modelErrorTv = this.findViewById(R.id.model_error_tv);
        this.androidVersionErrorTv = this.findViewById(R.id.android_version_error_tv);
        this.webSiteErrorTv = this.findViewById(R.id.web_site_error_tv);

        loadPhone();

        this.findViewById(R.id.save_bt).setOnClickListener(view -> savePhone());
        this.findViewById(R.id.cancel_bt).setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        this.findViewById(R.id.web_site_bt).setOnClickListener(view -> {
            // app will take current value from the web site field and try to open it in the browser
            if (phoneWebSite == null || phoneWebSite.isEmpty()) {
                Toast.makeText(this, R.string.open_web_site_error_empty, Toast.LENGTH_SHORT).show();
                return;
            }
            if (UrlUtil.isInvalid(phoneWebSite)) {
                Toast.makeText(this, R.string.open_web_site_error_invalid, Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(phoneWebSite));
            startActivity(intent);
        });
    }

    private void loadPhone() {
        Intent intent = this.getIntent();
        if (intent == null) return;

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        long id = bundle.getLong(PHONE_ID_KEY);
        this.phoneId = id;
        if (id <= 0) return;

        String phoneManufacturer = bundle.getString(PHONE_MANUFACTURER_KEY);
        String phoneModel = bundle.getString(PHONE_MODEL_KEY);
        String phoneAndroidVersion = bundle.getString(PHONE_ANDROID_VERSION_KEY);
        this.phoneWebSite = bundle.getString(PHONE_WEB_SITE_KEY);
        this.manufacturerEt.setText(String.valueOf(phoneManufacturer));
        this.modelEt.setText(phoneModel);
        this.androidVersionEt.setText(phoneAndroidVersion);
        this.webSiteEt.setText(phoneWebSite);
    }

    private void savePhone() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent();

        String manufacturer = this.manufacturerEt.getText().toString();
        String model = this.modelEt.getText().toString();
        String androidVersion = this.androidVersionEt.getText().toString();
        String webSite = this.webSiteEt.getText().toString();

        if (!validate(manufacturer, model, androidVersion, webSite)) return;

        bundle.putLong(PHONE_ID_KEY, this.phoneId);
        bundle.putString(PHONE_MANUFACTURER_KEY, manufacturer);
        bundle.putString(PHONE_MODEL_KEY, model);
        bundle.putString(PHONE_ANDROID_VERSION_KEY, androidVersion);
        bundle.putString(PHONE_WEB_SITE_KEY, webSite);

        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean validate(@Nullable String manufacturer, @Nullable String model, @Nullable String androidVersion, @Nullable String webSite) {
        clearErrors();
        boolean isValid = true;

        if (manufacturer == null || manufacturer.isEmpty()) {
            setError(this.manufacturerErrorTv, getString(R.string.error_empty_manufacturer));
            isValid = false;
        }

        if (model == null || model.isEmpty()) {
            setError(this.modelErrorTv, getString(R.string.error_empty_model));
            isValid = false;
        }

        if (androidVersion == null || androidVersion.isEmpty()) {
            setError(this.androidVersionErrorTv, getString(R.string.error_empty_android_version));
            isValid = false;
        }

        if (webSite == null || webSite.isEmpty()) {
            setError(this.webSiteErrorTv, getString(R.string.error_empty_web_site));
            isValid = false;
        } else if (UrlUtil.isInvalid(webSite)) {
            setError(this.webSiteErrorTv, getString(R.string.error_invalid_web_site));
            isValid = false;
        }

        return isValid;
    }

    private void setError(TextView errorTv, String message) {
        errorTv.setText(message);
        errorTv.setVisibility(View.VISIBLE);
    }

    private void clearErrors() {
        this.manufacturerErrorTv.setText("");
        this.modelErrorTv.setText("");
        this.androidVersionErrorTv.setText("");
        this.webSiteErrorTv.setText("");

        this.manufacturerErrorTv.setVisibility(View.GONE);
        this.modelErrorTv.setVisibility(View.GONE);
        this.androidVersionErrorTv.setVisibility(View.GONE);
        this.webSiteErrorTv.setVisibility(View.GONE);
    }
}