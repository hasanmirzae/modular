package org.modular.module;

/**
 * Module connects to another module into one direction.
 * It takes an input and yields the output into consumer module.
 * 
 * @author Hassan_Mirzaee
 *
 * @param <I> Input type
 * @param <O> Output type
 */
public interface  Module<I,O> {
    void input(Input<I> in);
    OutPut<O> yield();
    void process();
    void connectTo(Module<O,?> consumer);
}
