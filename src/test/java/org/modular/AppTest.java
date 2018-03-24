package org.modular;


import org.junit.Test;
import org.modular.module.impl.SchedulerConfig;
import org.modular.module.impl.SchedulerModule;
import org.modular.module.impl.SchedulerStatus;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @Test
    public void test() throws InterruptedException {


        new SchedulerModule(new SchedulerConfig(1),()-> {
            if (new Random().nextInt(4)==2)
                throw new RuntimeException("Something bad happend");
            else
                System.out.println("Task called!");
        })
        .onException(System.out::println)
        .invoke(SchedulerStatus.ACTIVE);

        TimeUnit.SECONDS.sleep(10);


    }


}
