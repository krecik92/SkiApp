package com.pieprzyca.dawid.skiapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pieprzyca.dawid.skiapp.DatabaseOperations;
import com.pieprzyca.dawid.skiapp.R;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;

/**
 * Created by Dawid on 29.05.2016.
 */
public class Rating extends Fragment {
    private Button button_submit;
    private TextView textView;
    private RatingBar ratingBar;
    private EditText reviewText;

    public Rating() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rating, container, false);
        reviewText = (EditText) view.findViewById(R.id.review);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        textView = (TextView) view.findViewById(R.id.ratingText);
        button_submit = (Button) view.findViewById(R.id.buttonSubmit);
        listenerForRatingBar(ratingBar, textView);
        onButtonClickListener(ratingBar, button_submit);
        return view;
    }

    public void listenerForRatingBar(RatingBar ratingBar, final TextView textView) {
        ratingBar.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar1, float ratingNumber, boolean f) {
                        textView.setText(String.valueOf(ratingNumber));
                    }
                }
        );
    }

    public void onButtonClickListener(final RatingBar ratingBar, Button buttonSubmit) {
        buttonSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseOperations.postNewReview(getActivity().getApplicationContext(), DatabaseConfig.INSERT_RATING_REQUEST_URL, getActivity().getIntent().getStringExtra("skiResortId"), reviewText.getText().toString(), Float.valueOf(ratingBar.getRating()));
                        Toast.makeText(getContext(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
                        reviewText.setText("");
                    }
                }
        );
    }
}