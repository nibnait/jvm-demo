package cc.tianbin.demo.jvm.classfile;

import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import org.apache.ibatis.javassist.bytecode.ConstantAttribute;

/**
 * 字段、方法表，都用 这个接口描述
 * Created by nibnait on 2022/11/29
 */
public class MemberInfo {

    private ConstantPool constantPool;

    /**
     * u2             access_flags;
     * u2             name_index;
     * u2             descriptor_index;
     * u2             attributes_count;
     * attribute_info attributes[attributes_count];
     */
    private AccessFlag accessFlags;
    // 常量池索引：字段名/方法名
    private int nameIndex;
    // 常量池索引：字段描述符/方法描述符
    private int descriptorIndex;
    // 属性表
    private AttributeInfo[] attributes;

    public MemberInfo(ConstantPool constantPool, ClassReader reader) {
        this.constantPool = constantPool;
        this.accessFlags = ClassFile.readAccessFlag(reader);
        this.nameIndex = reader.readU2ToInt();
        this.descriptorIndex = reader.readU2ToInt();
        attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    public static MemberInfo[] readMembers(ConstantPool constantPool, ClassReader reader) {
        int fieldCount = reader.readU2ToInt();
        MemberInfo[] fields = new MemberInfo[fieldCount];

        for (int i = 0; i < fieldCount; i++) {
            fields[i] = new MemberInfo(constantPool, reader);
        }

        return fields;
    }

    public AccessFlag getAccessFlags() {
        return this.accessFlags;
    }

    public String getName() {
        return this.constantPool.getUTF8(this.nameIndex);
    }

    public String getDescriptor() {
        return this.constantPool.getUTF8(this.descriptorIndex);
    }

    public AttributeInfo[] getAttributes() {
        return this.attributes;
    }

    public CodeAttribute getCodeAttribute() {
        for (AttributeInfo attribute : this.getAttributes()) {
            if (attribute instanceof CodeAttribute) {
                return (CodeAttribute) attribute;
            }
        }
        return null;
    }

    public ConstantAttribute constantAttribute() {
        for (AttributeInfo attribute : this.getAttributes()) {
            if (attribute instanceof ConstantAttribute) {
                return (ConstantAttribute) attribute;
            }
        }
        return null;
    }

}
