package com.powin.stackcommander;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.stackcommander.Executor.Status;

/**
 * 
 */
public final class Commander {
	private final static Logger LOG = LogManager.getLogger(Commander.class.getName());
	private static List<Executor> cmdsToRun;

	/**
	 * Returns the list of Executor
	 *
	 * @return
	 */
	public static List<Executor> getCmdsToRun() {
		LOG.info("There are {} Commands in the script.", cmdsToRun.size());
		return cmdsToRun;
	}

	/**
	 * Says hello to the world.
	 *
	 * @param args The arguments of the program.
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static void main(final String[] args) throws IOException, ParseException {
		if (args.length == 0 || !(new File(args[0])).exists()) {
			LOG.error("File not found.");
		}
		List<String> data = FileHelper.readFileToList(args[0]);
		processLines(data );
	}

	/**
	 * Process the script, line by line.
	 *
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static List<Executor> processLines(final List<String> data) throws IOException, ParseException {
		
		final StackScriptParser ssp = new StackScriptParser();
		cmdsToRun = ssp.parse(data);
		reportParseStatus(data, cmdsToRun);
		return getCmdsToRun();
	}



	/**
	 * Log status of the parse.
	 *
	 * @param data
	 * @param cmdsToRun
	 * @throws ParseException 
	 */
	private static void reportParseStatus(final List<String> data, final List<Executor> cmdsToRun) throws ParseException {
		int longestLine = 0;
		for (int i = 0; i < data.size(); ++i) {
			longestLine = Math.max(longestLine, data.get(i).length());
		}

		final String tabs = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
		for (int i = 0; i < data.size(); ++i) {
			Status status = cmdsToRun.get(i).getStatus();
			LOG.info(String.format("%s parsed status = %s%s", data.get(i),
					tabs.substring(0, (longestLine - data.get(i).length()) / 4), status));
			if (status == Executor.Status.NOT_READY) {
				throw new ParseException(String.format("Parse Error with %s", data.get(i)), 1);
			}
			
		}

	}

	private Commander() {
	}

}
