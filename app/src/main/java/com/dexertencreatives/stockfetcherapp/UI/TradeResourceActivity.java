package com.dexertencreatives.stockfetcherapp.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dexertencreatives.stockfetcherapp.R;
import com.dexertencreatives.stockfetcherapp.network.NetworkURLRequest;

public class TradeResourceActivity extends AppCompatActivity {

    private static final String TAG = TradeResourceActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_resource);
        setTitle(R.string.tr_title);
        String[] listItems = getResources().getStringArray(R.array.resource_names);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.custom_list_item, listItems);

        ListView listView = findViewById(R.id._listview);
        listView.setAdapter(adapter);
        listView.setBackgroundColor(getResources().getColor(R.color.brandcolor));
        listView.setOnItemClickListener(
                (adapterView, view, position, l) -> launchDetailActivity(position));
    }


    private void launchDetailActivity(int position) {
        NetworkURLRequest networkURLRequest = new NetworkURLRequest();


        switch (position) {
            case 0: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(networkURLRequest.forexintroIntent()));

                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(networkURLRequest.mt4introIntent()));

                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(networkURLRequest.markethoursIntent()));

                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(networkURLRequest.rsitradingIntent()));

                startActivity(intent);
            }
            break;
            case 4: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(networkURLRequest.fibotradingIntent()));

                startActivity(intent);
            }
            break;
            case 5: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(networkURLRequest.tradingIntent()));

                startActivity(intent);
            }
            break;

            case 6: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(networkURLRequest.scalptradingIntent()));

                startActivity(intent);
            }
            break;

            case 7: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(networkURLRequest.HFTIntent()));

                startActivity(intent);
            }
            break;


        }

    }


}