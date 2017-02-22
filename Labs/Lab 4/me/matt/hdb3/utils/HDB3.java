package me.matt.hdb3.utils;

public class HDB3 {

    public static String encode(String message) {
        String[] toEncode = message.split("");

        boolean negativePolarity = true;
        boolean even = false;
        String converted = "";

        //1100000000110000010
        //+-000-+00+-+-00-0+0
        for(int i = 0; i < toEncode.length; i++) {
            if (toEncode[i].equals("1")) {
                negativePolarity = !negativePolarity;
                even = !even;
                converted += negativePolarity ? "-" : "+";
            } else if(toEncode[i].equals("0")) {
                if (i+3 >= toEncode.length) {
                    converted += "0";
                } else {
                    if (toEncode[i+1].equals("0") && toEncode[i+2].equals("0") && toEncode[i+3].equals("0")) {
                        i += 3;
                        if (negativePolarity) {
                            if (even) {
                                converted += "+00+";
                                negativePolarity = false;
                            } else {
                                converted += "000-";
                                negativePolarity = true;
                            }
                        } else {
                            if (even) {
                                converted += "-00-";
                                negativePolarity = true;
                            } else {
                                converted += "000+";
                                negativePolarity = false;
                            }
                        }
                        even = true;
                    } else {
                        converted += "0";
                    }
                }
            }
        }
        return converted;
    }

    public static String decode(String message) {
        //+-000-+00+-+-00-0+0
        //1100000001110000010
        return message.replace("000-", "2222")
                .replace("000+", "2222")
                .replace("-00-", "2222")
                .replace("+00+", "2222")
                .replace("2", "0")
                .replaceAll("[^0]", "1");
    }
}
