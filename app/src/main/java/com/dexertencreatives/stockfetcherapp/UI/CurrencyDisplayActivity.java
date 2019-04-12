package com.dexertencreatives.stockfetcherapp.UI;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dexertencreatives.stockfetcherapp.R;
import com.dexertencreatives.stockfetcherapp.adapter.CurrDataAdapter;
import com.dexertencreatives.stockfetcherapp.model.ParseData;
import com.dexertencreatives.stockfetcherapp.network.NetworkSingleton;
import com.dexertencreatives.stockfetcherapp.network.NetworkURLRequest;
import com.dexertencreatives.stockfetcherapp.widgets.AppWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.volley.Request.Method.GET;

public class CurrencyDisplayActivity extends AppCompatActivity implements CurrDataAdapter.OnItemClickListener {
    private CurrDataAdapter mCurrDataAdapter;
    private ArrayList<ParseData> mParseData;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_msg_textview)
    TextView mErrorTextView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private static final String SAVED_SORT = "KeyForLayoutManagerState";
    private RequestQueue mRequestQueue;
    private final CharSequence toastString = "Widget Set";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        ButterKnife.bind(this);

        setTitle(getString(R.string.currency_title));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCurrDataAdapter);


        mParseData = new ArrayList<>();
        mRequestQueue = NetworkSingleton.getInstance(this).getRequestQueue();
        NetworkURLRequest networkURLRequest = new NetworkURLRequest();


        if (isOnline()) {

            hideErrorMessage();
            fetchCurrData(networkURLRequest.Get1ForgeApi());

        } else {

            showErrorMessage();
        }
        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(SAVED_SORT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }


        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isOnline()) {

                hideErrorMessage();
                fetchCurrData(networkURLRequest.Get1ForgeApi());
            } else {
                Toast.makeText(CurrencyDisplayActivity.this,
                        getResources().getText(R.string.no_connectivity_error),
                        Toast.LENGTH_LONG).show();
            }

        });


    }

    // Menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sort_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh_settings:
                if (isOnline()) {

                    hideErrorMessage();
                    NetworkURLRequest networkURLRequest = new NetworkURLRequest();
                    fetchCurrData(networkURLRequest.Get1ForgeApi());
                } else {
                    Toast.makeText(CurrencyDisplayActivity.this,
                            getResources().getText(R.string.no_connectivity_error),
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.widget_settings:
                createWidget();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void fetchCurrData(String dataUrl) {
        //  mLoadingBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(GET, dataUrl,
                null,
                array -> {
                    mParseData.clear();

                    try {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject dataObject = array.getJSONObject(i);
                            ParseData parseData = new ParseData(dataObject.optString(getString(R.string.currr_sym)), dataObject.optString(getString(R.string.currr_price)), dataObject.optString(getString(R.string.currr_time)));
                            mParseData.add(parseData);


                        }
                        mCurrDataAdapter = new CurrDataAdapter(CurrencyDisplayActivity.this, mParseData);
                        mCurrDataAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mCurrDataAdapter);
                        mCurrDataAdapter.setOnItemClickListener(CurrencyDisplayActivity.this);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> showErrorMessage()
        );
        mRequestQueue.add(jsonObjectRequest);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_SORT, mRecyclerView.getLayoutManager().onSaveInstanceState());

    }


    @Override
    public void onItemClick(int position) {
        Intent CurrDetailIntent = new Intent(this, GraphActivity.class);
        ParseData selectedSymbol = mParseData.get(position);

        CurrDetailIntent.putExtra(GraphActivity.EXTRA_SYMBOL, selectedSymbol.getSymbol());
        CurrDetailIntent.putExtra(GraphActivity.EXTRA_POSITION, position);


        startActivity(CurrDetailIntent);

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

    private void showErrorMessage() {
        ButterKnife.bind(this);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage() {
        ButterKnife.bind(this);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }

    private void createWidget() {

        String Tile1 = getString(R.string.rate_header);
        String Tile2 = getString(R.string.wid_msg);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.cuurency_widget_layout);
        ComponentName thisWidget = new ComponentName(this, AppWidget.class);
        remoteViews.setTextViewText(R.id.appwidget_head_text, Tile1);
        remoteViews.setTextViewText(R.id.appwidget_text, Tile2);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        Toast.makeText(this, toastString,
                Toast.LENGTH_LONG).show();
    }


}