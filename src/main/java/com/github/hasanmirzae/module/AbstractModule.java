package com.github.hasanmirzae.module;

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

    public final void invoke(I input){
        try{
            O out = process(input);
            if (moduleConsumers != null && !moduleConsumers.isEmpty())
                moduleConsumers.parallelStream().forEach(mod-> mod.invoke(out));
        }catch (Throwable e){
            handleException(e);
        }
    }

    protected void handleException(Throwable e){
        if(exceptionHandler != null)
            exceptionHandler.accept(new ModuleException(getClass(),e));
    }

    @Override
    public Module<I, O> onException(Consumer<Throwable> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

}

 class ModuleException extends Throwable{

    private Class<? extends Module> modClass;

    ModuleException(Class<? extends Module> clazz, Throwable cause){
        super(String.format("[%s]: %s\n",clazz.getSimpleName(),cause.getMessage()), cause);
        this.modClass = clazz;
    }


    @Override
    public String toString() {
        return String.format("%s %s",modClass.getSimpleName(),super.toString());
    }
}



