package pl.pollub.android.app_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.pollub.android.app_2.model.Phone;

public class PhoneInfoListAdapter extends RecyclerView.Adapter<PhoneInfoViewHolder> {
    private Activity contextActivity;
    private List<Phone> phoneList;
    private OnItemClickListener listener;

    public PhoneInfoListAdapter(Activity contextActivity) {
        this.contextActivity = contextActivity;
        this.phoneList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PhoneInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.contextActivity.getLayoutInflater().inflate(R.layout.phone_info_row, parent, false);
        PhoneInfoViewHolder phoneInfoViewHolder = new PhoneInfoViewHolder(itemView);
        if (this.listener != null) {
            phoneInfoViewHolder.setOnItemClickListener(this.listener);
        }
        return phoneInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneInfoViewHolder holder, int position) {
        Phone currentPhone = this.phoneList.get(position);
        holder.getManufacturerInfoTv().setText(currentPhone.getManufacturer());
        holder.getModelInfoTv().setText(currentPhone.getModel());
    }

    @Override
    public int getItemCount() {
        return this.phoneList.size();
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
