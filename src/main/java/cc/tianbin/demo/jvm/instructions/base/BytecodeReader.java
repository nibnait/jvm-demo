package cc.tianbin.demo.jvm.instructions.base;

import cc.tianbin.demo.jvm.utils.NumberUtil;

import java.util.Arrays;

/**
 * Created by nibnait on 2022/12/05
 */
public class BytecodeReader {

    // 字节码
    private byte[] bytecode;
    // 记录读取到了哪个字节
    private int pc;

    public void reset(byte[] codes, int pc) {
        this.bytecode = codes;
        this.pc = pc;
    }

    public int pc() {
        return this.pc;
    }

    public int readU1ToInt() {
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

    public byte[] readBytes(int len) {
        if (pc + len >= bytecode.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        byte[] copy = Arrays.copyOfRange(bytecode, pc, pc + len);
        pc += len;
        return copy;
    }

    /**
     * lookupswitch, tableswitcch
     * 读一个 int 数组
     */
    public int[] readInts(int n) {
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = this.readU4ToInt();
        }
        return ints;
    }

    /**
     * lookupswitch, tableswitcch
     * 这两个指令操作码的后面有 0~3字节的 padding，以保证 defaultOffset 在字节码中的地址是4的倍数
     */
    public void skipPadding() {
        while (this.pc % 4 != 0) {
            this.readBytes(1);
        }
    }

}
