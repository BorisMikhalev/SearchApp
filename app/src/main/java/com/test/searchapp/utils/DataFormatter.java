package com.test.searchapp.utils;


import com.test.searchapp.model.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataFormatter {
    private String unformattedValue;
    private IDataFormatter dataFormatter;
    private List<Result> data = new ArrayList<>();

    public DataFormatter(String unformattedValue, IDataFormatter dataFormatter) {
        this.unformattedValue = unformattedValue;
        this.dataFormatter = dataFormatter;
        checkSource();
    }

    private void checkSource() {
        data.clear();
        try{
            JSONObject value = new JSONObject(unformattedValue);
            if(value.has("items")){
                parseGitHubData(value.getJSONArray("items"));
            }else{
                parseWikiMediaData(value.getJSONObject("query").getJSONArray("search"));
            }
        }catch (JSONException e){
            e.printStackTrace();
            dataFormatter.error("Неверный формат полученных данных");
        }
    }

    private void parseWikiMediaData(JSONArray value) throws JSONException{
        if(!(value.length() > 0)){
            dataFormatter.error("Нет результатов поиска");
        }else{
            for(int i = 0; i < value.length() ; i++){
                data.add(new Result(value.getJSONObject(i).getString("title"),
                        value.getJSONObject(i).getString("snippet"),"http://mobi.pro-z.net/images/pictures/cartoons_026.jpg"));
            }
            dataFormatter.result(data);
        }
    }

    private void parseGitHubData(JSONArray value) throws JSONException{
        if(!(value.length() > 0)){
            dataFormatter.error("Нет результатов поиска");
        }else{
            for(int i = 0; i < value.length() ; i++){
                data.add(new Result(value.getJSONObject(i).getString("login"),
                        value.getJSONObject(i).getString("url"),value.getJSONObject(i).getString("avatar_url")));
                if (i > 100) break;
            }
            dataFormatter.result(data);
        }
    }
}
