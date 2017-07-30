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
import android.widget.RatingBar;
import android.widget.TextView;

import com.pieprzyca.dawid.skiapp.R;
import com.pieprzyca.dawid.skiapp.data.ReviewsInfo;

import java.util.ArrayList;

/**
 * Created by Dawid on 19.07.2017.
 */

public class ReviewsAdapter extends ArrayAdapter<ReviewsInfo> {
    public ReviewsAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<ReviewsInfo> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }
        ReviewsInfo reviewsInfo = getItem(position);
        if(reviewsInfo != null ) {
            TextView reviewText = (TextView) convertView.findViewById(R.id.reviewText);
            TextView reviewDate = (TextView) convertView.findViewById(R.id.reviewDate);
            RatingBar reviewRating = (RatingBar) convertView.findViewById(R.id.ratingBarItem);
            if(reviewText != null && reviewDate != null && reviewRating != null){
                reviewText.setText(reviewsInfo.getReview());
                reviewDate.setText(reviewsInfo.getReviewDate());
                reviewRating.setRating(Float.parseFloat(reviewsInfo.getReviewRating()));
                Log.d("Set Text: ", (String)reviewText.getText());
                Log.d("Set Text: ", (String)reviewDate.getText());
                Log.d("Set Text: ", String.valueOf(reviewRating.getRating()));
            }
        }
        return convertView;
    }
}