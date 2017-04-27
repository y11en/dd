package com.vvt.http.request;

import java.io.IOException;
import net.rim.device.api.util.DataBuffer;

/**
 * @author nattapon
 * @version 0.0.1
 * @created 08-Aug-2010 6:32 PM
 */

public class PostByteItem extends PostItem{

	private DataBuffer mBuffer;
	private long mSize;
	//private boolean isFirstTime = true;
	
	public void setBytes(byte[] data) {
		mBuffer = new DataBuffer();
		mBuffer.write(data);
		mBuffer.setPosition(0);
		mSize = data.length;
	}
	
	public byte getDataType() {
		return PostItemType.BYTE_ARRAY;
	}

	public long getTotalSize() throws SecurityException, IOException {
		return mSize;
	}

	public int read(byte[] buffer) throws IllegalArgumentException, SecurityException, IOException {
		/*if (isFirstTime) {
			mBuffer.reset();
			isFirstTime = false;
		}*/
		int len = mBuffer.read(buffer, 0, buffer.length);
		if (len == 0) {
			len = -1;
		}
		return len;
	}
	
	/*public void resetBuffer() {
		mBuffer.reset();
	}*/
}