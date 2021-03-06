package com.sam_chordas.android.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.StockDetails;
/**
 * Created by raanand on 10/10/16.
 */

public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = WidgetProvider.class.getSimpleName();

    //variables to define broadcast and pass along some data in it.
    public static final String INTENT_ACTION = "INTENT_ACTION";
    public static final String EXTRA_SYMBOL = "EXTRA_SYMBOL";
    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_CURRENCY = "EXTRA_CURRENCY";
    public static final String EXTRA_LASTTRADEDATE = "EXTRA_LASTTRADEDATE";
    public static final String EXTRA_DAYLOW = "EXTRA_DAYLOW";
    public static final String EXTRA_DAYHIGH = "EXTRA_DAYHIGH";
    public static final String EXTRA_YEARLOW = "EXTRA_YEARLOW";
    public static final String EXTRA_YEARHIGH = "EXTRA_YEARHIGH";
    public static final String EXTRA_EARNINGSSHARE = "EXTRA_EARNINGSSHARE";
    public static final String EXTRA_MARKETCAPITALIZATION = "EXTRA_MARKETCAPITALIZATION";





    /**
     * Method would be called on broadcast created on item touch from the collection widget.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(INTENT_ACTION)){

            /**
             * Matches our own created intent, and thus helps in showing data over time.
             * Can add other methods later too in the same receiver.
             */

            String symbol = intent.getStringExtra(EXTRA_SYMBOL);
            String name = intent.getStringExtra(EXTRA_NAME);
            String currency = intent.getStringExtra(EXTRA_CURRENCY);
            String lasttradedate = intent.getStringExtra(EXTRA_LASTTRADEDATE);
            String daylow = intent.getStringExtra(EXTRA_DAYLOW);
            String dayhigh = intent.getStringExtra(EXTRA_DAYHIGH);
            String yearlow = intent.getStringExtra(EXTRA_YEARLOW);
            String yearhigh = intent.getStringExtra(EXTRA_YEARHIGH);
            String earningsshare = intent.getStringExtra(EXTRA_EARNINGSSHARE);
            String marketcapitalization = intent.getStringExtra(EXTRA_MARKETCAPITALIZATION);
            Intent showHistoricalData = new Intent(context, StockDetails.class);
            showHistoricalData.putExtra("symbol_name", symbol);
            showHistoricalData.putExtra("name", name);
            showHistoricalData.putExtra("currency", currency);
            showHistoricalData.putExtra("lasttradedate", lasttradedate);
            showHistoricalData.putExtra("daylow", daylow);
            showHistoricalData.putExtra("dayhigh", dayhigh);
            showHistoricalData.putExtra("yearlow", yearlow);
            showHistoricalData.putExtra("yearhigh", yearhigh);
            showHistoricalData.putExtra("earningsshare", earningsshare);
            showHistoricalData.putExtra("marketcaptalization", marketcapitalization);
            showHistoricalData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(showHistoricalData);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        /**
         * appWidgetIds contains all the widgets added to the home  screen.
         */
        for (int i = 0; i< appWidgetIds.length ; i++){

            /**
             * Intent that defines service to perform for widgets.
             */

            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            /**
             * generating RemoteViews for the widget. Equivalent to "View".
             */
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.stock_widget_layout);

            //This method is deprecated but, the other method with only two parameters doesn't seem to work!
            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.lv_stock_widget_layout, intent);
            remoteViews.setEmptyView(R.id.lv_stock_widget_layout, R.id.tv_empty_stocks_widget_layout);

            //Intent that fires broadcast on item click!
            Intent openSymbol = new Intent(context, WidgetProvider.class);
            openSymbol.setAction(WidgetProvider.INTENT_ACTION);
            openSymbol.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, openSymbol,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.lv_stock_widget_layout, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
