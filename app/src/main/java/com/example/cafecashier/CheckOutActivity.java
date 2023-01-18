package com.example.cafecashier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafecashier.adapter.CheckOutAdapter;
import com.example.cafecashier.model.CafeModel;
import com.example.cafecashier.model.Menu;

import java.text.DateFormat;
import java.util.Calendar;

public class CheckOutActivity extends AppCompatActivity {

    private EditText editCSName;
    private RecyclerView recycleCheckOut;
    private TextView txtHello, txtDate, txtSubTotal, txtTax, txtTotal, txtChange, txtCash, btnPay;
    private CheckOutAdapter checkOutAdapter;
    private AlertDialog.Builder addCashBuilder;
    private AlertDialog addCashDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        editCSName = findViewById(R.id.edit_csname);

        recycleCheckOut = findViewById(R.id.checkout_recycle);

        txtHello = findViewById(R.id.hello_user_txt);
        txtDate = findViewById(R.id.current_date_txt);
        txtSubTotal = findViewById(R.id.subtotal_txt);
        txtTax = findViewById(R.id.tax_txt);
        txtTotal = findViewById(R.id.total_txt);
        txtChange = findViewById(R.id.change_txt);
        txtCash = findViewById(R.id.edit_cash);
        btnPay = findViewById(R.id.pay_btn);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        txtDate.setText(currentDate);

        CafeModel cafeModel = getIntent().getParcelableExtra("CafeModels");
        txtHello.setText(cafeModel.getName());

        initRecycleView(cafeModel);
        calculateAmount(cafeModel);

        txtCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPopUp();
            }
        });

        txtCash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int total = Integer.parseInt(txtTotal.getText().toString());
                int cash = Integer.parseInt(txtCash.getText().toString());
                int change = cash - total;

                txtChange.setText(String.valueOf(change));
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editCSName.getText().toString().equals("")) {
                    editCSName.setError("Input Name");
                    return;
                } else if (txtCash.getText().toString().equals("")) {
                    Toast.makeText(CheckOutActivity.this, "Empty Cash", Toast.LENGTH_SHORT).show();
                    return;
                } int change = Integer.parseInt(txtChange.getText().toString());
                    if (change < 0) {
                    Toast.makeText(CheckOutActivity.this, "Cash isn't enough", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(CheckOutActivity.this, BillActivity.class);
                i.putExtra("CafeModels", cafeModel);
                i.putExtra("name", editCSName.getText().toString());
                i.putExtra("date", txtDate.getText().toString());
                i.putExtra("subTotal", txtSubTotal.getText().toString());
                i.putExtra("tax", txtTax.getText().toString());
                i.putExtra("total", txtTotal.getText().toString());
                i.putExtra("cash", txtCash.getText().toString());
                i.putExtra("change", txtChange.getText().toString());
                startActivityForResult(i, 1000);
            }
        });
    }

    private void calculateAmount(CafeModel cafeModel) {
        int subTotal = 0, tax, total;

        for (Menu m : cafeModel.getMenus()) {
            subTotal += m.getPrice() * m.getTotalCheckOut();
        }
        txtSubTotal.setText("" + subTotal);
        txtTax.setText("10%");
        tax = subTotal / cafeModel.getTax();
        total = subTotal + tax;
        txtTotal.setText("" + total);
    }

    private void initRecycleView(CafeModel cafeModel) {
        recycleCheckOut.setLayoutManager(new LinearLayoutManager(this));
        checkOutAdapter = new CheckOutAdapter(cafeModel.getMenus());
        recycleCheckOut.setAdapter(checkOutAdapter);
    }

    public void addPopUp() {
        addCashBuilder = new AlertDialog.Builder(this);
        final View addPopUpView = getLayoutInflater().inflate(R.layout.popup, null);

        EditText popUpEditCash;
        TextView addCashPopUp;
        ImageView btnCancel;

        popUpEditCash = addPopUpView.findViewById(R.id.edit_cashpopup);
        addCashPopUp = addPopUpView.findViewById(R.id.addcash_btnpopup);
        btnCancel = addPopUpView.findViewById(R.id.btn_cancel);

        addCashBuilder.setView(addPopUpView);
        addCashDialog = addCashBuilder.create();
        addCashDialog.show();

        addCashPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cashPopUp = popUpEditCash.getText().toString();
                String strPattern = "^0+";

                if (popUpEditCash.getText().toString().equals("")){
                    popUpEditCash.setError("Input cash");
                    return;
                } else {
                    txtCash.setText("" + cashPopUp.replaceAll(strPattern, ""));
                    addCashDialog.dismiss();
                }
                popUpEditCash.setText("");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCashDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}