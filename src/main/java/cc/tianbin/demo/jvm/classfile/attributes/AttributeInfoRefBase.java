package cc.tianbin.demo.jvm.classfile.attributes;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public abstract class AttributeInfoRefBase implements AttributeInfo {

    protected final ConstantPool constantPool;

    public AttributeInfoRefBase(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    /**
     * 每个属性的标配：
     * u2 attribute_name_index;
     * u4 attribute_length;
     * 仅用于标记。没有任何数据。长度为 0
     */
    protected int attrNameIndex;
    protected int attrLength;

    @Override
    public String attrName() {
        return constantPool.getUTF8(attrNameIndex);
    }

    @Override
    public int attrLen() {
        return this.attrLength;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.attrNameIndex = reader.nextU2ToInt();
        this.attrLength = reader.nextU4ToInt();
    }
}
