package cc.tianbin.demo.jvm.classfile.constantpool.impl.invokdynamic;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.base.ConstantInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantMethodTypeInfo extends ConstantInfoRefBase implements ConstantInfo {

    /**
     * u1 tag;
     * u2 descriptor_index;
     */
    private int descriptorIndex;

    public ConstantMethodTypeInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    public int descriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.descriptorIndex = reader.readU2ToInt();
    }
}
