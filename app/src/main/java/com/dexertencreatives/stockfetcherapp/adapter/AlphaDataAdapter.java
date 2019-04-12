package com.dexertencreatives.stockfetcherapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexertencreatives.stockfetcherapp.R;
import com.dexertencreatives.stockfetcherapp.model.ParseAlphaData;
import com.dexertencreatives.stockfetcherapp.network.Formatter;

import java.util.ArrayList;

public class AlphaDataAdapter extends RecyclerView.Adapter<AlphaDataAdapter.ParseDataViewHolder> {

    private Context mContext;
    private ArrayList<ParseAlphaData> mParseData;

    public AlphaDataAdapter(Context context, ArrayList<ParseAlphaData> parseData) {
        mContext = context;
        mParseData = parseData;

    }

    @NonNull
    @Override
    public ParseDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_alphadata, parent, false);
        return new ParseDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParseDataViewHolder holder, int position) {
        ParseAlphaData currentDataItem = mParseData.get(position);


        //Get Items to bind to view
        String CurrSymbol = currentDataItem.getSymbol();
        String CurrOpen = currentDataItem.getOpen();
        String CurrHigh = currentDataItem.getHigh();

        String CurrClose = currentDataItem.getClose();
        String CurrLow = currentDataItem.getLow();

        String CurrPrevClose = currentDataItem.getPrevClose();
        String CurrRange = currentDataItem.getRange();

        //Bind data to view
        double priceIndicator = Double.valueOf(CurrClose) - Double.valueOf(CurrOpen);
        double pChange = Formatter.formatter((priceIndicator / Double.valueOf(CurrOpen))*100, 2);
        String pChangeText = String.valueOf(pChange)+ " "+"%" ;

        String CurrPriceChange = currentDataItem.getDailyChange();
        String CurrPercentChange = currentDataItem.getChangePercent() ;




        holder.mTitleTextView.setText(CurrSymbol);
        holder.mTitleTextView2.setText(CurrOpen);
        holder.mTitleTextView3.setText(CurrHigh);
        holder.mTitleTextView4.setText(CurrClose);
        holder.mTitleTextView5.setText(CurrLow);
        holder.mTitleTextView6.setText(CurrPrevClose);
        holder.mTitleTextView7.setText(CurrRange);
        holder.mTitleTextView8.setText(pChangeText);
        holder.mTitleTextView9.setText(CurrPercentChange);
        if (priceIndicator > 0 ){
            holder.mImageView.setImageResource(R.drawable.ic_arrow_drop_up_black_36dp);
            holder.mImageView.setVisibility(View.VISIBLE);
        }

        if (priceIndicator < 0 ){
            holder.mImageView.setImageResource(R.drawable.ic_arrow_drop_down_black_36dp);
            holder.mImageView.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return mParseData.size();
    }

    public class ParseDataViewHolder extends RecyclerView.ViewHolder {


        TextView mTitleTextView;
        TextView mTitleTextView2;
        TextView mTitleTextView3;
        TextView mTitleTextView4;
        TextView mTitleTextView5;
        TextView mTitleTextView6;
        TextView mTitleTextView7;
        TextView mTitleTextView8;
        TextView mTitleTextView9;
        ImageView mImageView;

        public ParseDataViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.symbol_data);
            mTitleTextView2 = itemView.findViewById(R.id.data_open);

            mTitleTextView3 = itemView.findViewById(R.id.data_high);
            mTitleTextView4 = itemView.findViewById(R.id.data_close);

            mTitleTextView5 = itemView.findViewById(R.id.data_low);
            mTitleTextView6 = itemView.findViewById(R.id.data_prev_close);

            mTitleTextView7 = itemView.findViewById(R.id.data_range);
            mTitleTextView8 = itemView.findViewById(R.id.data_price_change);

            mTitleTextView9 = itemView.findViewById(R.id.data_percent_change);
            mImageView = itemView.findViewById(R.id.symbol_floatbutton);


        }
    }
}
