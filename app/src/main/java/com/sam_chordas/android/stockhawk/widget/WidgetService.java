package com.sam_chordas.android.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;

import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by raanand on 10/10/16.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return null;
    }

    /**
     * Populate stocks in  ListView.
     */
    public class StockViewFactory implements RemoteViewsFactory{

        private Context context;
        private Cursor cursor;
        private int widgetId;

        public StockViewFactory(Context context,Intent intent) {
            this.context = context;
            this.widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

            cursor = getContentResolver().query(
                    QuoteProvider.Quotes.CONTENT_URI,
                    new String[]{QuoteColumns._ID, //0
                            QuoteColumns.SYMBOL, //1
                            QuoteColumns.BIDPRICE, //2
                            QuoteColumns.CHANGE, //3
                            QuoteColumns.ISUP,//4
                            QuoteColumns.NAME,//5
                            QuoteColumns.CURRENCY,//6
                            QuoteColumns.LASTTRADEDATE,//7
                            QuoteColumns.DAYLOW,//8
                            QuoteColumns.DAYHIGH,//9
                            QuoteColumns.YEARLOW,//10
                            QuoteColumns.YEARHIGH,//11
                            QuoteColumns.EARNINGSSHARE,//12
                            QuoteColumns.MARKETCAPITALIZATION}, //13
                    QuoteColumns.ISCURRENT + " = ?",
                    new String[]{"1"},
                    null
            );
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            if(this.cursor != null){
                this.cursor.close();
            }
        }

        @Override
        public int getCount() {
            return (this.cursor != null) ? this.cursor.getCount() : 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.list_item_quote);

            if (this.cursor.moveToPosition(i)) {
                String symbol = cursor.getString(1);
                remoteViews.setTextViewText(R.id.stock_symbol, symbol);
                remoteViews.setTextViewText(R.id.bid_price, cursor.getString(2));
                remoteViews.setTextViewText(R.id.change, cursor.getString(3));
                String name = cursor.getString(5);
                String currency = cursor.getString(6);
                String lasttradedate = cursor.getString(7);
                String daylow = cursor.getString(8);
                String dayhigh = cursor.getString(9);
                String yearlow = cursor.getString(10);
                String yearhigh = cursor.getString(11);
                String earningsshare = cursor.getString(12);
                String marketcapitalization = cursor.getString(13);
                if (cursor.getInt(4) == 1) {
                    remoteViews.setInt(R.id.change, "setBackgroundResource",
                            R.drawable.percent_change_pill_green);
                } else {
                    remoteViews.setInt(R.id.change, "setBackgroundResource",
                            R.drawable.percent_change_pill_red);
                }

                Bundle extras = new Bundle();
                extras.putString(WidgetProvider.EXTRA_SYMBOL, symbol);
                extras.putString(WidgetProvider.EXTRA_NAME, name);
                extras.putString(WidgetProvider.EXTRA_CURRENCY, currency);
                extras.putString(WidgetProvider.EXTRA_LASTTRADEDATE, lasttradedate);
                extras.putString(WidgetProvider.EXTRA_DAYLOW, daylow);
                extras.putString(WidgetProvider.EXTRA_DAYHIGH, dayhigh);
                extras.putString(WidgetProvider.EXTRA_YEARLOW, yearlow);
                extras.putString(WidgetProvider.EXTRA_YEARHIGH, yearhigh);
                extras.putString(WidgetProvider.EXTRA_EARNINGSSHARE, earningsshare);
                extras.putString(WidgetProvider.EXTRA_MARKETCAPITALIZATION, marketcapitalization);

                Intent fillInIntent = new Intent();
                fillInIntent.putExtras(extras);
                remoteViews.setOnClickFillInIntent(R.id.ll_list_item_quote, fillInIntent);

            }


            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return this.cursor.getInt(0);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
