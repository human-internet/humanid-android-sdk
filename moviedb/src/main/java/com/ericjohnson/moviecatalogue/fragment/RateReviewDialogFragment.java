package com.ericjohnson.moviecatalogue.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ericjohnson.moviecatalogue.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RateReviewDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    @BindView(R.id.imgStar1)
    ImageView imgStar1;

    @BindView(R.id.imgStar2)
    ImageView imgStar2;

    @BindView(R.id.imgStar3)
    ImageView imgStar3;

    @BindView(R.id.imgStar4)
    ImageView imgStar4;

    @BindView(R.id.imgStar5)
    ImageView imgStar5;

    @BindView(R.id.btnRateReview)
    Button btnRateReview;

    private  ImageView[] stars = new ImageView[5];

    public static final String TAG = "RateReviewDialog";

    public static RateReviewDialogFragment newInstance() {
        return new RateReviewDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_rating, container, false);
        ButterKnife.bind(this, view);

        stars[0] = imgStar1;
        stars[1] = imgStar2;
        stars[2] = imgStar3;
        stars[3] = imgStar4;
        stars[4] = imgStar5;

        imgStar1.setOnClickListener(this);
        imgStar2.setOnClickListener(this);
        imgStar3.setOnClickListener(this);
        imgStar4.setOnClickListener(this);
        imgStar5.setOnClickListener(this);
        btnRateReview.setOnClickListener(this);

        return view;

    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgStar1:
                for (int i =0 ; i < 1; i++){
                    stars[i].setSelected(true);
                }
                for (int i =4 ; i > 0; i--){
                    stars[i].setSelected(false);
                }
                break;

            case R.id.imgStar2:
                for (int i =0 ; i < 2; i++){
                    stars[i].setSelected(true);
                }
                for (int i =4 ; i > 1; i--){
                    stars[i].setSelected(false);
                }
                break;

            case R.id.imgStar3:
                for (int i =0 ; i < 3; i++){
                    stars[i].setSelected(true);
                }
                for (int i =4 ; i > 2; i--){
                    stars[i].setSelected(false);
                }
                break;

            case R.id.imgStar4:
                for (int i =0 ; i < 4; i++){
                    stars[i].setSelected(true);
                }
                for (int i =4 ; i > 3; i--){
                    stars[i].setSelected(false);
                }
                break;

            case R.id.imgStar5:
                for (int i =0 ; i < 5; i++){
                    stars[i].setSelected(true);
                }
                break;

            case R.id.btnRateReview:
                break;
        }

    }
}
