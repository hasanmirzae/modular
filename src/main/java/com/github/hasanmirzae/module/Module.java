package com.github.hasanmirzae.module;

import java.util.UUID;
import java.util.function.Consumer;

public interface Module<I,O> {

    O process(I input);

    Module<I,O> addConsumer(Module<O,?>... modules);

    Module<I,O> removeConsumer(Module<O,?>... modules);

    Module<I,O> addConsumer(Consumer<O>... consumers);

    Module<I,O> removeConsumer(Consumer<O>... consumers);

    void invoke(I input);

    Module<I,O> onException(Consumer<Throwable> exceptionHandler);

    Module<I,O> recordStatistics(boolean recordStatistic);

    ModuleStatistics getStatistics();

    UUID getUuid();

}
