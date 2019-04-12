package com.dexertencreatives.stockfetcherapp.UI;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dexertencreatives.stockfetcherapp.R;
import com.dexertencreatives.stockfetcherapp.adapter.AlphaDataAdapter;
import com.dexertencreatives.stockfetcherapp.adapter.CustomFragmentAdapter;
import com.dexertencreatives.stockfetcherapp.model.ParseAlphaData;
import com.dexertencreatives.stockfetcherapp.network.Formatter;
import com.dexertencreatives.stockfetcherapp.network.NetworkSingleton;
import com.dexertencreatives.stockfetcherapp.network.NetworkURLRequest;
import com.viewpagerindicator.CirclePageIndicator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.volley.Request.Method.GET;


public class GraphActivity extends AppCompatActivity {
    private RequestQueue theRequestQueue;
    private String symbol;
    private ViewPager viewpager;
    private CustomFragmentAdapter customfragmentadapter;
    private AlphaDataAdapter alphaDataAdapter;
    private ArrayList<ParseAlphaData> mparseAlphaData;
    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;

    private TabHost tabhost;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar;


    public static final String EXTRA_POSITION = "extra_position";
    private static final String SAVED_SORT = "KeyForLayoutManagerState";
    public static final String EXTRA_SYMBOL = "GBPUSD";

    private CirclePageIndicator indicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);
        setTitle(R.string.graph_title);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setAdapter(alphaDataAdapter);


        mparseAlphaData = new ArrayList<>();
        indicate = (CirclePageIndicator) findViewById(R.id.indicator);
        viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setOffscreenPageLimit(1);
        tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup();
        theRequestQueue = NetworkSingleton.getInstance(this).getRequestQueue();
        NetworkURLRequest networkURLRequest = new NetworkURLRequest();

        Intent i1 = getIntent();
        if (i1 != null) {

            symbol = i1.getStringExtra(EXTRA_SYMBOL);
            if (isOnline()) {

                fetchDailyOHCLData1(networkURLRequest.GlobalQuoteRequest(symbol));
            } else {
                Toast.makeText(GraphActivity.this,
                        getResources().getText(R.string.no_connectivity_error),
                        Toast.LENGTH_LONG).show();
            }


        }
        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(SAVED_SORT);
            mrecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

        Toast.makeText(this.getApplicationContext(), symbol, Toast.LENGTH_LONG).show();
        setCustomfragmentadapter(symbol);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.graph_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.get_pivot:
                Intent pivotIntent = new Intent(GraphActivity.this, PivotGeneratorActivity.class);
                pivotIntent.putExtra(GraphActivity.EXTRA_SYMBOL, symbol);
                Toast.makeText(this.getApplicationContext(), symbol, Toast.LENGTH_SHORT).show();
                startActivity(pivotIntent);

                return true;
            case R.id.get_rate:
                Intent CurrIntent = new Intent(GraphActivity.this, CurrencyExchangeActivity.class);
                CurrIntent.putExtra(GraphActivity.EXTRA_SYMBOL, symbol);
                Toast.makeText(this.getApplicationContext(), symbol, Toast.LENGTH_SHORT).show();
                startActivity(CurrIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_SORT, mrecyclerView.getLayoutManager().onSaveInstanceState());

    }

    private void fetchDailyOHCLData1(String urlRequest) {

        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(GET, urlRequest, null, onDataLoaded, onDataError);

        theRequestQueue.add(request);


    }

    private final Response.Listener<JSONObject> onDataLoaded = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {

            //Parse results
            mparseAlphaData.clear();
            try {


                JSONObject series = response.getJSONObject(getString(R.string.time_series_daily));
                JSONObject meta = response.getJSONObject(getString(R.string.meta_data));
                JSONObject lastData = null, secondData = null;
                for (int i = 0; i < series.names().length(); i++) {
                    if (i == 0) lastData = series.getJSONObject(series.names().optString(i));
                    if (i == 1) {
                        secondData = series.getJSONObject(series.names().optString(i));
                        break;
                    }
                }
                //  if (lastData == null || secondData == null) return;
                progressBar.setVisibility(View.INVISIBLE);
                double changeVal = Formatter.formatter(
                        Double.valueOf(Objects.requireNonNull(lastData).optString(getString(R.string.g_close))) -
                                Double.valueOf(lastData.optString(getString(R.string.g_open))),
                        2);
                double changePercent = Formatter.formatter(changeVal / Double.valueOf(lastData.optString(getString(R.string.g_open))) * 100, 2);
                String timeStamp = meta.optString(getString(R.string.g_last_resfresh));
                // timeStamp += timeStamp.length() > 10 ? " EDT" : " 16:00:00 EDT";
                String StockSymbol = meta.optString(getString(R.string.g_symbol));
                String Timestamp = timeStamp;
                String PreviouClose = Double.valueOf(secondData.optString(getString(R.string.g_close))).toString();
                String CurrOpen = Double.valueOf(Objects.requireNonNull(lastData).optString(getString(R.string.g_open))).toString();
                String CurrHigh = Double.valueOf(lastData.optString(getString(R.string.g_high))).toString();
                String CurrLow = Double.valueOf(lastData.optString(getString(R.string.g_low))).toString();
                String CurrClose = Double.valueOf(lastData.optString(getString(R.string.g_close))).toString();
                String CurrVolume = Double.valueOf(lastData.optString(getString(R.string.g_vol))).toString();
                double CurrentDayRange = Formatter.formatter(Double.valueOf(CurrHigh) - Double.valueOf(CurrLow), 4);
                //   String DealVolume = lastData.getString("5. volume");
                ParseAlphaData parseData = new ParseAlphaData();
                parseData.setSymbol(StockSymbol);
                parseData.setOpen(CurrOpen);
                parseData.setHigh(CurrHigh);
                parseData.setClose(CurrClose);
                parseData.setLow(CurrLow);
                parseData.setPrevClose(PreviouClose);
                parseData.setmRange(String.valueOf(CurrentDayRange));
                parseData.setDailyChange(String.valueOf(changePercent)+ " "+ "%");
                parseData.setChangePercent(CurrVolume);
                parseData.setmLatestTradingDay(Timestamp);
                mparseAlphaData.add(parseData);

                alphaDataAdapter = new AlphaDataAdapter(GraphActivity.this, mparseAlphaData);
                mrecyclerView.setAdapter(alphaDataAdapter);
                alphaDataAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onDataError = error -> error.printStackTrace();


    public CustomFragmentAdapter getCustomfragmentadapter() {
        return customfragmentadapter;
    }


    private boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void setCustomfragmentadapter(String symbol_p) {
        customfragmentadapter = new CustomFragmentAdapter(this, tabhost, viewpager, symbol_p);
        customfragmentadapter.addTab(this.tabhost.newTabSpec("1min").setIndicator("1m"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("15min").setIndicator("15m"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("30min").setIndicator("30m"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("60min").setIndicator("60m"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("D").setIndicator("D"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("W").setIndicator("W"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("M").setIndicator("M"));
        indicate = (CirclePageIndicator) findViewById(R.id.indicator);
        viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setOffscreenPageLimit(1);

        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.DKGRAY);
            ((TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title)).setTextColor(Color.WHITE);
        }
        indicate.setViewPager(viewpager);

    }


}
