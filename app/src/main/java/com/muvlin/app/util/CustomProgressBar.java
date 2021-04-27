package com.muvlin.app.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.muvlin.app.R;

public class CustomProgressBar {
    Dialog dialog;

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog show(Context context, String title) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.progress_bar, null);
        TextView titulo = view.findViewById(R.id.cp_title);
        if (title != null) {
            titulo.setText(title);
        }
        view.findViewById(R.id.cp_bg_view).setBackgroundColor(Color.parseColor("#60000000"));
        view.findViewById(R.id.cp_cardview).setBackgroundColor(Color.parseColor("#70000000"));
        ProgressBar pb = view.findViewById(R.id.cp_pbar);
        setColorFilter(pb.getIndeterminateDrawable(), ResourcesCompat.getColor(context.getResources(), R.color.blanco, null));
        titulo.setTextColor(Color.WHITE);


        dialog = new Dialog(context, R.style.CustomProgressBarTheme);
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

    public final void setColorFilter(@NonNull Drawable drawable, int color) {
        if (Build.VERSION.SDK_INT >= 29) {
            drawable.setColorFilter((ColorFilter)(new BlendModeColorFilter(color, BlendMode.SRC_ATOP)));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }

    }
}
