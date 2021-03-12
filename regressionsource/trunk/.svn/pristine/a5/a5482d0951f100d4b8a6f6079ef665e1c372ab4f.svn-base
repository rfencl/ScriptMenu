package com.powin.modbusfiles.configuration;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StackTypeTest {

	@Test
	void testFromName() throws Exception{
		assertEquals(StackType.STACK_140_GEN2, StackType.fromName("STACK_140_GEN2"));
		assertEquals(StackType.STACK_225_GEN22, StackType.fromName("STACK_225_GEN22"));
		assertEquals(StackType.STACK_230_GEN22, StackType.fromName("STACK_230_GEN22"));
	}

	@Test
	void testFromNameNULL() throws Exception{
		assertNull(StackType.fromName(null));
	}
	
	@Test
	void testFromConfigurationName() throws Exception{
		assertEquals(StackType.STACK_140_GEN2, StackType.fromStackConfigurationName("S20-140-200-1000-E40-derate01"));
		assertEquals(StackType.STACK_225_GEN22, StackType.fromStackConfigurationName("S22-225-180-1000-C271-110-derate01"));
		assertEquals(StackType.STACK_230_GEN22, StackType.fromStackConfigurationName("S22-230-140-1000-C280-110-derate01"));
	}
	
	@Test
	void testFromTurtleConfigurationName() throws Exception{
		assertEquals(StackType.STACK_140_GEN2, StackType.fromTurtleCommandConfigurationName("Stack140Gen2_0TurtleConfig"));
		assertEquals(StackType.STACK_225_GEN22, StackType.fromTurtleCommandConfigurationName("Stack225Gen2_2TurtleConfig"));
		assertEquals(StackType.STACK_230_GEN22, StackType.fromTurtleCommandConfigurationName("Stack230EGen2_2TurtleConfig"));
	}
}
