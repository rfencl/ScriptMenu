package stepdefinitions;

import java.math.BigDecimal;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus123;
import com.powin.modbusfiles.utilities.PowinProperty;

import cucumber.api.java.en.When;

public class inverter123 {
	private Modbus123 inverterController;

	public inverter123() {
			inverterController = new Modbus123(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
	}
	@When("^I set inverter 103 power to (\\d+)$")
	public void i_set_inverter_103_power_to(int percentMaxPower) throws ModbusException {
		inverterController.setWMaxLimPct(BigDecimal.valueOf(percentMaxPower));
	}

}
