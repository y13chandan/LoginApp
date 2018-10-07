package com.example.root.box8logintask.view;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {

    public static void makeText(Context context, String text, int duration){
        if(context != null) {
            Toast toast = Toast.makeText(context, "    " + text + "    ", duration);
            ViewGroup group = (ViewGroup) toast.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setGravity(Gravity.CENTER);
            messageTextView.setTextSize(18); // 18 sp is the standard size
            toast.show();
        }
    }
}
