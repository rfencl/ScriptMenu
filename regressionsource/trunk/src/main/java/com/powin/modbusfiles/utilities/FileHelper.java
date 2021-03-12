package com.powin.modbusfiles.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

public class FileHelper {
	private static final String RESOURCE_NAME = "default.properties";
	private final static Logger LOG = LogManager.getLogger(FileHelper.class.getName());
	private String csvFilePath = "";
	//private FileWriter mFileWriter;
	private Writer mFileWriter;
	private CSVPrinter mCsvWriter;

	public FileHelper(String path) throws IOException {
		if (path.contains("/")) {
			csvFilePath = path;
		} else {
			csvFilePath = getCsvPath(path);
		}
		this.init();
	}

	private void init() throws IOException {
		try {
			this.mFileWriter = 	new OutputStreamWriter(new FileOutputStream(csvFilePath, true), StandardCharsets.UTF_8);
			//new FileWriter(csvFilePath, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mCsvWriter = new CSVPrinter(this.mFileWriter, CSVFormat.EXCEL);
	}

	private String getCsvPath(String fileResourceName) throws IOException {
		return PowinProperty.fromPropertyFileKey(fileResourceName);
	}

	public String reportPath() {
		return csvFilePath;
	}

	private void closeFile() {
		try {
			mCsvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToCSV(String commaSeparatedString)  {
		try {
			this.init();
			String[] x = commaSeparatedString.split(",");
			ArrayList<String> a = new ArrayList<>(Arrays.asList(x));
			mCsvWriter.printRecord(a);
		} catch (IOException e) {
			LOG.error("Unable to write to File", e);
			throw new RuntimeException(e.getMessage());
		}
		closeFile();
	}

	public void writeToCSV(ArrayList<String> recordList) throws IOException {
		for (String record : recordList) {
			writeToCSV(record);
		}
	}

	public void writeToCSV(String prefix, List<String> recordList) throws IOException {
		for (String record : recordList) {
			writeToCSV(prefix + record);
		}
	}

	public void addHeader(String commaSeparatedHeader) throws IOException {
		writeToCSV(commaSeparatedHeader);
	}

	public void deleteFileContents() throws IOException {
		new FileWriter(csvFilePath, false).close();
	}

	public static void copyFile(String src, String dest) throws IOException {
	    Files.copy(new File(src).toPath(), new File(dest).toPath());
	}

	public static void copyFile(File src, File dest) throws IOException {
	    Files.copy(src.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
	}

	public static FileHelper createTimeStampedFile(String folder, String filename, String ext)  {
		try {
			return new FileHelper(folder + "/" + filename
					+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss").replace(" ", "_") + ext);
		} catch (IOException e) {
			LOG.error("Can't create file {}.", filename, e);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static List<String> readFileToList(final String filename)  {
		List<String> data= new ArrayList<>();
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			data = lines.collect(Collectors.toList());
		} catch (IOException e) {
			LOG.error("Can't read file {}", filename, e);
			throw new RuntimeException(e.getMessage());
		}
		return data;
	}
	
	public static String readFileAsString(String pathToFile) {
		String ret = "";
		try {
			ret = new String(Files.readAllBytes(Paths.get(pathToFile)));
		} catch (IOException e) {
			LOG.error("Unable to read file {}.", pathToFile);
		}
		return ret;
	}
	public static void main(String[] args) throws IOException {
		FileHelper mFileUtils = new FileHelper("/home/maheshb/bc_str.csv");
		// mFileUtils.writeToCSV("1,2,3,4");
		mFileUtils.deleteFileContents();
	}	

}