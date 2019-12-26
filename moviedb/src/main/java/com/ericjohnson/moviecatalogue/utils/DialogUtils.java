package com.ericjohnson.moviecatalogue.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.ericjohnson.moviecatalogue.R;

public class DialogUtils {

    public static void showCancelableDialogWithoutIcon(
            Context context,
            String title,
            String message,
            String positive,
            String negative,
            OnButtonNegativeListener listenerNegative,
            OnButtonPositiveListener listenerPositive) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_alert_dialog_without_icon, null);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvMessage = view.findViewById(R.id.tvMessage);
        Button btnNegative = view.findViewById(R.id.btnNegative);
        Button btnPositive = view.findViewById(R.id.btnPositive);

        tvTitle.setText(title);

        if (message != null) {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        }

        if (positive != null) {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setText(positive);
        }

        if (negative != null) {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setText(negative);
        }

        AlertDialog.Builder builderWithoutIcon = new AlertDialog.Builder(context);
        builderWithoutIcon.setView(view);
        AlertDialog dialogWithoutIcon = builderWithoutIcon.create();

        if (dialogWithoutIcon.getWindow() != null) {
            dialogWithoutIcon.getWindow().setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialogWithoutIcon.getWindow().setGravity(Gravity.CENTER);
            InsetDrawable inset = new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), 60);
            dialogWithoutIcon.getWindow().setBackgroundDrawable(inset);
        }

        dialogWithoutIcon.setCancelable(true);
        dialogWithoutIcon.show();

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerPositive.onButtonPositiveClicked();
                dialogWithoutIcon.dismiss();
            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerNegative.onButtonNegativeClicked();
                dialogWithoutIcon.dismiss();
            }
        });
    }

    public interface OnButtonNegativeListener {
        void onButtonNegativeClicked();
    }

    public interface OnButtonPositiveListener {
        void onButtonPositiveClicked();
    }
}
