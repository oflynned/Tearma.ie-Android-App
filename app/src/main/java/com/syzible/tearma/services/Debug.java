package com.syzible.tearma.services;

import java.util.Hashtable;
import java.util.Set;

/**
 * Created by ed on 29/11/2016
 */

public class Debug {
    public static void printHashTable(Hashtable<?, ?> hash) {
        Set<?> hashSet = hash.keySet();

        for (Object setKey : hashSet) {
            String key = (String) setKey;
            String value = (String) hash.get(key);
            System.out.println(key + ": " + value);
        }
    }
}
