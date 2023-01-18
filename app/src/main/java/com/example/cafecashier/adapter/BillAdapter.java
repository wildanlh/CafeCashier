package com.example.cafecashier.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafecashier.R;
import com.example.cafecashier.model.Menu;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {

    private List<Menu> menuList;

    public BillAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public BillAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_out_row, parent, false);
        return new BillAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.MyViewHolder holder, int position) {
        Glide.with(holder.imgMenu).load(menuList.get(position).getUrl()).into(holder.imgMenu);
        holder.nameMenu.setText(menuList.get(position).getName());
        holder.priceMenu.setText("Rp"+menuList.get(position).getPrice());
        holder.countMenu.setText("x"+menuList.get(position).getTotalCheckOut());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameMenu;
        TextView priceMenu;
        TextView countMenu;

        ImageView imgMenu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameMenu = itemView.findViewById(R.id.namemenu_txt);
            priceMenu = itemView.findViewById(R.id.menuprice_txt);
            countMenu = itemView.findViewById(R.id.countmenu_txt);

            imgMenu = itemView.findViewById(R.id.menu_img);
        }
    }
}
