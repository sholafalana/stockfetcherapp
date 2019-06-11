package com.dexertencreatives.stockfetcherapp.network;

import com.dexertencreatives.stockandforexfetcher.model.Coin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by USER on 5/12/2019.
 */

public interface CoinTrackApiService {
    @GET("/front")
    Call<List<Coin>> getFrontPageCoinData();
}
