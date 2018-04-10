package com.github.hasanmirzae.module;

import java.math.BigDecimal;

public class ModuleStatistics {

    private Class<? extends Module> module;

    public ModuleStatistics(Class<? extends Module> module){
        this.module = module;
    }

    public long lastProcessNanoTime =  0L;
    public BigDecimal invokations = BigDecimal.ZERO;


    @Override
    public String toString() {
        return String.format("[%s]: Invoked %d times, Last process time (ns): %d",module.getSimpleName(),invokations.intValue(),
                lastProcessNanoTime
        );
    }
}
