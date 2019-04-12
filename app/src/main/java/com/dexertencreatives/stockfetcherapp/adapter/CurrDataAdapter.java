package com.dexertencreatives.stockfetcherapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dexertencreatives.stockfetcherapp.R;
import com.dexertencreatives.stockfetcherapp.model.ParseData;


import java.util.ArrayList;

/**
 * Created by shola on 11/13/2018.
 */

public class CurrDataAdapter extends RecyclerView.Adapter<CurrDataAdapter.ParseDataViewHolder> {

    private Context mContext;
    private ArrayList<ParseData> mParseData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CurrDataAdapter(Context context, ArrayList<ParseData> parseData) {
        mContext = context;
        mParseData = parseData;

    }

    @NonNull
    @Override
    public ParseDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_layout, parent, false);
        return new ParseDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParseDataViewHolder holder, int position) {
        ParseData currentDataItem = mParseData.get(position);


        //Get Items to bind to view
        String CurrSymbol = currentDataItem.getSymbol();
        String CurrPrice = currentDataItem.getPrice();

        //Bind data to view
        holder.mTitleTextView.setText(CurrPrice);
        holder.mTitleTextView2.setText(CurrSymbol);

    }

    @Override
    public int getItemCount() {
        return mParseData.size();
    }

    public class ParseDataViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView2;
        public TextView mTitleTextView;

        public ParseDataViewHolder(View itemView) {
            super(itemView);
            mTitleTextView2 = itemView.findViewById(R.id.textView2);
            mTitleTextView = itemView.findViewById(R.id.price_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
