package cc.tianbin.demo.jvm.classfile;

import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * 字段、方法表，都用 这个接口描述
 * Created by nibnait on 2022/11/29
 */
public class MemberInfo {

    private ConstantPool constantPool;

    private int accessFlags;
    // 常量池索引：字段名/方法名
    private int nameIndex;
    // 常量池索引：描述符
    private int descriptorIndex;
    // 属性表
    private AttributeInfo[] attributes;

    public MemberInfo(ConstantPool constantPool, ClassReader reader) {
        this.constantPool = constantPool;
        this.accessFlags = reader.nextU2ToInt();
        this.nameIndex = reader.nextU2ToInt();
        this.descriptorIndex = reader.nextU2ToInt();
        attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    public static MemberInfo[] readMembers(ConstantPool constantPool, ClassReader reader) {
        int fieldCount = reader.nextU2ToInt();
        MemberInfo[] fields = new MemberInfo[fieldCount];

        for (int i = 0; i < fieldCount; i++) {
            fields[i] = new MemberInfo(constantPool, reader);
        }

        return fields;
    }

    public int accessFlags() {
        return this.accessFlags;
    }

    public String name() {
        return this.constantPool.getUTF8(this.nameIndex);
    }

    public String descriptor() {
        return this.constantPool.getUTF8(this.descriptorIndex);
    }


}
