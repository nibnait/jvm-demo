package cc.tianbin.demo.jvm.classfile;

import cc.tianbin.demo.jvm.util.Util;

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


    public int nextU1toInt() {
        return Util.byteToInt(new byte[]{data[pos++]});
    }

    public int nextU2ToInt() {
        return Util.byteToInt(new byte[]{data[pos++], data[pos++]});
    }

    public int nextU4ToInt() {
        return Util.byteToInt(new byte[]{data[pos++], data[pos++], data[pos++], data[pos++]});
    }

    public float nextU4ToFloat() {
        byte[] bytes = nextBytes(4);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public String nextU4ToHexString() {
        byte[] bytes = nextBytes(4);
        return Util.byteToHexString(bytes);
    }

    public byte[] nextBytes(int len) {
        if (pos + len >= data.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        byte[] copy = Arrays.copyOfRange(data, pos, pos + len);
        pos += len;
        return copy;
    }


    public long next2U4ToLong() {
        byte[] bytes = nextBytes(8);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getLong();
    }

    public double next2U4Double() {
        byte[] bytes = nextBytes(8);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getDouble();
    }

    public int[] nextUint16s() {
        int count = nextU2ToInt();
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = nextU2ToInt();
        }
        return result;
    }

    public void back(int n) {
        this.pos -= n;
    }

}
