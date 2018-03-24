package org.modular.module;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractModule<I,O> implements Module<I,O>{

    protected List<Module<O,?>> moduleConsumers;
    protected List<Consumer<O>> functionalConsumers;
    protected Consumer<Throwable> exceptionHandler;

    public Module<I,O> addConsumer(Module<O,?>... modules){
        if (moduleConsumers == null)
             moduleConsumers = new LinkedList<>();
        moduleConsumers.addAll(Arrays.asList(modules));
        return this;
    }

    public Module<I,O> removeConsumer(Module<O,?>... modules){
        if (moduleConsumers != null)
            moduleConsumers.removeAll(Arrays.asList(modules));
        return this;
    }

    public Module<I,O> addConsumer(Consumer<O>... consumers){
        if (functionalConsumers == null)
            functionalConsumers = new LinkedList<>();
        functionalConsumers.addAll(Arrays.asList(consumers));
        return this;
    }

    public Module<I,O> removeConsumer(Consumer<O>... consumers){
        if (functionalConsumers != null)
            functionalConsumers.removeAll(Arrays.asList(consumers));
        return this;
    }

    public void put(I input){
        try{
            O out = process(input);
            if (moduleConsumers != null && !moduleConsumers.isEmpty())
                moduleConsumers.parallelStream().forEach(mod-> mod.put(out));
        }catch (Throwable e){
            if(exceptionHandler != null)
                exceptionHandler.accept(e);
        }
    }

    @Override
    public Module<I, O> onException(Consumer<Throwable> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }
}
