package com.dexertencreatives.stockfetcherapp.network;

/**
 * Created by shola on 11/16/2018.
 */

public class NetworkURLRequest {

    String alphaAPIKey = "5LVUV1ZEVCZ8RNVJ";
    String fOAPIKey = "EvF59GsluGh3KtkdWSmdy4eOPzff00Zk";

    public String FXRequest(String FXPeriod, String theSymbol) {
        char firstLetter = theSymbol.charAt(0);
        char secondLetter = theSymbol.charAt(1);
        char thirdLetter = theSymbol.charAt(2);
        char tofirstLetter = theSymbol.charAt(3);
        char tosecondLetter = theSymbol.charAt(4);
        char tothirdLetter = theSymbol.charAt(5);
        String FromSymbol = Character.toString(firstLetter) + Character.toString(secondLetter) + Character.toString(thirdLetter);
        String ToSymbol = Character.toString(tofirstLetter) + Character.toString(tosecondLetter) + Character.toString(tothirdLetter);

        String requestURL = "https://www.alphavantage.co/query?function=" + FXPeriod + "&from_symbol=" + FromSymbol + "&to_symbol=" + ToSymbol + "&apikey=" + alphaAPIKey;

        return requestURL;
    }

    public String GlobalQuoteRequest(String theSymbol) {


        String requestURL1 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + theSymbol + "&apikey=" + alphaAPIKey;
        return requestURL1;
    }


    public String Get1ForgeApi() {

        String requestURL = "https://forex.1forge.com/1.0.3/quotes?pairs=EURUSD,GBPJPY,AUDUSD,GBPUSD,EURGBP,EURJPY,USDCAD,USDCHF,USDJPY,GBPAUD,EURAUD,AUDNZD,GBPCAD,EURCAD,NZDUSD,XAUUSD,BTCUSD&api_key=" + fOAPIKey;
        return requestURL;
    }

    public String Get1ForgeApiRate1(String symbol) {

        String requestURL = "https://forex.1forge.com/1.0.3/quotes?pairs=" + symbol + "&api_key=" + fOAPIKey;
        return requestURL;
    }

    public String forexintroIntent() {
        String requestURL = "https://www.youtube.com/watch?v=h68UkEyC8Ww";
        return requestURL;
    }

    public String mt4introIntent() {
        String requestURL = "https://www.youtube.com/watch?v=gImnMtr9Ob8";
        return requestURL;
    }

    public String markethoursIntent() {
        String requestURL = "https://www.youtube.com/watch?v=kzyUhD60JgQ";
        return requestURL;
    }

    public String rsitradingIntent() {
        String requestURL = "https://www.youtube.com/watch?v=5_vQIa5MrqY";
        return requestURL;
    }

    public String fibotradingIntent() {
        String requestURL = "https://www.youtube.com/watch?v=R6ft90FLI-I";
        return requestURL;
    }

    public String tradingIntent() {
        String requestURL = "https://www.youtube.com/watch?v=sPkn5DSQG40";
        return requestURL;
    }

    public String scalptradingIntent() {
        String requestURL = "https://www.youtube.com/watch?v=5wgd08TfK2U";
        return requestURL;
    }


    public String HFTIntent() {
        String requestURL = "https://www.youtube.com/watch?v=WstJM_aNSj8";
        return requestURL;
    }


}