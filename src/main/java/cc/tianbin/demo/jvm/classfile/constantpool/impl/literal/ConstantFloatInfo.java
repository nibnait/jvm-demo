package cc.tianbin.demo.jvm.classfile.constantpool.impl.literal;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.base.ConstantInfoLiteralBase;

/**
 * Created by nibnait on 2022/11/29
 */
public class ConstantFloatInfo extends ConstantInfoLiteralBase implements ConstantInfo {

    /**
     * u1 tag;
     * u4 bytes;
     */
    private float value;

    @Override
    public Float printValue() {
        return value;
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.value = reader.readU4ToFloat();
    }

}
