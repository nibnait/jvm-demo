package cc.tianbin.demo.jvm.classfile;

import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.CodeAttribute;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group1.ConstantValueAttribute;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;
import cc.tianbin.demo.jvm.common.AccessFlag;
import lombok.Getter;

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
    @Getter
    private AccessFlag accessFlag;
    // 常量池索引：字段名/方法名
    private int nameIndex;
    // 常量池索引：字段描述符/方法描述符
    private int descriptorIndex;
    // 属性表
    @Getter
    private AttributeInfo[] attributes;

    public MemberInfo(ConstantPool constantPool, ClassReader reader, AccessFlag.FlagType flagType) {
        this.constantPool = constantPool;
        this.accessFlag = ClassFile.readAccessFlag(reader, flagType);
        this.nameIndex = reader.readU2ToInt();
        this.descriptorIndex = reader.readU2ToInt();
        attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    public static MemberInfo[] readMembers(ConstantPool constantPool, ClassReader reader, AccessFlag.FlagType flagType) {
        int fieldCount = reader.readU2ToInt();
        MemberInfo[] fields = new MemberInfo[fieldCount];

        for (int i = 0; i < fieldCount; i++) {
            fields[i] = new MemberInfo(constantPool, reader, flagType);
        }

        return fields;
    }

    public String getName() {
        return this.constantPool.getUTF8(this.nameIndex);
    }

    public String getDescriptor() {
        return this.constantPool.getUTF8(this.descriptorIndex);
    }

    public CodeAttribute getCodeAttribute() {
        for (AttributeInfo attribute : this.getAttributes()) {
            if (attribute instanceof CodeAttribute) {
                return (CodeAttribute) attribute;
            }
        }
        return null;
    }

    public ConstantValueAttribute getConstantAttribute() {
        for (AttributeInfo attribute : this.getAttributes()) {
            if (attribute instanceof ConstantValueAttribute) {
                return (ConstantValueAttribute) attribute;
            }
        }
        return null;
    }

}
