package com.github.hasanmirzae.module;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModuleStatisticsTest {

    class SampleModule extends AbstractModule<String,String>{
        @Override protected Function<String, String> getLogic() {
            return input -> "Success";
        }
    }

    @Test
    public void testRecordingStatistics(){

        Module<String,String> sampleMod = new SampleModule();
        // recording on
        sampleMod.recordStatistics(true).process("");
        // check if recorded
        assertEquals(1, sampleMod.getStatistics().invokations.intValue());
        assertTrue(sampleMod.getStatistics().lastProcessNanoTime > 0);
        // recording off
        sampleMod.recordStatistics(false);
        sampleMod.process("");
        // should not record
        assertEquals(1, sampleMod.getStatistics().invokations.intValue());

    }
}
