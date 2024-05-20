package pl.pollub.android.app_2;


import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.pollub.android.app_2.model.Phone;

public class PhonesListActivity extends AppCompatActivity {
    public static String PHONE_ID_KEY = "PHONE_ID_KEY";
    public static String PHONE_MANUFACTURER_KEY = "PHONE_MANUFACTURER_KEY";
    public static String PHONE_MODEL_KEY = "PHONE_MODEL_KEY";
    public static String PHONE_ANDROID_VERSION_KEY = "PHONE_ANDROID_VERSION_KEY";
    public static String PHONE_WEB_SITE_KEY = "PHONE_WEB_SITE_KEY";

    private PhoneInfoListAdapter adapter;
    private PhoneViewModel phoneViewModel;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones_list);

        this.launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::upsertPhone);
        this.adapter = new PhoneInfoListAdapter(this);
        this.adapter.setListener(this::editAddPhone);
        this.phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        this.phoneViewModel.getAllPhones().observe(this, phones -> this.adapter.setPhoneList(phones));

        RecyclerView phoneListRv = this.findViewById(R.id.phones_list_rv);
        phoneListRv.setAdapter(this.adapter);
        phoneListRv.setLayoutManager(new LinearLayoutManager(this));

        this.findViewById(R.id.add_phone_fab).setOnClickListener(view -> this.addNewPhone());

        ItemTouchHelper itemTouchHelper = getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(phoneListRv);
    }

    @NonNull
    private ItemTouchHelper getItemTouchHelper() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(LEFT, LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    List<Phone> allPhones = PhonesListActivity.this.phoneViewModel.getAllPhones().getValue();
                    if (allPhones == null) return; // this should never happen but just in case

                    Phone phoneToDelete = allPhones.get(viewHolder.getAdapterPosition());
                    PhonesListActivity.this.phoneViewModel.delete(phoneToDelete);
                } catch (Exception e) {
                    Log.e("PhonesListActivity", "Error while deleting phone", e);
                    Toast.makeText(PhonesListActivity.this, R.string.phone_delete_error, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return new ItemTouchHelper(callback);
    }

    private void showConfirmationPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.confirmation_menu, null);
        builder.setView(view);

        Button confirmButton = view.findViewById(R.id.delete_all_confirm_btn);
        Button cancelButton = view.findViewById(R.id.btn_cancel);

        AlertDialog dialog = builder.create();
        dialog.show();

        confirmButton.setOnClickListener(v -> {
            dialog.dismiss();
            PhonesListActivity.this.phoneViewModel.deleteAll();
            Toast.makeText(PhonesListActivity.this, R.string.all_phones_deleted, Toast.LENGTH_SHORT).show();
        });
        cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_phones) {
            this.showConfirmationPopup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addNewPhone() {
        Bundle bundle = new Bundle();
        bundle.putLong(PHONE_ID_KEY, 0);
        Intent intent = new Intent(this, PhoneActivity.class);
        intent.putExtras(bundle);
        this.launcher.launch(intent);
    }

    private void editAddPhone(int position) {
        Phone currentPhone = this.adapter.getPhoneList().get(position);
        Bundle bundle = new Bundle();

        bundle.putLong(PHONE_ID_KEY, currentPhone.getId());
        bundle.putString(PHONE_MANUFACTURER_KEY, currentPhone.getManufacturer());
        bundle.putString(PHONE_MODEL_KEY, currentPhone.getModel());
        bundle.putString(PHONE_ANDROID_VERSION_KEY, currentPhone.getAndroidVersion());
        bundle.putString(PHONE_WEB_SITE_KEY, currentPhone.getWebSite());

        Intent intent = new Intent(this, PhoneActivity.class);
        intent.putExtras(bundle);

        this.launcher.launch(intent);
    }

    private void upsertPhone(ActivityResult o) {
        if (o.getResultCode() != RESULT_OK) return;

        Intent intent = o.getData();
        if (intent == null) return;
        Bundle bundle = intent.getExtras();

        if (bundle == null) return;

        long id = bundle.getLong(PHONE_ID_KEY);
        String manufacturer = bundle.getString(PHONE_MANUFACTURER_KEY);
        String model = bundle.getString(PHONE_MODEL_KEY);
        String androidVersion = bundle.getString(PHONE_ANDROID_VERSION_KEY);
        String webSite = bundle.getString(PHONE_WEB_SITE_KEY);

        // I do not need to show any message to the user, because validation was performed in the PhoneActivity
        if (manufacturer == null || model == null || androidVersion == null || webSite == null)
            return;

        if (id == 0) {
            this.phoneViewModel.insert(new Phone(manufacturer, model, androidVersion, webSite));
        } else {
            this.phoneViewModel.update(new Phone(id, manufacturer, model, androidVersion, webSite));
        }
    }
}