package com.sandbox.runtime.models;

/**
 * Created by nickhoughton on 7/02/2016.
 */
public enum RuntimeVersion {
    VERSION_1,
    VERSION_2;

    public static RuntimeVersion getLatest(){
        return VERSION_2;
    }

    public static String toCLIString() {
        return VERSION_1 + "|" + VERSION_2;
    }
}
