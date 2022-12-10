package cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.base.ConstantInfoRefBase;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import cc.tianbin.demo.jvm.common.FieldDescriptor;

/**
 * Created by nibnait on 2022/11/30
 */
public class ConstantNameAndTypeInfo extends ConstantInfoRefBase implements ConstantInfo {

    /**
     * u1 tag;
     * u2 name_index;
     * u2 descriptor_index;
     */
    // 字段名 或 方法名，的索引
    private int nameIndex;
    // 字段或方法的 描述符，的索引
    private int descriptorIndex;

    public ConstantNameAndTypeInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    /**
     * 字段名 或 方法名
     */
    public String name() {
        return constantPool.getUTF8(nameIndex);
    }

    /**
     * @see FieldDescriptor
     */
    public String descriptor() {
        return constantPool.getUTF8(descriptorIndex);
    }

    @Override
    public void readInfo(ClassReader reader) {
        super.readInfo(reader);
        this.nameIndex = reader.readU2ToInt();
        this.descriptorIndex = reader.readU2ToInt();
    }

    @Override
    public String printValue() {
        return String.format("#%d;#%-18d// %s&%s",
                this.nameIndex, this.descriptorIndex,
                this.name(), this.descriptor());
    }
}
