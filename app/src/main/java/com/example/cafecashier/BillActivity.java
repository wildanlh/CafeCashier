package com.example.cafecashier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cafecashier.adapter.BillAdapter;
import com.example.cafecashier.adapter.CheckOutAdapter;
import com.example.cafecashier.model.CafeModel;

public class BillActivity extends AppCompatActivity {
    
    private RecyclerView recyclerBill;
    private TextView txtCafe, txtDate, txtCSName, txtSubTotal, txtTax, txtTotal, txtCash, txtChange, btnReset;
    private BillAdapter billAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        txtCafe = findViewById(R.id.cafe_txt);
        txtDate = findViewById(R.id.current_date_txt);
        txtCSName = findViewById(R.id.csnamebill_txt);
        txtSubTotal = findViewById(R.id.subtotal_txt);
        txtTax = findViewById(R.id.tax_txt);
        txtTotal = findViewById(R.id.total_txt);
        txtCash = findViewById(R.id.rpcash_txt);
        txtChange = findViewById(R.id.rpchange_txt);
        btnReset = findViewById(R.id.reset_btn);

        recyclerBill = findViewById(R.id.bill_recycle);

        CafeModel cafeModel = getIntent().getParcelableExtra("CafeModels");
        txtCafe.setText(cafeModel.getName());
        txtDate.setText("" + getIntent().getStringExtra("date"));
        txtCSName.setText("" + getIntent().getStringExtra("name"));
        txtSubTotal.setText("" + getIntent().getStringExtra("subTotal"));
        txtTax.setText("" + getIntent().getStringExtra("tax"));
        txtTotal.setText("" + getIntent().getStringExtra("total"));
        txtCash.setText("Rp" + getIntent().getStringExtra("cash"));
        txtChange.setText("Rp" + getIntent().getStringExtra("change"));

        initRecycleView(cafeModel);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initRecycleView(CafeModel cafeModel) {
        recyclerBill.setLayoutManager(new LinearLayoutManager(this));
        billAdapter = new BillAdapter(cafeModel.getMenus());
        recyclerBill.setAdapter(billAdapter);
    }
}