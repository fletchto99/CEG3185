package me.matt.hdlc.utils;

/**
 * Created by mattlanglois on 2017-03-15.
 */
public class HDLC {
  public static final BitSet FLAG = "01111110";
	public static final BitSet FCS = "0000000000000000";
	public static final BitSet CONTROL_SNRM = "11001001";
	public static final BitSet CONTROL_UA = "11001110";
	public static final BitSet CONTROL_INFO = "00010000";
	public static final BitSet CONTROL_RR = "10001000";

}
