package com.powin.modbusfiles.modbus;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.SunspecMasterEndpoint;
import com.powin.modbus.sunspec.SunspecTcpMasterEndpointFactory;
import com.powin.modbus.sunspec.blocks.PowinAppsBlockCommon.AppTypeEnum;
import com.powin.modbus.sunspec.blocks.PowinAppsBlockMaster;
import com.powin.modbus.sunspec.blocks.PowinBlockCommon.BasicOpPriorityCommandEnum;
import com.powin.modbus.sunspec.blocks.PowinBlockCommon.BasicOpStatusEnum;
import com.powin.modbus.sunspec.blocks.PowinBlockCommon.BasicOpTriggerCommandEnum;
import com.powin.modbus.sunspec.blocks.PowinBlockMaster;
import com.powin.modbusfiles.apps.PowerApps;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class ModbusPowinBlock {
    private final static Logger LOG = LogManager.getLogger(ModbusPowinBlock.class.getName());

    private final String cModbusHostName;
    private final int cModbusPort;
    private final int cModbusUnitId;
    private final boolean cEnableModbusLogging;

    private PowinBlockMaster cPowinBlockMaster;
    private PowinAppsBlockMaster cPowinAppsBlockMaster;
    private SunspecMasterEndpoint cSunspecMasterEndpoint;

    private ModbusPowinBlock(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
        cModbusHostName = modbusHostName;
        cModbusPort = modbusPort;
        cModbusUnitId = modbusUnitId;
        cEnableModbusLogging = enableModbusLogging;
        init();
    }

    public static ModbusPowinBlock getModbusPowinBlock() {
    	return new ModbusPowinBlock(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
    }
    
    private void init() {
        try {
        	  boolean mModBusNull = true;
        	  int counter = 0;
        	  SunspecTcpMasterEndpointFactory mSunspecMasterEndpointFactory = null;
        	        while (mModBusNull && counter < 5) {
        	            try {
        					mSunspecMasterEndpointFactory = new SunspecTcpMasterEndpointFactory(cModbusHostName, cModbusPort, cModbusUnitId,
        				        cEnableModbusLogging, 2000);
        					cSunspecMasterEndpoint = mSunspecMasterEndpointFactory.getSunspecMasterEndpoint(null);
                            mModBusNull = null == cSunspecMasterEndpoint;
        	            }
        	            catch (Exception e) {
        	            	//LOG.error("counter = {}",counter, e);
        	                CommonHelper.sleep(5000);
        					counter++;
        	            }
        	        }
        	       // LOG.info("Tried {} times, modBusNull = {}",counter, mModBusNull);

			cPowinBlockMaster = cSunspecMasterEndpoint.getBlock(65123, 0);
			cPowinAppsBlockMaster = cSunspecMasterEndpoint.getBlock(65133, 0);
			if (cPowinBlockMaster == null) {
			    throw new IllegalStateException("No powin block!\n Add <Parameter name=\"dragon.sunspec.include65123\" value=\"true\" />  to turtle.xml");
			}
			if (cPowinAppsBlockMaster == null) {
			    throw new IllegalStateException("No powin apps block!\n Add <Parameter name=\"dragon.sunspec.include65133\" value=\"true\" />  to turtle.xml");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void setAppStatus(String app, int status) {
        int appIndex = getAppIndex(app);
        if (appIndex != -1) {
            try {
				cPowinAppsBlockMaster.setAppStatusControlValue(appIndex, status);
	            LOG.info("Setting {} to {}", app, status == 1 ? "enabled" : "disabled");
			} catch (ModbusException e) {
				LOG.error("Error accessing PowinAppsBlockMaster.",  e);
				throw new RuntimeException("Error trying to set app status");
			} 
        }
    }

    /**
     * Return the enabled Status
     *
     * @param app "SunspecPowerCommand" | "BasicOp"
     * @return
     * @throws ModbusException
     */
    public int getAppStatus(String app) throws ModbusException {
        int ret = -1;
        int appIndex = this.getAppIndex(app);
        if (appIndex != -1) {
            ret = cPowinAppsBlockMaster.getAppStatusValue(appIndex);//.getAppStatusControlValue(appIndex);
        }
        return ret;
    }

    public void enableApp(String app)  {
        this.setAppStatus(app, 1);
    }

    public void disableApp(String app)  {
        setAppStatus(app, 0);
    }

    public void disableBasicOp()  {
        disableApp(PowerApps.BasicOp.name());
    }

    public void disableSunspec()  {
        disableApp(PowerApps.SunspecPowerCommand.name());
    }

    public void enableBasicOp()  {
        enableApp(PowerApps.BasicOp.name());
    }

    public void enableSunspec()  {
        enableApp(PowerApps.SunspecPowerCommand.name());
    }

    public AppTypeEnum getAppType(int app)  {
        AppTypeEnum appType;
		try {
			appType = cPowinAppsBlockMaster.getAppType(app);
		} catch (ModbusException e) {
			LOG.error("Error access PowinAppsBlockMaster",  e);
			throw new RuntimeException(e.getMessage());
		}
		return appType;

    }

    public int getApplicationTypeValue(int app) throws ModbusException {
        return cPowinAppsBlockMaster.getAppTypeValue(app); // Enable/Disable app

    }

    public int getAvailableChargePowerCapacity() throws ModbusException {
        return cPowinBlockMaster.getAvailableChargePowerCapacity().intValue(); // Enable/Disable app

    }

    public int getNumApps() throws ModbusException {
    	int numberOfApps = 0;
    	try {
    		numberOfApps = cPowinBlockMaster.getNumberofApps();
		} catch (ModbusException e) {
          LOG.error("Error getting apps from PowinBlockMaster",  e);
          throw e;
		} 
        return numberOfApps;
    }

    public long configureScheduler() throws ModbusException {
        return cPowinBlockMaster.getSchedulerFlags();

    }

    @SuppressWarnings("unused")
    public void configurePowerCommandApp() throws ModbusException {
        // return cPowinBlockMaster.;

    }

    public void runSimpleBasicOpCommand(BigDecimal power) throws ModbusException {
        cPowinBlockMaster.setSimpleBasicOpCommand(power);
    }

    @Deprecated
    public void runSimpeBasicCommand(BigDecimal power) throws ModbusException {
        // return cPowinBlockMaster.;
        cPowinBlockMaster.setSimpleBasicOpCommand(power);
    }

    @Deprecated
    public void runSimpeBasicCommandPrioritySOC(BigDecimal power) throws ModbusException {
        cPowinBlockMaster.setSimpleBasicOpCommand(power);
    }

    @Deprecated
    public void runSimpeBasicCommandPriorityPower(BigDecimal power) throws ModbusException {
        cPowinBlockMaster.setSimpleBasicOpCommand(power);
    }

    @Deprecated
    public void runSimpeBasicCommandOff() throws ModbusException {
        cPowinBlockMaster.setSimpleBasicOpCommand(BigDecimal.ZERO);
    }

    public String getStatus() throws ModbusException {
        BasicOpStatusEnum status = cPowinBlockMaster.getBasicOpStatus();
        return status.toString();
    }

    public BasicOpStatusEnum getStatusEnum() throws ModbusException {
        return cPowinBlockMaster.getBasicOpStatus();
    }

    public void setBasicOpPrioritySOC(int soc, int power) throws ModbusException {
        // cPowinBlockMaster.setW_SF(3);
        // cPowinBlockMaster.setSoC_SF(0);
        cPowinBlockMaster.setBasicOpPriorityCommand(BasicOpPriorityCommandEnum.SOC);
		// cPowinBlockMaster.setBasicOpTargetPowerCommand(BigDecimal.valueOf(power *
		// Math.pow(10, cPowinBlockMaster.getW_SF())));
		cPowinBlockMaster.setBasicOpTargetPowerCommand(BigDecimal.valueOf(power));
        cPowinBlockMaster.setBasicOpTargetSOCCommand(BigDecimal.valueOf(soc * Math.pow(10, cPowinBlockMaster.getSoC_SF())));
        cPowinBlockMaster.setBasicOpTriggerCommand(BasicOpTriggerCommandEnum.Trigger);
    }

    public void setBasicOpPriorityPower(int power, int soc) throws ModbusException {
        cPowinBlockMaster.setBasicOpPriorityCommand(BasicOpPriorityCommandEnum.Power);
		// cPowinBlockMaster.setBasicOpTargetPowerCommand(BigDecimal.valueOf(power *
		// Math.pow(10, cPowinBlockMaster.getW_SF())));
		cPowinBlockMaster.setBasicOpTargetPowerCommand(BigDecimal.valueOf(power));
        cPowinBlockMaster.setBasicOpTargetSOCCommand(BigDecimal.valueOf(soc * Math.pow(10, cPowinBlockMaster.getSoC_SF())));
        cPowinBlockMaster.setBasicOpTriggerCommand(BasicOpTriggerCommandEnum.Trigger);
    }

	public void setW_SF(int sf) throws ModbusException {
		cPowinBlockMaster.setW_SF(sf);
	}

	public int getW_SF() throws ModbusException {
		return cPowinBlockMaster.getW_SF();
	}

	public String getBasicOpTargetPower() throws ModbusException {
		return cPowinBlockMaster.getBasicOpTargetPower().toString();
	}

	public String getBasicOpTargetPowerCommand() throws ModbusException {
		return cPowinBlockMaster.getBasicOpTargetPowerCommand().toString();
	}

	public String getBasicOpTargetSOC() throws ModbusException {
		return cPowinBlockMaster.getBasicOpTargetSOC().toString();
	}

	public String getBasicOpTargetSOCCommand() throws ModbusException {
		return cPowinBlockMaster.getBasicOpTargetSOCCommand().toString();
	}

    public void getBasicOpPower() throws ModbusException {
        LOG.info("getBasicOpPriority= {}", cPowinBlockMaster.getBasicOpPriority().toString());// SOC
        LOG.info("getBasicOpPriorityValue= {}", cPowinBlockMaster.getBasicOpPriorityValue());// 1=SOC
        LOG.info("getBasicOpPriorityCommand= {}", cPowinBlockMaster.getBasicOpPriorityCommand().toString());// Power
        LOG.info("getBasicOpPriorityCommandValue= {}", cPowinBlockMaster.getBasicOpPriorityCommandValue());// 0
        LOG.info("getBasicOpTargetPower= {}", cPowinBlockMaster.getBasicOpTargetPower().toString());// 0
        LOG.info("getBasicOpTargetPowerCommand= {}", cPowinBlockMaster.getBasicOpTargetPowerCommand().toString());// 0
        LOG.info("getBasicOpTargetSOC= {}", cPowinBlockMaster.getBasicOpTargetSOC().toString());// 50
        LOG.info("getBasicOpTargetSOCCommand= {}", cPowinBlockMaster.getBasicOpTargetSOCCommand().toString());// 0
        LOG.info("getBasicOpTriggerCommand= {}", cPowinBlockMaster.getBasicOpTriggerCommand().toString());// Triggered
        LOG.info("getBasicOpTriggerCommandValue= {}", cPowinBlockMaster.getBasicOpTriggerCommandValue());// 2=Triggered
        LOG.info("getSoC_SF= {}", cPowinBlockMaster.getSoC_SF());// 0
        LOG.info("getW_SF= {}", cPowinBlockMaster.getW_SF());// 3

    }

    public int getAppIndex(String appName)  {
        String appNameFromSystem = "";
        appName = appName.replace(" ", "");// Strip spaces
        int numApps=-1;
		try {
			numApps = this.getNumApps();
		} catch (ModbusException e) {
			LOG.error("Error getting apps from PowinBlockMaster",  e);
		}
        for (int i = 0; i < numApps; i++) {
            appNameFromSystem = this.getAppType(i).toString().toUpperCase();
            if (appNameFromSystem.contains(appName.toUpperCase())) {
                return i;
            }
        }
        return -1;
    }

    public BigDecimal getBlockAmpHourRating() throws ModbusException {
        return cPowinBlockMaster.getBlockAmpHourRating();
    }

    public BigDecimal getBlockWattHourRating() throws ModbusException {
        return cPowinBlockMaster.getBlockWattHourRating();
    }

    public BigDecimal getBlockMaxChargeRate() throws ModbusException {
        return cPowinBlockMaster.getBlockMaxChargeRate();
    }

    public BigDecimal getBlockMaxDischargeRate() throws ModbusException {
        return cPowinBlockMaster.getBlockMaxDischargeRate();
    }

    public BigDecimal getBlockOnlineSOC() throws ModbusException {
        return cPowinBlockMaster.getBlockOnlineSOC();
    }

    public BigDecimal getBlockOfflineSOC() throws ModbusException {
        return cPowinBlockMaster.getBlockOfflineSOC();
    }

    public BigDecimal getBlockNearlineSOC() throws ModbusException {
        return cPowinBlockMaster.getBlockNearlineSOC();
    }

    public BigDecimal getBlockTotalSOC() throws ModbusException {
        return cPowinBlockMaster.getBlockTotalSOC();
    }

    public BigDecimal getBlockOnlineStoredWh() throws ModbusException {
        return cPowinBlockMaster.getBlockOnlineStoredWh();
    }

    public BigDecimal getBlockOfflineStoredWh() throws ModbusException {
        return cPowinBlockMaster.getBlockOfflineStoredWh();
    }

    public BigDecimal getBlockNearlineStoredWh() throws ModbusException {
        return cPowinBlockMaster.getBlockNearlineStoredWh();
    }

    public BigDecimal getBlockTotalStoredWh() throws ModbusException {
        return cPowinBlockMaster.getBlockTotalStoredWh();
    }

    public BigDecimal getBlockOnlineCapacityWh() throws ModbusException {
        return cPowinBlockMaster.getBlockOnlineCapacityWh();
    }

    public BigDecimal getBlockOfflineCapacityWh() throws ModbusException {
        return cPowinBlockMaster.getBlockOfflineCapacityWh();
    }

    public BigDecimal getBlockNearlineCapacityWh() throws ModbusException {
        return cPowinBlockMaster.getBlockNearlineCapacityWh();
    }

    public BigDecimal getBlockTotalCapacityWh() throws ModbusException {
        return cPowinBlockMaster.getBlockTotalCapacityWh();
    }

    public BigDecimal getBlockW() throws ModbusException {
        return cPowinBlockMaster.getBlockW();
    }

    public BigDecimal getBlockVA() throws ModbusException {
        return cPowinBlockMaster.getBlockVA();
    }

    public BigDecimal getBlockVAr() throws ModbusException {
        return cPowinBlockMaster.getBlockVAr();
    }

    public int getAvailableDischargePowerCapacity() throws ModbusException {
        return cPowinBlockMaster.getAvailableDischargePowerCapacity().intValue();
    }

    public final static void main(String[] args) {

        try {
            ModbusPowinBlock mPowinBlockTest = new ModbusPowinBlock(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
            // mPowinBlockTest.setBasicOpPriorityPower(-55000, 55);
//            mPowinBlockTest.setBasicOpPriorityPower(-99, 66);
//            mPowinBlockTest = new ModbusPowinBlock(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
//            mPowinBlockTest.setBasicOpPriorityPower(99, 77);
//
//            mPowinBlockTest = new ModbusPowinBlock(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
//            mPowinBlockTest.setBasicOpPrioritySOC(88, -88);
//
//            mPowinBlockTest = new ModbusPowinBlock(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
//            mPowinBlockTest.setBasicOpPrioritySOC(99, 88);
//            mPowinBlockTest.setBasicOpPriorityPower(77, 23);
            // mPowinBlockTest.init();
            // LOG.info("getBasicOpTargetSOC= {}", mPowinBlockTest.getBasicOpTargetSOC());
//            mPowinBlockTest.setAppStatus("PowerCommand",1);
//             mPowinBlockTest.getAppStatus("PowerCommand");
            // LOG.info("getNumApps= {}", mPowinBlockTest.getNumApps());
             for (int i = 0; i < 6; i++) {
             LOG.info("getAppType= {}",
             mPowinBlockTest.getAppType(i));//SunspecPowerCommand
             LOG.info("getAppTgetApplicationTypeValueype= {}",
             mPowinBlockTest.getApplicationTypeValue(i));
             mPowinBlockTest.getAppStatus(mPowinBlockTest.getAppType(i).toString());
             }
            // LOG.info("getAppIndex= {}", mPowinBlockTest.getAppIndex("Sunspec"));
            // mPowinBlockTest.setAppStatus("Sunspec", 0);
            // mPowinBlockTest.enableApp("SunspecPowerCommand");
            // mPowinBlockTest.enableApp("SunspecPowerCommand");
            // mPowinBlockTest.getBasicOpPower();
            // mPowinBlockTest.setBasicOpPower();
            // mPowinBlockTest.setBasicOpPrioritySOC(42,-55);
            // mPowinBlockTest.setBasicOpPriorityPower(55, 30);
            // mPowinBlockTest.getBasicOpPower();
            // LOG.info("configureScheduler= {}", mPowinBlockTest.configureScheduler());

            // mPowinBlockTest.runSimpeBasicCommandPrioritySOC(BigDecimal.valueOf(-20000));
            // mPowinBlockTest.runSimpeBasicCommandPriorityPower(BigDecimal.valueOf(60000));
            // mPowinBlockTest.runSimpeBasicCommandOff();

        }
        catch (

        Exception e) {
            LOG.error("Exception in go.", e);
        }
    }
}
