package com.deepakkumarinc.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.deepakkumarinc.covid19tracker.Adapters.Adapter;
import com.deepakkumarinc.covid19tracker.Models.CoronaPojo;
import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CountryCodePicker countryCodePicker;
    TextView total, todayTotal, active, todayActive, recovered, todayRecovered, deaths, todayDeaths;

    String country;
    TextView filter;
    Spinner spinner;
    String[] types = {"cases","deaths","recovered","active"};
    List<CoronaPojo> coronaPojoList;
    List<CoronaPojo> coronaPojoList2;
    PieChart pieChart;
    RecyclerView recyclerView;
    com.deepakkumarinc.covid19tracker.Adapters.Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        countryCodePicker = findViewById(R.id.ccp);
        total = findViewById(R.id.totalCase);
        todayTotal = findViewById(R.id.todayTotal);
        active = findViewById(R.id.activeCases);
        todayActive = findViewById(R.id.todayActive);
        recovered = findViewById(R.id.recoveredCase);
        todayRecovered = findViewById(R.id.todayRecovered);
        deaths = findViewById(R.id.todayDeaths);
        todayDeaths = findViewById(R.id.totaldeaths);
        pieChart = findViewById(R.id.pieChart);
        spinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.recyclerView);
        filter = findViewById(R.id.filter);

        coronaPojoList = new ArrayList<>();
        coronaPojoList2 = new ArrayList<>();

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,types);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        ApiUtilities.getAPIInterface().getCountryData().enqueue(new Callback<List<CoronaPojo>>() {
            @Override
            public void onResponse(Call<List<CoronaPojo>> call, Response<List<CoronaPojo>> response) {
                coronaPojoList2.addAll(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<CoronaPojo>> call, Throwable t) {

            }
        });

        adapter = new Adapter(getApplicationContext(),coronaPojoList2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);

        countryCodePicker.setAutoDetectedCountry(true);
        country = countryCodePicker.getSelectedCountryName();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = countryCodePicker.getSelectedCountryName();
                fetchData();
            }
        });
        fetchData();


    }

    private void fetchData() {
        ApiUtilities.getAPIInterface().getCountryData().enqueue(new Callback<List<CoronaPojo>>() {
            @Override
            public void onResponse(Call<List<CoronaPojo>> call, Response<List<CoronaPojo>> response) {
                coronaPojoList.addAll(response.body());

                for (int i=0;i<coronaPojoList.size();i++)
            {
                    if (coronaPojoList.get(i).getCountry().equals(country))
                    {
                        active.setText((coronaPojoList.get(i).getActive()));
                        deaths.setText((coronaPojoList.get(i).getDeaths()));
                        todayDeaths.setText((coronaPojoList.get(i).getTodayDeaths()));
                        recovered.setText((coronaPojoList.get(i).getRecovered()));
                        todayRecovered.setText((coronaPojoList.get(i).getTodayRecovered()));
                        total.setText((coronaPojoList.get(i).getCases()));
                        todayTotal.setText((coronaPojoList.get(i).getTodayCases()));

                        int active, total, recovered, deaths;
                        active = Integer.parseInt(coronaPojoList.get(i).getActive());
                        total = Integer.parseInt(coronaPojoList.get(i).getCases());
                        recovered = Integer.parseInt(coronaPojoList.get(i).getRecovered());
                        deaths = Integer.parseInt(coronaPojoList.get(i).getDeaths());

                        updateGraph(active,total,recovered,deaths);



                    }
                }
            }

            @Override
            public void onFailure(Call<List<CoronaPojo>> call, Throwable t) {

            }
        });
    }

    private void updateGraph(int active, int total, int recovered, int deaths) {
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Confirm",total,Color.parseColor("#FFB701")));
        pieChart.addPieSlice(new PieModel("Active",active,Color.parseColor("#FF4CAF50")));
        pieChart.addPieSlice(new PieModel("Recovered",recovered,Color.parseColor("#38ACCD")));
        pieChart.addPieSlice(new PieModel("Deaths",deaths,Color.parseColor("#F55c47")));
        pieChart.startAnimation();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = types[position];
        filter.setText(item);
        adapter.filter(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}