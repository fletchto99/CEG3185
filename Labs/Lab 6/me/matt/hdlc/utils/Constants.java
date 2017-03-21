package me.matt.hdlc.utils;

import java.util.BitSet;

public class Constants {

    public static final int DEFAULT_PORT = 8085;
    public static final BitSet FLAG = "01111110";
	public static final BitSet FCS = "0000000000000000";
	public static final BitSet CONTROL_SNRM = "11001001";
	public static final BitSet CONTROL_UA = "11001110";
	public static final BitSet CONTROL_INFO = "00010000";
	public static final BitSet CONTROL_ACK = "10001000";

}
