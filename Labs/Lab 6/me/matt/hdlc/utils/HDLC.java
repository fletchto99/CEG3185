package me.matt.hdlc.utils;

import java.util.BitSet;

/**
 * Created by mattlanglois on 2017-03-15.
 */
public class HDLC {
	public static final String FLAG = "01111110";
        public static final String FCS = "0000000000000000";
        public static final String CONTROL_SNRM = "11001001";
        public static final String CONTROL_UA = "11001110";
        public static final String CONTROL_INFO = "00010000";
        public static final String CONTROL_RR = "10001000";
	
	private boolean checkFlags(String message) {
		boolean result = false;
		if (message.substring(0, 8).equals(FLAG)
				&& message.substring(message.length() - 8, message.length()).equals(FLAG))
			result = true;
		return result;
	}
}
