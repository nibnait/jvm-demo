package cc.tianbin.demo.jvm.classfile.attributes.impl.group2;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.attributes.AttributeInfo;
import cc.tianbin.demo.jvm.classfile.attributes.base.AttributeInfoRefBase;
import cc.tianbin.demo.jvm.classfile.attributes.impl.group3.DeprecatedAttribute;
import cc.tianbin.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * Created by nibnait on 2022/12/01
 */
public class SyntheticAttribute extends AttributeInfoRefBase implements AttributeInfo {

    /**
     * @see DeprecatedAttribute
     * 和 Deprecated 一样。最简单的两种属性，仅起标记作用，不包含任何数据。
     * ClassFile、field_info和method_info都可以用
     */
    public SyntheticAttribute(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public int attrLen() {
        return 0;
    }

    /**
     * u2 attribute_name_index;
     * u4 attribute_length;
     * 仅用于标记。没有任何数据。长度为 0
     */
    @Override
    public void readInfo(ClassReader reader) {
        this.attrNameIndex = reader.readU2ToInt();
        this.attrLength = reader.readU4ToInt();
    }}
