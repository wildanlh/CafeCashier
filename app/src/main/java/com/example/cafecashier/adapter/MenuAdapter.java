package com.example.cafecashier.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafecashier.R;
import com.example.cafecashier.model.Menu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<Menu> menuList;
    private MenuClickListener menuClickListener;

    public MenuAdapter(List<Menu> menuList, MenuClickListener menuClickListener) {
        this.menuList = menuList;
        this.menuClickListener = menuClickListener;
    }

    public void updateData(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(holder.imgMenu).load(menuList.get(position).getUrl()).into(holder.imgMenu);
        holder.nameMenu.setText(menuList.get(position).getName());
        holder.priceMenu.setText("Rp" + menuList.get(position).getPrice());

        holder.orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = menuList.get(position);
                menu.setTotalCheckOut(1);
                menuClickListener.onAddCheckOut(menu);
                holder.countLayout.setVisibility(View.VISIBLE);
                holder.orderBtn.setVisibility(View.GONE);
                holder.countMenu.setText(menu.getTotalCheckOut() + "");
            }
        });

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = menuList.get(position);

                int total = menu.getTotalCheckOut();
                total--;
                if (total > 0) {
                    menu.setTotalCheckOut(total);
                    menuClickListener.onUpdateCheckOut(menu);
                    holder.countMenu.setText(total + "");
                } else {
                    holder.countLayout.setVisibility(View.GONE);
                    holder.orderBtn.setVisibility(View.VISIBLE);
                    menu.setTotalCheckOut(total);
                    menuClickListener.onRemoveCheckOut(menu);
                }
            }
        });

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu  = menuList.get(position);

                int total = menu.getTotalCheckOut();
                total++;
                if(total <= 25 ) {
                    menu.setTotalCheckOut(total);
                    menuClickListener.onUpdateCheckOut(menu);
                    holder.countMenu.setText(total +"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameMenu;
        TextView priceMenu;
        TextView countMenu;
        TextView orderBtn;

        ImageView imgMenu;
        ImageView imgRemove;
        ImageView imgAdd;

        LinearLayout countLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameMenu = itemView.findViewById(R.id.namemenu_txt);
            priceMenu = itemView.findViewById(R.id.menuprice_txt);
            countMenu = itemView.findViewById(R.id.countmenu_txt);
            orderBtn = itemView.findViewById(R.id.menuorder_btn);

            imgMenu = itemView.findViewById(R.id.menu_img);
            imgRemove = itemView.findViewById(R.id.removemenu_img);
            imgAdd = itemView.findViewById(R.id.addmenu_img);

            countLayout =itemView.findViewById(R.id.layout_countmenu);
        }
    }

    public interface MenuClickListener {
        public void onAddCheckOut(Menu menu);
        public void onUpdateCheckOut(Menu menu);
        public void onRemoveCheckOut(Menu menu);
    }
}
