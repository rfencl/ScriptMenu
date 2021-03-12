package stepdefinitions;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.utilities.PowinProperty;

import cucumber.api.java.en.When;

public class powinBlock {
	private ModbusPowinBlock powinBlock;
	
	public powinBlock () {
		powinBlock = ModbusPowinBlock.getModbusPowinBlock();
	}

	@When("^I enable app \"([^\"]*)\"$")
	public void i_enable_app(String appName) throws ModbusException {
		powinBlock.enableApp(appName);
	}

	@When("^I disable app \"([^\"]*)\"$")
	public void i_disable_app(String appName) throws ModbusException {
		powinBlock.disableApp(appName);
	}

}
