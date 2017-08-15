package com.pieprzyca.dawid.skiapp.arrayAdapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pieprzyca.dawid.skiapp.R;
import com.pieprzyca.dawid.skiapp.data.ResortData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 16.07.2017.
 * Klasa definjująca Adapter dla listy z informacjami o ośrodkach narciarskich.
 */

public class ResortInfoAdapter extends  ArrayAdapter<ResortData> implements Filterable {
    private List<ResortData> resortDataList;
    private ResortFilter resortFilter;

    public ResortInfoAdapter(@NonNull Context context, @LayoutRes int resource, List<ResortData> objects) {
        super(context, resource, objects);
        this.resortDataList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.resortlist, parent, false);
        }

        ResortData resortData = getItem(position);

        if (resortData != null) {
            TextView resortName = (TextView) convertView.findViewById(R.id.resortName);
            if (resortName != null) {
                resortName.setText(resortData.getResortName());
            }
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(resortFilter == null)
            resortFilter = new ResortFilter();
        return resortFilter;
    }

    private class ResortFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint == null || constraint.length() == 0){
                results.values = resortDataList;
                results.count = resortDataList.size();
            }
            else {
                List<ResortData> resortDatasTemp = new ArrayList<>();
                for (ResortData resort : resortDataList) {
                    if (resort.getResortName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        resortDatasTemp.add(resort);
                    }
                }
                for(int i =0 ; i< resortDatasTemp.size(); i++){
                    Log.d("TEMP: ", resortDatasTemp.get(i).getResortName());
                }
                results.values = resortDatasTemp;
                results.count = resortDatasTemp.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            resortDataList = (ArrayList<ResortData>) results.values;
            notifyDataSetChanged();
        }
    }
}