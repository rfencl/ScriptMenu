package com.powin.sunspectests;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringWireupTest {
    @Ignore
    @Test
    public void testWireup() throws Exception {
        final ClassPathXmlApplicationContext mContext = new ClassPathXmlApplicationContext(
                new String[] { "/spring/sunspectests-testmain.xml" });
        mContext.start();
        mContext.stop();
        mContext.close();
    }
}
