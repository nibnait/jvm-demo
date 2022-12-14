package cc.tianbin.demo.jvm.classfile.constantpool.impl.literal;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.base.ConstantInfoLiteralBase;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantLongInfo extends ConstantInfoLiteralBase implements ConstantInfo {

    /**
     * u1 tag;
     * u4 high_bytes;
     * u4 low_bytes;
     */
    private long value;

    @Override
    public Long printValue() {
        return value;
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.value = reader.read2U4ToLong();
    }

}
