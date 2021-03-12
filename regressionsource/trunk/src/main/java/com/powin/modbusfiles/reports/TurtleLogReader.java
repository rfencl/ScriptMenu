package com.powin.modbusfiles.reports;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.powin.modbusfiles.utilities.CommonHelper;

public class TurtleLogReader {
	private String cLogFilePath;
	private RandomAccessFile cLogFile;
	private long cLogPointer;
	private boolean cFileOpen;

	public TurtleLogReader(String filePath) {
		setLogFilePath(filePath);
	}

	public String getLogFilePath() {
		return cLogFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		cLogFilePath = logFilePath;
	}

	public RandomAccessFile getLogFile() {
		return cLogFile;
	}

	public void setLogFile(RandomAccessFile logFile) {
		cLogFile = logFile;
	}

	public long getLogPointer() {
		return cLogPointer;
	}

	public void setLogPointer(long logPointer) {
		cLogPointer = logPointer;
	}

	public boolean isFileOpen() {
		return cFileOpen;
	}

	public void setFileOpen(boolean fileOpen) {
		cFileOpen = fileOpen;
	}

	public void openFile4Read() {
		try {
			setLogFile(new RandomAccessFile(getLogFilePath(), "r"));
			setFileOpen(true);

		} catch (FileNotFoundException e) {
			setFileOpen(false);
		}
	}

	public void refreshSeekPointer() {
		if (getLogFile() != null) {
			try {
				long mFileLen = getLogFile().length();
				if (mFileLen < getLogFile().getFilePointer()) {
					setLogPointer(0);
				} else {
					setLogPointer(mFileLen);
				}
			} catch (IOException e) {
				setLogPointer(0);
			}
		}
	}

	public boolean verifyOpenContactorsTestResult() throws InterruptedException {
		try {
			String str;
			
			while ((str = getLogFile().readLine()) != null) {
//				if (str.contains("ErrorFlag : DCUV") && str.contains("OpSt : Following/Fault")
//						|| str.contains("App BSF0001 Tripped NR[NotReady]")) {
//					return true;
//				}
				System.out.println(str);
				if (str.contains("switched to not ready") ) {
					return true;
				}
				
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return false;
	}

	public boolean verifyCloseContactorsAfterOpeningWhileIdle() {
		try {
			String str;
			while ((str = getLogFile().readLine()) != null) {
				if (str.contains("Operating") && str.contains("Following")) {
					return true;
				}
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return false;
	}
}
