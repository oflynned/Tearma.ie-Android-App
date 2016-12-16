package com.syzible.tearma.services;

/**
 * Created by ed on 30/10/2016
 */

public abstract class Constants {
    // server addresses
    private static final String SYZIBLE_URL = "http://www.syzible.com";
    private static final String LOCAL_URL = "http://10.0.2.2:3000";
    private static final String HOST_URL = SYZIBLE_URL;

    public static final String WEB_URL = HOST_URL + "/tearma";
    public static final String REMOTE_URL = WEB_URL + "/backend";
    public static final String TOD_URL = WEB_URL + "/tod";
}
