package cc.tianbin.demo.jvm.classfile.attributes.impl;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/11/30
 */
public class UnparsedRefAttribute extends AttributeInfoRefBase implements AttributeInfo {

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u1 info[attribute_length];
     */
    private byte[] info;

    public byte[] info() {
        return this.info;
    }

    public UnparsedRefAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.info = reader.readBytes(this.attrLength);
    }

}
