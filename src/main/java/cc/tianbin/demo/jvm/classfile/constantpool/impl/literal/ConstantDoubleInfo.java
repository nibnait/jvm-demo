package cc.tianbin.demo.jvm.classfile.constantpool.impl.literal;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfoLiteralBase;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantDoubleInfo extends ConstantInfoLiteralBase implements ConstantInfo {

    /**
     * u1 tag;
     * u4 high_bytes;
     * u4 low_bytes;
     */
    private double value;

    @Override
    public String value() {
        return value + "";
    }

    @Override
    public void readInfo(ClassReader reader) {
        // 先请爸爸把 tag 读了
        super.readInfo(reader);
        // 再读自己的 value
        this.value = reader.next2U4Double();
    }

    @Override
    public String toString() {
        return "Double: " + value;
    }
}
