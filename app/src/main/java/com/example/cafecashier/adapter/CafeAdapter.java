package com.example.cafecashier.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafecashier.R;
import com.example.cafecashier.model.CafeModel;

import java.util.List;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.MyViewHolder> {

    private List<CafeModel> cafeModelList;
    private CafeClickListener clickListener;

    public CafeAdapter(List<CafeModel> cafeModelList, CafeClickListener clickListener) {
        this.cafeModelList = cafeModelList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cafeName.setText(cafeModelList.get(position).getName());
        holder.cafeAddress.setText(cafeModelList.get(position).getAddress());

        holder.openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(cafeModelList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cafeModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cafeName;
        TextView cafeAddress;
        TextView openBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cafeName = itemView.findViewById(R.id.cafename_txt);
            cafeAddress = itemView.findViewById(R.id.addresscafe_txt);
            openBtn = itemView.findViewById(R.id.open_txt);
        }
    }

    public interface CafeClickListener {
        public void onItemClick(CafeModel cafeModel);
    }
}
