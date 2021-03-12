package stepdefinitions;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.utilities.PowinProperty;

import cucumber.api.java.en.When;

public class battery802 {
	private Modbus802 battery;

	public battery802() {
		battery = new Modbus802(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
	}
	
	@When("^I open contactor for battery 802$")
	public void i_open_contactor_for_battery_802() {
		try {
			battery.setBatteryContactorsOff();
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@When("^I close contactor for battery 802$")
	public void i_close_contactor_for_battery_802() {
		try {
			battery.setBatteryContactorsOn();
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
