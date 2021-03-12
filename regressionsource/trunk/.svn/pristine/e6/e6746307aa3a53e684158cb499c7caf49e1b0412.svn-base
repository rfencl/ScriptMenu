package com.powin.modbusfiles.configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.powin.modbusfiles.awe.NotifcationContactorBehavior;
import com.powin.modbusfiles.awe.CellTemperatureLimits;
import com.powin.modbusfiles.awe.CellVoltageLimits;

public enum StackType {
	STACK_140_GEN2("S20-140-200-1000-E40-derate01", "Stack140Gen2_0TurtleConfig","SetSafetyAndNotificationConfigurationStack140.csv"),
	STACK_225_GEN22("S22-225-180-1000-C271-110-derate01", "Stack225Gen2_2TurtleConfig","SetSafetyAndNotificationConfigurationStack225.csv"),
	STACK_230_GEN22("S22-230-140-1000-C280-110-derate01", "Stack230EGen2_2TurtleConfig","SetSafetyAndNotificationConfigurationStack225.csv"),
	UNDEFINED("", "","");
	
	public String stackConfigurationName;
	public String turtleCommandConfigurationName;
	public String safetyAndNotificationConfigurationFile;
	
	
	
	public static Stream<StackType> stream() {
		return Stream.of(StackType.values());
	}
	
   public static StackType fromStackConfigurationName(final String configName) {
	   StackType found = StackType.UNDEFINED;
	    List<StackType> list = StackType.stream()
	    .filter(d -> d.stackConfigurationName.equals(configName))
	    .collect(Collectors.toList());
	     found = list.isEmpty() ? found : list.get(0);
	return found;
   }
   
   public static StackType fromTurtleCommandConfigurationName(final String configName) {
	   StackType found = StackType.UNDEFINED;
	    List<StackType> list = StackType.stream()
	    .filter(d -> d.turtleCommandConfigurationName.equals(configName))
	    .collect(Collectors.toList());
	     found = list.isEmpty() ? found : list.get(0);
	return found;
   }
   
   public static StackType fromSafetyAndNotificationConfigurationFile(final String configName) {
	   StackType found = StackType.UNDEFINED;
	    List<StackType> list = StackType.stream()
	    .filter(d -> d.safetyAndNotificationConfigurationFile.equals(configName))
	    .collect(Collectors.toList());
	     found = list.isEmpty() ? found : list.get(0);
	return found;
   }
   
   public static StackType fromName(final String name) {
	   StackType found = StackType.UNDEFINED;
	    List<StackType> list = StackType.stream()
	    .filter(d -> d.name().equals(name))
	    .collect(Collectors.toList());
	     found = list.isEmpty() ? found : list.get(0);
	return found;
   }

	public  CellVoltageLimits getCellVoltageLimits() {
		CellVoltageLimits limit = null;
		switch(this) {
			case STACK_140_GEN2:
				limit= (new CellVoltageLimits()).get140();
				break;
			case STACK_225_GEN22:
				limit= (new CellVoltageLimits()).get225();
				break;
			case UNDEFINED:
				break;
			default:
				break;
		}
		return limit ;	
	}
	
	public  CellTemperatureLimits getCellTemperatureLimits() throws InstantiationException, IllegalAccessException {
		CellTemperatureLimits limit = null;
		switch(this) {
			case STACK_140_GEN2:
				limit= (new CellTemperatureLimits()).get140();
				break;
			case STACK_225_GEN22:
				limit= (new CellTemperatureLimits()).get225();
				break;
			case STACK_230_GEN22:
				limit= (new CellTemperatureLimits()).get230();
				break;
			case UNDEFINED:
				break;
			default:
				break;
		}
		return limit ;	
	}
	
	public  NotifcationContactorBehavior getNotificationBehavior() {
		NotifcationContactorBehavior limit = null;
		switch(this) {
			case STACK_140_GEN2:
				limit= (new NotifcationContactorBehavior()).get140();
				break;
			case STACK_225_GEN22:
				limit= (new NotifcationContactorBehavior()).get225();
				break;
			case UNDEFINED:
				break;
			default:
				break;
		}
		return limit ;	
	}
	
	StackType (String stackConfigName, String turtleConfigName, String safetyAndNotificationConfigFile) {
		stackConfigurationName = stackConfigName;
		turtleCommandConfigurationName = turtleConfigName;
		safetyAndNotificationConfigurationFile=safetyAndNotificationConfigFile;
	}

	
}


