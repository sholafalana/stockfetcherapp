package com.dexertencreatives.stockfetcherapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dexertencreatives.stockfetcherapp.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends AppCompatActivity {
    @BindView(R.id.market_rate)
    Button btMarketRate;
    @BindView(R.id.pivot_generator)
    Button btSignalGen;
    @BindView(R.id.trade_journal)
    Button mTradeJournal;
    @BindView(R.id.convert_curr)
    Button mCurrConvert;
    @BindView(R.id.trade_resource)
    Button mTradeResource;
    private static final String SAVED_SORT = "KeyForLayoutManagerState";

    @BindView(R.id.stock_search_edittext)
    EditText seacrchText;
    @BindView(R.id.search_button)
    ImageButton SearchButton;

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btMarketRate.setOnClickListener(v -> {
            Intent CurrDetailIntent = new Intent(v.getContext(), CurrencyDisplayActivity.class);
            startActivity(CurrDetailIntent);
            Bundle bundle = new Bundle();

            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, getString(R.string.button_one));
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.Curr_display));
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, getString(R.string.Button_click));
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        });

        btSignalGen.setOnClickListener(v -> {
            Intent pivotIntent = new Intent(v.getContext(), PivotGeneratorActivity.class);
            startActivity(pivotIntent);
        });
        mTradeResource.setOnClickListener(v -> {
            Intent currIntent = new Intent(v.getContext(), TradeResourceActivity.class);
            startActivity(currIntent);
        });
        mCurrConvert.setOnClickListener(v -> {
            Intent currIntent = new Intent(v.getContext(), CurrencyExchangeActivity.class);
            startActivity(currIntent);
        });

        mTradeJournal.setOnClickListener(v -> {
            Intent tjIntent = new Intent(v.getContext(), TradeJournalActivity.class);
            startActivity(tjIntent);
        });

        SearchButton.setOnClickListener(v -> {

            String sym = seacrchText.getText().toString().trim();
            if (sym.length() != 0) {
                Intent graphIntent = new Intent(v.getContext(), GraphActivity.class);
                graphIntent.putExtra(GraphActivity.EXTRA_SYMBOL, sym);
                startActivity(graphIntent);
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Live_rate:
                Intent CurrDetailIntent = new Intent(HomePageActivity.this, CurrencyDisplayActivity.class);
                startActivity(CurrDetailIntent);

                return true;
            case R.id.pivot_gen:
                Intent pivotIntent = new Intent(HomePageActivity.this, PivotGeneratorActivity.class);
                startActivity(pivotIntent);
                return true;
            case R.id.curr_conv:
                Intent currConvIntent = new Intent(HomePageActivity.this, CurrencyExchangeActivity.class);
                startActivity(currConvIntent);
                return true;
            case R.id.trade_journal:
                Intent journalIntent = new Intent(HomePageActivity.this, TradeJournalActivity.class);
                startActivity(journalIntent);

                return true;
            case R.id.trade_resources:
                Intent trIntent = new Intent(HomePageActivity.this, TradeResourceActivity.class);
                startActivity(trIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        saveState.putString(SAVED_SORT, seacrchText.getText().toString());
        super.onSaveInstanceState(saveState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        seacrchText.setText(saveInstanceState.getString(SAVED_SORT));

    }
}
