package com.github.hasanmirzae.module;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Configuration extends HashMap<String, String> {

    public Configuration(){
        super();
    }

    public Configuration(String configSrt){
        parse(configSrt);
    }

    private void parse(String configStr) {
        try{
            StringTokenizer st = new StringTokenizer(configStr,",");
            String pair;
            while (st.hasMoreTokens()) {
                pair = st.nextToken();
                put(pair.split(":")[0],pair.split(":")[1]);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public Configuration add(String name, String value){
        put(name,value);
        return this;
    }

}
