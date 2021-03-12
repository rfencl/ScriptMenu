package com.powin.modbusfiles.cycling;

import java.math.BigDecimal;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTimeUtils;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.blocks.PowinBlockCommon.BasicOpStatusEnum;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import static com.powin.modbusfiles.modbus.ModbusPowinBlock.getModbusPowinBlock;

public class SimpleBasicOpCycler {
    private final static Logger LOG = LogManager.getLogger(SimpleBasicOpCycler.class.getName());
    protected final static long ONE_MINUTE = 60 * 1000;
    protected final static long FIVE_SECONDS = 5 * 1000;
    protected final static long FIVE_MINUTES = 5 * ONE_MINUTE;

    private String cModbusHostName;
    private int cModbusPort;
    private int cModbusUnitId;
    private boolean cEnableModbusLogging;

    public String getModbusHostName() {
        return cModbusHostName;
    }

    public void setModbusHostName(String modbusHostName) {
        cModbusHostName = modbusHostName;
    }

    public int getModbusPort() {
        return cModbusPort;
    }

    public void setModbusPort(int modbusPort) {
        cModbusPort = modbusPort;
    }

    public int getModbusUnitId() {
        return cModbusUnitId;
    }

    public void setModbusUnitId(int modbusUnitId) {
        cModbusUnitId = modbusUnitId;
    }

    public boolean isEnableModbusLogging() {
        return cEnableModbusLogging;
    }

    public void setEnableModbusLogging(boolean enableModbusLogging) {
        cEnableModbusLogging = enableModbusLogging;
    }

    protected void stopApps() {
        boolean mSucceededInStoppingApps = false;
        while (!mSucceededInStoppingApps) {
            try {
                getModbusPowinBlock().disableBasicOp();
                getModbusPowinBlock().disableSunspec();
                mSucceededInStoppingApps = true;
            }
            catch (Exception e) {
                LOG.error("Failed to stop apps. Will retry in {} milliseconds.", FIVE_SECONDS, e);
                try {
                    Thread.sleep(FIVE_SECONDS);
                }
                catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    protected void movePower(int targetP, long minutes, long logInterval) {
        LOG.info("Start moving {}kW using SimpleBasicOp.", targetP);

        boolean mSucceededInStarting = false;
      
        while (!mSucceededInStarting) {
            try {
                getModbusPowinBlock().runSimpleBasicOpCommand(BigDecimal.valueOf(targetP).movePointRight(3));
                mSucceededInStarting = true;
                LOG.info("Suceeded in starting moving power @ {}kW.", targetP);
            }
            catch (ModbusException e) {
                LOG.error("Failed to start moving power @ {}kW. Will retry in {} ms.", targetP, FIVE_SECONDS, e);
                try {
                    Thread.sleep(FIVE_SECONDS);
                }
                catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        long mNextWaitingLog = DateTimeUtils.currentTimeMillis() + logInterval;
        long mStopTime = DateTimeUtils.currentTimeMillis() + minutes;
        
        boolean mIsBasicOpComplete = false;
        while (!mIsBasicOpComplete) {
            long mNow = DateTimeUtils.currentTimeMillis();
            try {
                if (mNow >= mNextWaitingLog) {
                    LOG.info("Waiting. Power moving is {} kW with {}% online SOC of {}kWh.", getModbusPowinBlock().getBlockW().movePointLeft(3),
                            getModbusPowinBlock().getBlockOnlineSOC().intValue(), getModbusPowinBlock().getBlockOnlineCapacityWh().movePointLeft(3));
                    mNextWaitingLog = mNow + logInterval;
                }
                Thread.sleep(FIVE_SECONDS);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            catch (ModbusException e) {
                LOG.error("Failed to get data for logging wait event. Will retry immediately.", e);
                mNextWaitingLog = DateTimeUtils.currentTimeMillis();
            }

            try {
                mIsBasicOpComplete = (getModbusPowinBlock().getStatusEnum() == BasicOpStatusEnum.Done);
                if (minutes > 0) {
                	mIsBasicOpComplete |= mNow >= mStopTime;
                }
            }
            catch (ModbusException e) {
                LOG.error("Failed to check if BasicOp is done. will recheck immediately.", e);
                mIsBasicOpComplete = false;
            }
        }

        LOG.info("Done moving {}kW using SimpleBasicOp.", targetP);
    }

    public void run(int kw, boolean startWithCharge) {
        stopApps();

        if (startWithCharge) {
            movePower(-kw, 0, FIVE_MINUTES);
        }

        while (true) {
            movePower(kw, 0, FIVE_MINUTES);
            movePower(-kw, 0, FIVE_MINUTES);
        }
    }

    public  static void main(String[] args) {
        SimpleBasicOpCycler mSimpleBasicOpCycler = new SimpleBasicOpCycler();

        mSimpleBasicOpCycler.setModbusHostName("localhost");
        mSimpleBasicOpCycler.setModbusPort(4502);
        mSimpleBasicOpCycler.setModbusUnitId(255);
        mSimpleBasicOpCycler.setEnableModbusLogging(false);

        boolean mStartWithCharge = true;
        
        mSimpleBasicOpCycler.run(100, mStartWithCharge);
    }
}
