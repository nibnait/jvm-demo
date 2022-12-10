package cc.tianbin.demo.jvm.common;

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
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by nibnait on 2022/12/01
 */
@AllArgsConstructor
@Getter
public enum ConstantTag {

    // literal(字面量): 数字常量
    CONSTANT_TAG_INTEGER(3, "Integer", ConstantIntegerInfo.class),
    CONSTANT_TAG_FLOAT(4, "Float", ConstantFloatInfo.class),
    CONSTANT_TAG_LONG(5, "Long", ConstantLongInfo.class),
    CONSTANT_TAG_DOUBLE(6, "Double", ConstantDoubleInfo.class),

    // literal(字面量): 字符串常量
    CONSTANT_TAG_UTF8(1, "Utf8", ConstantUtf8Info.class),

    // 以下3个[引用]UTF8
    CONSTANT_TAG_STRING(8, "String", ConstantStringInfo.class),
    CONSTANT_TAG_CLASS(7, "Class", ConstantClassInfo.class),
    CONST_TAG_NAME_AND_TYPE(12, "NameAndType", ConstantNameAndTypeInfo.class),

    // 以下3个[引用]CLASS + NAME_AND_TYPE
    CONST_TAG_FIELD_REF(9, "FieldRef", ConstantFieldRefInfo.class),
    CONSTANT_TAG_METHOD_REF(10, "MethodRef", ConstantMethodRefInfo.class),
    CONSTANT_TAG_INTERFACE_METHOD_REF(11, "InterfaceMethodRef", ConstantInterfaceMethodRefInfo.class),

    // 是Java SE 7才添加到class文件中的，目的是支持新增的 invokeDynamic 指令
    CONST_TAG_METHOD_HANDLE(15, "MethodHandle", ConstantMethodHandleInfo.class),
    CONSTANT_TAG_METHOD_TYPE(16, "MethodType", ConstantMethodTypeInfo.class),
    CONST_TAG_INVOKE_DYNAMIC(18, "InvokeDynamic", ConstantInvokeDynamicInfo.class),
    ;

    private final int code;
    private final String description;
    private final Class clazz;

    // double, long 占2个常量池索引位置
    public static final List<ConstantTag> OCCUPY_2INDEX_TAG = Lists.newArrayList(
            CONSTANT_TAG_DOUBLE, CONSTANT_TAG_LONG
    );

    public static ConstantTag getByCode(Integer code) {
        if (code == null) {
            return null;
        }

        for (ConstantTag value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }

        throw new IllegalArgumentException(String.format("%s 非法的常量池tag", code));
    }

}
