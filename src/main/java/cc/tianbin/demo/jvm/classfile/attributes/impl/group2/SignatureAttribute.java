package cc.tianbin.demo.jvm.classfile.attributes.impl.group2;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public class SignatureAttribute extends AttributeInfoRefBase implements AttributeInfo {
    public SignatureAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 signature_index;
     */
    private int signatureIndex;

    public String signature() {
        return this.constantPool.getUTF8(this.signatureIndex);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.signatureIndex = reader.readU2ToInt();
    }

}
