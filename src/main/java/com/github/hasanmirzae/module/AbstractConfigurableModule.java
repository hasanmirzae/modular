package com.github.hasanmirzae.module;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractConfigurableModule<I, O> extends AbstractModule<I, O> {

    private AbstractConfigurableModule() {
    }

    protected AbstractConfigurableModule(Configuration config){
        validateConfiguration(config);
    }
    protected abstract Collection<String> requiredConfigNames();

    private void validateConfiguration(Configuration config) {
        if(requiredConfigNames() == null || requiredConfigNames().isEmpty()){
            throw new RuntimeException("Method requiredConfigNames() returned empty collection. A configurable Module must have at least 1 required parameter.");
        }
        if (!config.keySet().containsAll(requiredConfigNames())) {
            Collection<String> required = new ArrayList<>(requiredConfigNames());
            required.removeAll(config.keySet());

            throw new RuntimeException(
                    "Required parameters not found in configuration :" + required);
        }
    }


}



