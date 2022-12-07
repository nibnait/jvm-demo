package cc.tianbin.demo.jvm.utils;

import cc.tianbin.demo.jvm.rtda.frame.Slot;

public class NumberUtil {

    private NumberUtil() {
        throw new AssertionError("工具类不允许被实例化");
    }

    public static Slot setInt(int val) {
        boolean flag = true;
        String s = appendZero(Integer.toBinaryString(val), 32);
        if (val < 0) {
            flag = false;
            s = appendZero(translate(Integer.toBinaryString(val)), 32);
        }
        return Slot.num(s, flag);
    }

    public static int getInt(Slot slot) {
        if (slot.isFlag()) {
            return slot.getNumDecimalInt();
        } else {
            return slot.getNumDecimalInt() * -1 - 1;
        }
    }

    public static Slot[] setLong(long val) {
        boolean flag = true;
        String s = NumberUtil.appendZero(Long.toBinaryString(val), 64);
        if (val < 0) {
            flag = false;
            s = NumberUtil.appendZero(NumberUtil.translate(Long.toBinaryString(val)), 64);
        }
        String high = s.substring(0, 32);
        String low = s.substring(32);

        Slot[] slots = new Slot[2];
        slots[0] = Slot.num(high, flag);
        slots[1] = Slot.num(low, flag);
        return slots;
    }

    public static long getLong(Slot[] slots) {
        Slot high = slots[0];
        Slot low = slots[1];
        if (high.isFlag()) {
            return Long.parseLong(high.getNumBinaryStr() + low.getNumBinaryStr(), 2);
        } else {
            return Long.parseLong(high.getNumBinaryStr() + low.getNumBinaryStr(), 2) * -1 - 1;
        }
    }

    public static String appendZero(String s, int length) {
        if (s.length() >= length) {
            return s;
        }
        StringBuilder builder = new StringBuilder(s);
        for (int i = 0; i < length - s.length(); i++) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }

    public static String insertZero(String s, int length) {
        if (s.length() >= length) {
            return s;
        }
        StringBuilder builder = new StringBuilder(s);
        for (int i = 0; i < length - s.length(); i++) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }

    private static String translate(String s) {
        StringBuilder builder = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '0') {
                builder.append("1");
            } else {
                builder.append("0");
            }
        }
        return builder.toString();
    }

    public static int byte2Int(byte[] codes) {
        String s1 = byte2HexString(codes);
        return Integer.valueOf(s1, 16);
    }

    public static String byte2HexString(byte[] codes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : codes) {
            int value = b & 0xFF;
            String strHex = Integer.toHexString(value);
            if (strHex.length() < 2) {
                strHex = "0" + strHex;
            }
            sb.append(strHex);
        }
        return sb.toString();
    }

    public static String int2HexString(int opCode) {
        String strHex = Integer.toHexString(opCode);
        if (strHex.length() < 2) {
            strHex = "0" + strHex;
        }
        return "0x" + strHex;
    }
}
