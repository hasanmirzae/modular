package org.modular.module;

import java.io.IOException;
import java.util.function.Consumer;

public interface Module<I,O> {

    O process(I input) throws IOException;

    Module<I,O> addConsumer(Module<O,?>... modules);

    Module<I,O> removeConsumer(Module<O,?>... modules);

    Module<I,O> addConsumer(Consumer<O>... consumers);

    Module<I,O> removeConsumer(Consumer<O>... consumers);

    void invoke(I input);

    Module<I,O> onException(Consumer<Throwable> exceptionHandler);

}
