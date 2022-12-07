package cc.tianbin.demo.jvm.classfile;

import cc.tianbin.demo.jvm.utils.NumberUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * java虚拟机定义了u1、u2、u4三种数据类型来表示；1字节、2字节、4字节，无符号整数。
 * 在如下实现中，用增位方式表示无符号类型：
 * u1: 1字节，每个常量的 tag 位
 * u2:
 * u4: 4字节，用来存储 boolean, byte, short, char, int(3), flout(4) 类型
 * 2u4: 8个字节，用来存储 long(5) 或 double(6) 类型
 */
public class ClassReader {

    private byte[] data;
    private int pos;

    public ClassReader(byte[] data) {
        this.data = data;
        this.pos = 0;
    }


    public int readU1toInt() {
        return NumberUtil.byte2Int(readBytes(1));
    }

    public int readU2ToInt() {
        byte[] bytes = readBytes(2);
        return NumberUtil.byte2Int(bytes);
    }

    public int readU4ToInt() {
        byte[] bytes = readBytes(4);
        return NumberUtil.byte2Int(bytes);
    }

    public float readU4ToFloat() {
        byte[] bytes = readBytes(4);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public String readU2ToHexString() {
        byte[] bytes = readBytes(2);
        return NumberUtil.byte2HexString(bytes);
    }

    public String readU4ToHexString() {
        byte[] bytes = readBytes(4);
        return NumberUtil.byte2HexString(bytes);
    }

    public byte[] readBytes(int len) {
        if (pos + len >= data.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        byte[] copy = Arrays.copyOfRange(data, pos, pos + len);
        pos += len;
        return copy;
    }

    public long read2U4ToLong() {
        byte[] bytes = readBytes(8);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getLong();
    }

    public double read2U4Double() {
        byte[] bytes = readBytes(8);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getDouble();
    }

    public int[] readUint16s() {
        int count = readU2ToInt();
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = readU2ToInt();
        }
        return result;
    }

    public void back(int n) {
        this.pos -= n;
    }

}
