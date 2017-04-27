package com.vvt.std;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.io.FileNotFoundException;
import net.rim.device.api.util.DataBuffer;

public final class FileUtil {
	
	public static void append(String filename, String data) throws IOException {
		FileConnection fCon = null;
        OutputStream os = null;
		try {
	        fCon = (FileConnection)Connector.open(filename, Connector.READ_WRITE);
	        if (!fCon.exists()) {
	        	fCon.create();
	        }
	        os = fCon.openOutputStream(fCon.totalSize());
	        os.write(data.getBytes());
		} finally {
			IOUtil.close(os);
	        IOUtil.close(fCon);
		}
	}
	
	public static void replace(String filename, String data) throws IOException {
		byte[] newLine = "\n".getBytes();
		FileConnection fCon = null;
        OutputStream os = null;
		try {
	        fCon = (FileConnection)Connector.open(filename, Connector.READ_WRITE);
	        if (fCon.exists()) {
	        	fCon.delete();
	        }
	        fCon.create();
	        os = fCon.openOutputStream();
	        os.write(data.getBytes());
	        os.write(newLine);
		} finally {
			 IOUtil.close(os);
			 IOUtil.close(fCon);
		}
	}
	
	public static String read(String filename) throws IOException {
		int EOF = -1; // End of File
		String text = null;
		FileConnection fCon = null;
        InputStream is = null;
		try {
	        fCon = (FileConnection)Connector.open(filename, Connector.READ);
        	int tmpSize = 100;
        	byte[] tmp = new byte[tmpSize];
        	DataBuffer buffer = new DataBuffer();
	        is = fCon.openInputStream();
	        int status = is.read(tmp);
	        while(status != EOF) {
	        	buffer.write(tmp);
	        	tmp = new byte[tmpSize];
	        	status = is.read(tmp);
	        }
	        text = new String(buffer.getArray());
		} finally {
	        IOUtil.close(is);
	        IOUtil.close(fCon);
		}
		return text;
	}
	
	public static InputStream getInputStream(String absoluteFilename, int offset) throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException {
		FileConnection fCon = null;
		fCon = getFileConnection(absoluteFilename);
		if (fCon.fileSize() == -1) { 
			throw new FileNotFoundException("Can not find "+ absoluteFilename);
		}
		if (offset < 0 || offset >= fCon.fileSize()) { 
			throw new IllegalArgumentException(absoluteFilename+ ": offset is out of file range");
		}		
		InputStream inStream = fCon.openInputStream();
		inStream.skip(offset); 
		return inStream;
	}
	
	public static void writeToFile(String absoluteFilename, byte[] data) throws FileNotFoundException, IOException, SecurityException {
		// 1 create file
		OutputStream os = writeItem(absoluteFilename);		
		// 2 write to file
		os.write(data);
		os.close();
	}
	
	public static void writeOffsetToFile(String absoluteFilename, byte[] data, int offset, int len) throws FileNotFoundException, IOException, SecurityException {
		// 1 create file
		OutputStream os = writeItem(absoluteFilename);		
		// 2 write to file
		os.write(data, offset, len);
		os.close();
	}

	public static FileConnection getFileConnection(String absoluteFilename) throws SecurityException, FileNotFoundException, IOException {
		FileConnection fCon = null;
		fCon = (FileConnection) Connector.open(absoluteFilename,Connector.READ_WRITE);		
		return fCon;
	}
	
	public static OutputStream writeItem(String absoluteFilename) throws SecurityException, FileNotFoundException, IOException {
		FileConnection fconn   = null;
		OutputStream os = null;
		fconn = getFileConnection(absoluteFilename);
		if (fconn.exists()) {
		     fconn.delete();
		}	
		fconn.create();
		os = fconn.openOutputStream();	     
	    return os;
	}
}