package com.test.searchapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.searchapp.adapters.SearchArrayAdapter;
import com.test.searchapp.utils.DataFormatter;
import com.test.searchapp.utils.IDataFormatter;
import com.test.searchapp.R;
import com.test.searchapp.model.Result;
import com.test.searchapp.utils.HttpConnection;
import com.test.searchapp.utils.IConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private boolean gitHubTab = false;
    private ArrayList<Result> data = new ArrayList<>();
    private ProgressBar pg;
    private TextView infoTxt;
    private ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.search_fragment, container, false);
            pg = (ProgressBar) view.findViewById(R.id.pg);
            infoTxt = (TextView) view.findViewById(R.id.infoTxt);
            list = (ListView) view.findViewById(R.id.list);
            pg.setVisibility(View.GONE);
            infoTxt.setText(R.string.inputMsg);
        if(savedInstanceState != null){
            infoTxt.setVisibility(View.GONE);
            data = savedInstanceState.getParcelableArrayList("data");
            gitHubTab = savedInstanceState.getBoolean("gitHubTab");
            updateAdapter();
        }
        return view;
    }

    public void setGitHubTab(boolean gitHubTab) {
        this.gitHubTab = gitHubTab;
    }

    public void startSearch(String searchValue){
        if(!(data.isEmpty())){
            data.clear();
            updateAdapter();
        }
        pg.setVisibility(View.VISIBLE);
        infoTxt.setVisibility(View.VISIBLE);
        infoTxt.setText(R.string.loadingMsg);
        URL url = null;
        try{
            if(gitHubTab){
                url = queryGitHub(searchValue);
            }else{
                url = queryWikiMedia(searchValue);
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        if(url != null) {
            new HttpConnection(url, new IConnection() {
                @Override
                public void result(String s) {
                    infoTxt.setText(R.string.workingMsg);
                    new DataFormatter(s, new IDataFormatter() {
                        @Override
                        public void result(List<Result> newData) {
                            data.addAll(newData);
                            pg.setVisibility(View.GONE);
                            infoTxt.setVisibility(View.GONE);
                            updateAdapter();
                        }

                        @Override
                        public void error(String error) {
                            pg.setVisibility(View.GONE);
                            infoTxt.setText(error);
                        }
                    });
                }

                @Override
                public void error() {
                    pg.setVisibility(View.GONE);
                    infoTxt.setText(R.string.errorMsg);
                }
            }).execute();
        }else{
            infoTxt.setText(R.string.incorrectMsg);
        }
    }

    private void updateAdapter(){
        SearchArrayAdapter adaptor = new SearchArrayAdapter(getContext(),data);
        list.setAdapter(adaptor);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("data",data);
        outState.putBoolean("gitHubTab",gitHubTab);
    }

    private URL queryGitHub(String searchValue) throws MalformedURLException{
        return new URL(getResources().getString(R.string.gitHubUrl) + searchValue);
    }

    private URL queryWikiMedia(String searchValue) throws MalformedURLException{
        return new URL(getResources().getString(R.string.wikiMediaUrl) + searchValue
                + getResources().getString(R.string.wikiMediaLimitUrl));
    }
}
