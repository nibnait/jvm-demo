package cc.tianbin.demo.jvm.classfile.constantpool.impl.literal;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfoLiteralBase;

/**
 * Created by nibnait on 2022/11/29
 */
public class ConstantIntegerInfo extends ConstantInfoLiteralBase implements ConstantInfo {

    /**
     * u1 tag;
     * u4 bytes;
     * -
     * int, boolean, byte, short, char
     */
    private int value;

    @Override
    public String value() {
        return value + "";
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.value = reader.nextU4ToInt();
    }

    @Override
    public String toString() {
        return "Integer: " + value;
    }
}
