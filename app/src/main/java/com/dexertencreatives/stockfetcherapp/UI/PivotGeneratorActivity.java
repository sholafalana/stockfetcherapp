package com.dexertencreatives.stockfetcherapp.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dexertencreatives.stockfetcherapp.ForexCalc.PivotPointCalc;
import com.dexertencreatives.stockfetcherapp.R;
import com.dexertencreatives.stockfetcherapp.model.ParseData;
import com.dexertencreatives.stockfetcherapp.network.NetworkSingleton;
import com.dexertencreatives.stockfetcherapp.network.NetworkURLRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dexertencreatives.stockfetcherapp.UI.GraphActivity.EXTRA_SYMBOL;


public class PivotGeneratorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = PivotGeneratorActivity.class.getSimpleName();
    @BindView(R.id.delete_but)
    Button clearAll;
    @BindView(R.id.price_fetch)
    Button fetchPrice;
    @BindView(R.id.calculate)
    Button calculatePivot;
    private String symbol;

    @BindView(R.id.progressBar)
    ProgressBar loading_p_bar;

    @BindView(R.id.previousClose)
    EditText previousClose1;
    @BindView(R.id.previousHigh)
    EditText previousHigh1;
    @BindView(R.id.previousLow)
    EditText previousLow1;
    @BindView(R.id.previousopen)
    EditText previousOpen1;
    @BindView(R.id.setPivot)
    TextView pivot;
    @BindView(R.id.setR1)
    TextView rs1;
    @BindView(R.id.setR2)
    TextView rs2;
    @BindView(R.id.setR3)
    TextView rs3;
    @BindView(R.id.setS1)
    TextView ss1;
    @BindView(R.id.setS2)
    TextView ss2;
    @BindView(R.id.setS3)
    TextView ss3;
    Spinner spinner_currency, spinner_pivot;
    String spinner_Curr;
    private static final String SAVED_SORTR1 = "KeyForLayoutManagerState";
    private static final String SAVED_SORTR2 = "KeyForLayoutManagerState";
    private static final String SAVED_SORTR3 = "KeyForLayoutManagerState";
    private static final String SAVED_SORTP = "KeyForLayoutManagerState";
    private static final String SAVED_SORTS1 = "KeyForLayoutManagerState";
    private static final String SAVED_SORTS2 = "KeyForLayoutManagerState";
    private static final String SAVED_SORTS3 = "KeyForLayoutManagerState";

    private final Context context = this;
    private ArrayList<ParseData> mParseData;
    private RequestQueue mRequestQueue;
    private int SPINNER_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        spinner_currency = (Spinner) findViewById(R.id.spinner_currency);
        spinner_pivot = (Spinner) findViewById(R.id.spinner_pivotChose);

        clearAll.setOnClickListener(this);
        fetchPrice.setOnClickListener(this);
        calculatePivot.setOnClickListener(this);

        mParseData = new ArrayList<>();
        mRequestQueue = NetworkSingleton.getInstance(this).getRequestQueue();
        spinnerListener();
        updateActionBar();
        NetworkURLRequest networkURLRequest = new NetworkURLRequest();
        spinner_currency.setSelection(1);
        spinner_pivot.setSelection(0);

        Intent i1 = getIntent();
        if (i1 != null) {
            symbol = i1.getStringExtra(EXTRA_SYMBOL);

            if (symbol != null) {
                if (symbol.length() == 6) {
                    fetchDailyOHCLData(networkURLRequest.FXRequest(getString(R.string.pv_fx_daily), symbol));
                    spinner_currency.setSelection(0);
                }
            }

        }


    }

    private void spinnerListener() {
        ButterKnife.bind(this);
        spinner_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    spinner_Curr = parent.getItemAtPosition(position).toString().trim();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_pivot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SPINNER_POSITION = spinner_pivot.getSelectedItemPosition();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SPINNER_POSITION = spinner_pivot.getLastVisiblePosition();

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_but:
                resetAllData();
                break;
            case R.id.price_fetch:
                NetworkURLRequest networkURLRequest = new NetworkURLRequest();
                if (isOnline()) {
                    fetchDailyOHCLData(networkURLRequest.FXRequest(getString(R.string.daily_request), spinner_Curr));

                } else {

                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, R.string.net_error_toast, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.calculate:

                if (previousClose1.getText().toString().length() != 0 && previousHigh1.getText().toString().length() != 0 && previousOpen1.getText().toString().length() != 0 && previousLow1.getText().toString().length() != 0) {
                    computeData();
                }
                break;

        }

    }

    private void fetchDailyOHCLData(String requestURL) {
        loading_p_bar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null, onDataLoaded, onDataError);
        mRequestQueue.add(request);

    }

    private final Response.Listener<JSONObject> onDataLoaded = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            //Parse results
            try {
                //Clear current data List Before parsing
                mParseData.clear();
                JSONObject series = response.optJSONObject(getString(R.string.p_fx_daily));
                JSONObject lastData = null, secondData = null;
                for (int i = 0; i < series.names().length(); i++) {
                    if (i == 0) lastData = series.optJSONObject(series.names().getString(i));
                    if (i == 1) {
                        secondData = series.optJSONObject(series.names().getString(i));
                        break;
                    }
                }
                if (lastData == null || secondData == null) return;
                String PreviouOpen = Double.valueOf(secondData.getString(getString(R.string.p_open))).toString();
                String PreviouHigh = Double.valueOf(secondData.getString(getString(R.string.p_high))).toString();
                String PreviouLow = Double.valueOf(secondData.getString(getString(R.string.p_low))).toString();
                String PreviouClose = Double.valueOf(secondData.getString(getString(R.string.p_close))).toString();

                loading_p_bar.setVisibility(View.INVISIBLE);
                previousClose1.setText(PreviouClose);
                previousHigh1.setText(PreviouHigh);
                previousOpen1.setText(PreviouOpen);
                previousLow1.setText(PreviouLow);

                loading_p_bar.setVisibility(View.INVISIBLE);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onDataError = Throwable::printStackTrace;


    private boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void computeData() {
        ButterKnife.bind(this);
        double calcpreviousClose1;
        double calcpreviousHigh1;
        double calcpreviousLow1;
        double calcpreviousOpen1;
        double tomDemark = 0;
        calcpreviousClose1 = Double.parseDouble(previousClose1.getText().toString());
        calcpreviousHigh1 = Double.parseDouble(previousHigh1.getText().toString());
        calcpreviousLow1 = Double.parseDouble(previousLow1.getText().toString());
        calcpreviousOpen1 = Double.parseDouble(previousOpen1.getText().toString());
        PivotPointCalc pivotPointCalc = new PivotPointCalc();
        DecimalFormat df = new DecimalFormat("#.#####");

        if (SPINNER_POSITION == 0) {

            String setPivot = df.format(pivotPointCalc.CalculateStandardPivot(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R1 = df.format(pivotPointCalc.CalculateStandardR1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R2 = df.format(pivotPointCalc.CalculateStandardR2(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R3 = df.format(pivotPointCalc.CalculateStandardR3(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S1 = df.format(pivotPointCalc.CalculateStandardS1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S2 = df.format(pivotPointCalc.CalculateStandardS2(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S3 = df.format(pivotPointCalc.CalculateStandardS3(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));

            pivot.setText(setPivot);
            rs1.setText(R1);
            rs2.setText(R2);
            rs3.setText(R3);
            ss1.setText(S1);
            ss2.setText(S2);
            ss3.setText(S3);

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, R.string.standard_toast, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }


        if (SPINNER_POSITION == 1) {


            String setPivot = df.format(pivotPointCalc.CalculateFiboPivot(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R1 = df.format(pivotPointCalc.CalculateFiboR1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R2 = df.format(pivotPointCalc.CalculateFiboR2(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R3 = df.format(pivotPointCalc.CalculateFiboR3(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S1 = df.format(pivotPointCalc.CalculateFiboS1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S2 = df.format(pivotPointCalc.CalculateFiboS2(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S3 = df.format(pivotPointCalc.CalculateFiboS3(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));

            pivot.setText(setPivot);
            rs1.setText(R1);
            rs2.setText(R2);
            rs3.setText(R3);
            ss1.setText(S1);
            ss2.setText(S2);
            ss3.setText(S3);

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, R.string.fibo_toast, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }


        if (SPINNER_POSITION == 2) {


            String setPivot = df.format(pivotPointCalc.CalculateWoodiePivot(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R1 = df.format(pivotPointCalc.CalculateWoodieR1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R2 = df.format(pivotPointCalc.CalculateWoodieR2(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R3 = df.format(pivotPointCalc.CalculateWoodieR3(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S1 = df.format(pivotPointCalc.CalculateWoodieS1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S2 = df.format(pivotPointCalc.CalculateWoodieS2(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S3 = df.format(pivotPointCalc.CalculateWoodieS3(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));

            pivot.setText(setPivot);
            rs1.setText(R1);
            rs2.setText(R2);
            rs3.setText(R3);
            ss1.setText(S1);
            ss2.setText(S2);
            ss3.setText(S3);
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, R.string.woodie_toast, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


        }

        if (SPINNER_POSITION == 3) {


            String setPivot = df.format(pivotPointCalc.CalculateCamarillaPivot(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R1 = df.format(pivotPointCalc.CalculateCamarillaR1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R2 = df.format(pivotPointCalc.CalculateCamarillaR2(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String R3 = df.format(pivotPointCalc.CalculateCamarillaR3(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S1 = df.format(pivotPointCalc.CalculateCamarillaS1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S2 = df.format(pivotPointCalc.CalculateCamarillaS2(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));
            String S3 = df.format(pivotPointCalc.CalculateCamarillaS3(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1));

            pivot.setText(setPivot);
            rs1.setText(R1);
            rs2.setText(R2);
            rs3.setText(R3);
            ss1.setText(S1);
            ss2.setText(S2);
            ss3.setText(S3);
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, R.string.cama_toast, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

        if (SPINNER_POSITION == 4) {


            String setPivot = df.format(pivotPointCalc.CalculateDemarkPivot(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1, calcpreviousOpen1));
            String R1 = df.format(pivotPointCalc.CalculateDemarkR1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1, calcpreviousOpen1));
            String S1 = df.format(pivotPointCalc.CalculateDemarkS1(calcpreviousHigh1, calcpreviousLow1, calcpreviousClose1, calcpreviousOpen1));

            pivot.setText(setPivot);
            rs1.setText(R1);
            ss1.setText(S1);
            ss2.setText(R.string.clear_all_text);

            ss3.setText(R.string.clear_all_text);
            rs2.setText(R.string.clear_all_text);

            rs3.setText(R.string.clear_all_text);
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, R.string.demark_toast, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


        }


    }

    private void resetAllData() {
        ButterKnife.bind(this);

        previousClose1.setText(R.string.clear_all_text);

        previousHigh1.setText(R.string.clear_all_text);

        previousLow1.setText(R.string.clear_all_text);

        previousOpen1.setText(R.string.clear_all_text);

        pivot.setText(R.string.clear_all_text);

        rs1.setText(R.string.clear_all_text);

        rs2.setText(R.string.clear_all_text);

        rs3.setText(R.string.clear_all_text);

        ss1.setText(R.string.clear_all_text);

        ss2.setText(R.string.clear_all_text);

        ss3.setText(R.string.clear_all_text);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, R.string.data_cleared, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    private void updateActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.pivot_settitle);
    }

    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        saveState.putString(SAVED_SORTR1, rs1.getText().toString());
        saveState.putString(SAVED_SORTR2, rs2.getText().toString());
        saveState.putString(SAVED_SORTR3, rs3.getText().toString());
        saveState.putString(SAVED_SORTP, pivot.getText().toString());
        saveState.putString(SAVED_SORTS1, ss1.getText().toString());
        saveState.putString(SAVED_SORTS2, ss2.getText().toString());
        saveState.putString(SAVED_SORTS3, ss3.getText().toString());
        super.onSaveInstanceState(saveState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        rs1.setText(saveInstanceState.getString(SAVED_SORTR1));
        rs2.setText(saveInstanceState.getString(SAVED_SORTR2));
        rs3.setText(saveInstanceState.getString(SAVED_SORTR3));
        pivot.setText(saveInstanceState.getString(SAVED_SORTP));
        ss1.setText(saveInstanceState.getString(SAVED_SORTS1));
        ss2.setText(saveInstanceState.getString(SAVED_SORTS2));
        ss3.setText(saveInstanceState.getString(SAVED_SORTS3));

    }


}