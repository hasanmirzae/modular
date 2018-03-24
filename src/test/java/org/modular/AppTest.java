package org.modular;


import org.junit.Test;
import org.modular.module.impl.SchedulerConfig;
import org.modular.module.impl.SchedulerModule;
import org.modular.module.impl.Status;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @Test
    public void test() throws InterruptedException {


        SchedulerModule scheduler = new SchedulerModule(new SchedulerConfig(1),()-> System.out.println("Task called!"));
        scheduler.onException(System.out::println);
        scheduler.invoke(Status.ACTIVE);

        TimeUnit.SECONDS.sleep(10);


    }


}
