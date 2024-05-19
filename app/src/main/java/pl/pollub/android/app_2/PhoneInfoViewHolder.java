package pl.pollub.android.app_2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhoneInfoViewHolder extends RecyclerView.ViewHolder {
    private final TextView manufacturerInfoTv;
    private final TextView modelInfoTv;

    public PhoneInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.manufacturerInfoTv = itemView.findViewById(R.id.manufacturer_info_tv);
        this.modelInfoTv = itemView.findViewById(R.id.model_info_tv);
    }

    public TextView getManufacturerInfoTv() {
        return manufacturerInfoTv;
    }

    public TextView getModelInfoTv() {
        return modelInfoTv;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemView.setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
    }

}
