package cc.tianbin.demo.jvm.classfile.constantpool;

import cc.tianbin.demo.jvm.classfile.ClassReader;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.invokdynamic.ConstantInvokeDynamicInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.invokdynamic.ConstantMethodHandleInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.invokdynamic.ConstantMethodTypeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.literal.*;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantFieldRefInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantInterfaceMethodRefInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.memberref.ConstantMethodRefInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref.ConstantClassInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref.ConstantNameAndTypeInfo;
import cc.tianbin.demo.jvm.classfile.constantpool.impl.symbolicref.ConstantStringInfo;
import cc.tianbin.demo.jvm.common.ConstantTag;

/**
 * Created by nibnait on 2022/11/29
 */
public interface ConstantInfo {

    /**
     * u1 tag
     * u1 info[]
     */
    // 每一组常量信息的第1个字节, 对应的常量类型
    ConstantTag tag();
    // 常量值
    // 对于[字面量](literal)、ConstantStringInfo 就是 对应的值
    // 对于[引用类型](xxref)、[invokeDynamic指令] 直接返回 ""（因为他们的value 用一个 String 表示不请）
    default Object printValue() {
        return "";
    }

    /**
     * 读取常量信息
     */
    void readInfo(ClassReader reader);

    static ConstantInfo readConstantInfo(ClassReader reader, ConstantPool constantPool) {
        // 先读出 tag值
        int tag = reader.readU1toInt();
        // 退回 当前常量的开头位置
        reader.back(1);

        // 再创建具体的常量
        ConstantInfo constantInfo = newConstantInfo(tag, constantPool);
        // 读取常量信息
        constantInfo.readInfo(reader);
        return constantInfo;
    }

    static ConstantInfo newConstantInfo(int tag, ConstantPool constantPool) {
        ConstantTag constantTag = ConstantTag.getByCode(tag);
        switch (constantTag) {
            // literal(字面量): 数字常量
            case CONSTANT_TAG_INTEGER:
                return new ConstantIntegerInfo();
            case CONSTANT_TAG_FLOAT:
                return new ConstantFloatInfo();
            case CONSTANT_TAG_LONG:
                return new ConstantLongInfo();
            case CONSTANT_TAG_DOUBLE:
                return new ConstantDoubleInfo();
            // literal(字面量): 字符串常量
            case CONSTANT_TAG_UTF8:
                return new ConstantUtf8Info();

            // 以下3个[引用]UTF8
            case CONSTANT_TAG_STRING:
                return new ConstantStringInfo(constantPool);
            case CONSTANT_TAG_CLASS:
                return new ConstantClassInfo(constantPool);
            case CONST_TAG_NAME_AND_TYPE:
                return new ConstantNameAndTypeInfo(constantPool);

            //以下3个[引用]CLASS + NAME_AND_TYPE
            case CONST_TAG_FIELD_REF:
                return new ConstantFieldRefInfo(constantPool);
            case CONSTANT_TAG_METHOD_REF:
                return new ConstantMethodRefInfo(constantPool);
            case CONSTANT_TAG_INTERFACE_METHOD_REF:
                return new ConstantInterfaceMethodRefInfo(constantPool);

            //是Java SE 7才添加到class文件中的，目的是支持新增的 invokeDynamic 指令
            case CONST_TAG_METHOD_HANDLE:
                return new ConstantMethodHandleInfo(constantPool);
            case CONSTANT_TAG_METHOD_TYPE:
                return new ConstantMethodTypeInfo(constantPool);
            case CONST_TAG_INVOKE_DYNAMIC:
                return new ConstantInvokeDynamicInfo(constantPool);
            default:
                throw new ClassFormatError("constant pool tag");
        }
    }

    static ConstantInfo newConstantInfoV2(int tag, ConstantPool constantPool) {
        ConstantTag constantTag = ConstantTag.getByCode(tag);
        if (constantTag == null) {
            throw new ClassFormatError("constant pool tag");
        }

        try {
            return (ConstantInfo) constantTag.getClazz().getDeclaredConstructor(ConstantPool.class).newInstance(constantPool);
        } catch (Exception e) {
            throw new ClassFormatError("constant pool newInstance error");
        }
    }
}
