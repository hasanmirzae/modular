package com.github.hasanmirzae.module;

import java.util.HashMap;

public class Configuration extends HashMap<String, String> {

    public Configuration add(String name, String value){
        put(name,value);
        return this;
    }

}
