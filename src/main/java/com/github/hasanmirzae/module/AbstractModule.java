package com.github.hasanmirzae.module;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractModule<I,O> implements Module<I,O>{

    protected List<Module<O,?>> moduleConsumers;
    protected List<Consumer<O>> functionalConsumers;
    protected Consumer<Throwable> exceptionHandler;
    protected ModuleStatistics statistics = new ModuleStatistics(getClass());
    protected Function<I,O> logic = getLogic();
    protected UUID uuid;


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
            O out = logic.apply(input);
            if (moduleConsumers != null && !moduleConsumers.isEmpty())
                moduleConsumers.parallelStream().forEach(mod-> mod.invoke(out));
        }catch (Throwable e){
            handleException(e);
        }
    }

    protected abstract Function<I,O> getLogic();

    public O process(I input){
        return logic.apply(input);
    }

    private Function<I,O> logicWithStatistics(){
        return input -> {
            statistics.lastProcessNanoTime = System.nanoTime();
            O out = getLogic().apply(input);
            statistics.lastProcessNanoTime = System.nanoTime() - statistics.lastProcessNanoTime;
            statistics.invokations = statistics.invokations.add(BigDecimal.ONE);
            return out;
        };
    }

    /**
     * Turn on/off recording statistics.
     * @param recordStatistic on/off
     */
    public final Module<I,O> recordStatistics(boolean recordStatistic){
        this.logic = recordStatistic ? logicWithStatistics() : getLogic();
        return this;
    }

    public ModuleStatistics getStatistics(){
        return statistics;
    }

    protected void handleException(Throwable e){
        if(exceptionHandler != null)
            exceptionHandler.accept(new ModuleException(getClass(),e));
    }

    @Override
    public Module<I, O> onException(Consumer<Throwable> handler) {
        exceptionHandler = handler;
        return this;
    }

    @Override
    public UUID getUuid() {
        if (this.uuid == null){
            this.uuid = UUID.fromString(toString()+System.nanoTime());
        }
        return this.uuid;
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



