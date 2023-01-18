package com.example.cafecashier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafecashier.adapter.CafeAdapter;
import com.example.cafecashier.adapter.MenuAdapter;
import com.example.cafecashier.model.CafeModel;
import com.example.cafecashier.model.Menu;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements MenuAdapter.MenuClickListener {
    private TextView txtHello, txtDate, txtAmount, btnCheclOut;
    private List<Menu> menuList = null;
    private List<Menu> itemsCheckOut;
    private MenuAdapter menuAdapter;
    private int totalItemsCheckOut = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        txtHello = findViewById(R.id.hello_user_txt);
        txtDate = findViewById(R.id.current_date_txt);
        txtAmount = findViewById(R.id.amount_txt);
        btnCheclOut = findViewById(R.id.checkout_btn);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        txtDate.setText(currentDate);

        CafeModel cafeModel = getIntent().getParcelableExtra("CafeModels");
        txtHello.setText(cafeModel.getName());
        menuList = cafeModel.getMenus();
        initRecyclerView();

        btnCheclOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemsCheckOut == null) {
                    Toast.makeText(OrderActivity.this, "Please add some items for check out", Toast.LENGTH_SHORT).show();
                    return;
                } else if (itemsCheckOut.size() <= 0) {
                    Toast.makeText(OrderActivity.this, "Please add some items for check out", Toast.LENGTH_SHORT).show();
                    return;
                }
                cafeModel.setMenus(itemsCheckOut);
                Intent i = new Intent(OrderActivity.this, CheckOutActivity.class);
                i.putExtra("CafeModels", cafeModel);
                startActivityForResult(i, 1000);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.menu_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(menuList, this);
        recyclerView.setAdapter(menuAdapter);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }

    @Override
    public void onAddCheckOut(Menu menu) {
        if (itemsCheckOut == null) {
            itemsCheckOut = new ArrayList<>();
        }
        itemsCheckOut.add(menu);
        totalItemsCheckOut = 0;

        for (Menu m : itemsCheckOut) {
            totalItemsCheckOut = totalItemsCheckOut + m.getTotalCheckOut();
        }
        txtAmount.setText("Amount: " + totalItemsCheckOut + " items");
    }

    @Override
    public void onUpdateCheckOut(Menu menu) {
        if (itemsCheckOut.contains(menu)) {
            int index = itemsCheckOut.indexOf(menu);
            itemsCheckOut.remove(index);
            itemsCheckOut.add(index, menu);

            totalItemsCheckOut = 0;

            for (Menu m : itemsCheckOut) {
                totalItemsCheckOut = totalItemsCheckOut + m.getTotalCheckOut();
            }
        }
        txtAmount.setText("Amount: " + totalItemsCheckOut + " items");
    }

    @Override
    public void onRemoveCheckOut(Menu menu) {
        if (itemsCheckOut.contains(menu)) {
            itemsCheckOut.remove(menu);
            totalItemsCheckOut = 0;

            for (Menu m : itemsCheckOut) {
                totalItemsCheckOut = totalItemsCheckOut + m.getTotalCheckOut();
            }
        }
        txtAmount.setText("Amount: " + totalItemsCheckOut + " items");
    }
}