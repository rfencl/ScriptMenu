package com.powin.modbusfiles.apps;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.google.protobuf.ByteString;
import com.powin.goblin.commands.GoblinCommandDepot;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.PowinProperty;
import com.powin.redis.JedisPoolWrapper;
import com.powin.tongue.fourba.command.Command;
import com.powin.tongue.fourba.command.CommandPayload;
import com.powin.tongue.fourba.command.Endpoint;
import com.powin.tongue.fourba.command.EndpointType;
import com.powin.tongue.fourba.command.SetEMSApplicationConfiguration;

import redis.clients.jedis.JedisPool;

public class PowerCommandApp {
	private final static Logger LOG = LogManager.getLogger(PowerCommandApp.class.getName());
	private String cStationCode;
	private int cBlockIndex;
	private static String cloudHost;

	
	public PowerCommandApp() throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException{
		String stationCode = SystemInfo.getStationCode();
		int blockIndex = SystemInfo.getBlockIndex() ;
		cStationCode = stationCode;
		cBlockIndex = blockIndex;
		cloudHost= PowinProperty.CLOUDHOST.toString();
	}
	
	public PowerCommandApp(String stationCode, int blockIndex)throws IOException {
		cStationCode = stationCode;
		cBlockIndex = blockIndex;
	}
    
	public GoblinCommandDepot getGoblinCommandDepot() {  	  	  	
    	GenericObjectPoolConfig cGenericObjectPoolConfig = new GenericObjectPoolConfig();
    	cGenericObjectPoolConfig.setMinEvictableIdleTimeMillis(60000L);
    	cGenericObjectPoolConfig.setTestWhileIdle(true);
    	cGenericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(30000L);
    	cGenericObjectPoolConfig.setNumTestsPerEvictionRun(1);
		JedisPool cJedisPool = new JedisPool(cGenericObjectPoolConfig, cloudHost, 6379, 10000, false);
    	JedisPoolWrapper cJedisPoolWrapper = new JedisPoolWrapper();
    	cJedisPoolWrapper.setJedisPool(cJedisPool);
    	GoblinCommandDepot cGoblinCommandDepot= new GoblinCommandDepot();
    	cGoblinCommandDepot.setRedisEnabled(true);
    	cGoblinCommandDepot.setRedisPool(cJedisPoolWrapper);
    	cGoblinCommandDepot.setRedisKeyPrefix("gcd2");
    	cGoblinCommandDepot.init();
    	return cGoblinCommandDepot;
    }
    
	public void sendCommandToPowerCommandApp(int realPowerKw,int reactivePowerKw,boolean mEnabled){
		System.out.println("sendCommandToPowerCommandApp");
		StringBuilder mCommandText = new StringBuilder("{");
        mCommandText.append("\"realPowerkW\" :").append((int) realPowerKw).append(", ");
        mCommandText.append("\"reactivePowerkVAr\" :").append((int) reactivePowerKw).append(", ");
        mCommandText.append("\"gridMode\" :").append("\"GRID_FOLLOWING\"").append(", ");
        mCommandText.append("\"enabled\" :").append(mEnabled).append(", ");
        mCommandText.append("\"appConfigName\" :").append("\"instant\"").append(", ");
        mCommandText.append("\"appConfigVersion\" :").append(0);
        mCommandText.append("} ");

        ByteString mByteString = ByteString.copyFrom(mCommandText.toString(), Charset.defaultCharset());

        SetEMSApplicationConfiguration mSetEMSApplicationConfiguration = SetEMSApplicationConfiguration.newBuilder()
        		.setApplicationConfigurationName("instant")
                .setApplicationConfigurationVersionid(0)
                .setApplicationTypeCode("PC00001")
                .setApplicationPriority(4)
                .setEnabled(mEnabled)
                .setRawApplicationConfiguration(mByteString)
                .build();

        Command mCommand = Command.newBuilder()
        		.setCommandId(UUID.randomUUID().toString())
                .setCommandSource(Endpoint.newBuilder().setEndpointType(EndpointType.GOBLIN))
                .setCommandTarget(Endpoint.newBuilder().setEndpointType(EndpointType.BLOCK)
                .setStationCode(cStationCode)
                .setBlockIndex(cBlockIndex))
                .setCommandPayload(CommandPayload.newBuilder().setSetEMSApplicationConfiguration(mSetEMSApplicationConfiguration))
                .build();

       getGoblinCommandDepot().addCommand("admin", mCommand);
	}
	
	public void disablePowerCommandApp() {
		sendCommandToPowerCommandApp(0,0,false);
	}
	
	public void setPower(int realPowerKw,int reactivePowerKw) {
		sendCommandToPowerCommandApp(realPowerKw,reactivePowerKw,true);
	}
	
	public void enablePowerCommandAppWithZeroPower() {
		sendCommandToPowerCommandApp(0,0,true);
	}

	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException  {	
		PowerCommandApp pcApp = new PowerCommandApp();
		pcApp.enablePowerCommandAppWithZeroPower();
//		pcApp.setPower(-18,0);
	}
}