package com.powin.modbusfiles.awe;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbusfiles.awe.NotificationCodes;
public class NotificationCodesTest {


    @BeforeClass
    public static void beforeClass() {
        System.out.println("@BeforeClass");
    }
	@Test
	public void testFromCode() {
		assertThat(NotificationCodes.fromCode("2561").name(), is("STRING_OUT_OF_ROTATION_WARNING"));
		assertThat(NotificationCodes.fromCode(2561).name(), is("STRING_OUT_OF_ROTATION_WARNING"));
		assertThat(NotificationCodes.fromCode("dummy"), is(NotificationCodes.UNDEFINED));
	}

}
