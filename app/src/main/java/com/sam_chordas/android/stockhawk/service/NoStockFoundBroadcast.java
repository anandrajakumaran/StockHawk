package com.sam_chordas.android.stockhawk.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by raanand on 11/14/2016.
 */

public class NoStockFoundBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Symbol Not Valid",Toast.LENGTH_SHORT).show();
    }
}
