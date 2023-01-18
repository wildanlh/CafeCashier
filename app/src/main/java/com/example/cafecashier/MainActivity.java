package com.example.cafecashier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cafecashier.adapter.CafeAdapter;
import com.example.cafecashier.model.CafeModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CafeAdapter.CafeClickListener {
    private TextView txtGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGreeting = findViewById(R.id.greeting_txt);

        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);

        if (time >= 0 && time < 12) {
            txtGreeting.setText("Good Morning!");
        } else if (time >= 12 && time < 16) {
            txtGreeting.setText("Good Afternoon!");
        } else if (time >= 16 && time < 21) {
            txtGreeting.setText("Good Evening!");
        } else if (time >= 21 && time < 24) {
            txtGreeting.setText("Good Night!");
        } else {
            txtGreeting.setText("Hello, Welcome!");
        }

        List<CafeModel> cafeModelList =  getCafeData();

        initRecyclerView(cafeModelList);
    }

    private void initRecyclerView(List<CafeModel> cafeModelList) {
        RecyclerView recyclerView =  findViewById(R.id.cafe_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CafeAdapter adapter = new CafeAdapter(cafeModelList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<CafeModel> getCafeData() {
        InputStream is = getResources().openRawResource(R.raw.cafecashier);

        Writer writer = new StringWriter();
        char[] buffer= new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        } catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson =new Gson();
        CafeModel[] cafeModels = gson.fromJson(jsonStr, CafeModel[].class);
        List<CafeModel> cafeList = Arrays.asList(cafeModels);

        return cafeList;
    }


    @Override
    public void onItemClick(CafeModel cafeModel) {
        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
        intent.putExtra("CafeModels", cafeModel);
        startActivity(intent);
    }
}