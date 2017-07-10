package com.test.searchapp.utils;


import com.test.searchapp.model.Result;

import java.util.List;

public interface IDataFormatter {
    void result(List<Result> newData);
    void error(String error);
}
