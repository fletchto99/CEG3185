package me.matt.hdlc.utils;

import java.util.BitSet;

/**
 * Created by mattlanglois on 2017-03-15.
 */
public class HDLC {
	public static final BitSet FLAG = createFromString("01111110");
    public static final BitSet FCS = createFromString("0000000000000000");
    public static final BitSet CONTROL_SNRM = createFromString("11001001");
    public static final BitSet CONTROL_UA = createFromString("11001110");
    public static final BitSet CONTROL_INFO = createFromString("00010000");
    public static final BitSet CONTROL_RR = createFromString("10001000");


    private static BitSet createFromString(final String data) {
        BitSet bitset = new BitSet(data.length());
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '1') {
                bitset.set(i);
            }
        }
        return bitset;
    }
}
