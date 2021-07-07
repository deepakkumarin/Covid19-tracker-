package com.deepakkumarinc.covid19tracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deepakkumarinc.covid19tracker.Models.CoronaPojo;
import com.deepakkumarinc.covid19tracker.R;

import java.text.NumberFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    int m=1;
    Context context;
    List<CoronaPojo> countryList;

    public Adapter(Context context, List<CoronaPojo> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.ViewHolder holder, int position) {

        CoronaPojo coronaPojo = countryList.get(position);
        if (m==1){
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(coronaPojo.getCases())));
        }
        else if (m==2){
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(coronaPojo.getRecovered())));
        }
        else if (m==3)
        {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(coronaPojo.getDeaths())));
        }else
            {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(coronaPojo.getActive())));
        }

        holder.country.setText(coronaPojo.getCountry());

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView country, cases;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.countryName);
            cases = itemView.findViewById(R.id.countryCases);
        }
    }

    public void filter(String charText){
        if (charText.equals("cases"))
        {
            m=1;
        }
        else if (charText.equals("recovered"))
        {
            m=2;
        }
        else if (charText.equals("deaths"))
        {
            m=3;
        }
        else
        {
            m=4;
        }
        notifyDataSetChanged();
    }




}
